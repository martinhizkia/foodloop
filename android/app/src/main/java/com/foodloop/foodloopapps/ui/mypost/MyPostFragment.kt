package com.foodloop.foodloopapps.ui.mypost

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodloop.foodloopapps.data.SharedPreference
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.FragmentMyPostBinding
import com.foodloop.foodloopapps.ui.mypost.detailmypost.DetailMyPostActivity

class MyPostFragment : Fragment() {
    private lateinit var homeBinding: FragmentMyPostBinding
    private lateinit var foodAdapter: MyPostAdapter
    private lateinit var vieModel: ViewModelMypost
    private lateinit var preferences: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeBinding = FragmentMyPostBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = SharedPreference(requireActivity())
        val username = preferences.getStringPreference("USERNAME")

        homeBinding.progressBar.visibility = View.VISIBLE
        
        foodAdapter = MyPostAdapter()

        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelMypost::class.java)

        username?.let { vieModel.setMypost(it) }
        vieModel.getMypost().observe(viewLifecycleOwner, {
            if (it != null) {
                showListMyPost(it)
            }
        })
        with(foodAdapter) {
            notifyDataSetChanged()
            setOnItemClickCallBack(object : MyPostAdapter.OnItemClickCallBack {
                override fun onItemClick(data: InfoDetailRespons) {
                    Intent(context, DetailMyPostActivity::class.java).also {
                        it.putExtra(DetailMyPostActivity.BREAD_ID, data.id)
                        startActivity(it)
                    }
                }
            })
        }
    }

    private fun showListMyPost(it: ArrayList<InfoDetailRespons>) {
        foodAdapter.setbread(it)
        when (it.size) {
            0 -> {
                homeBinding?.apply {
                    progressBar.visibility = View.GONE
                    imgError.visibility = View.VISIBLE
                }
            }
            else -> {
                homeBinding?.apply {
                    progressBar.visibility = View.GONE
                    imgError.visibility = View.GONE
                }
                homeBinding.apply {
                    rvMypost.layoutManager = LinearLayoutManager(activity)
                    rvMypost.setHasFixedSize(true)
                    rvMypost.adapter = foodAdapter
                }
            }
        }
    }
}