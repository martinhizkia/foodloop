package com.foodloop.foodloopapps.ui.mypost

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.FragmentMyPostBinding
import com.foodloop.foodloopapps.ui.mypost.detailmypost.DetailMyPostActivity

class MyPostFragment : Fragment() {
    private lateinit var homeBinding: FragmentMyPostBinding
    private lateinit var foodAdapter: MyPostAdapter
    private lateinit var vieModel: ViewModelMypost
    lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeBinding = FragmentMyPostBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences =
            this.activity?.getSharedPreferences("SHARE LOGIN", Context.MODE_PRIVATE) ?: preferences
        val username = preferences.getString("USERNAME", "")
        foodAdapter = MyPostAdapter()

        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelMypost::class.java)

        homeBinding.apply {
            rvMypost.layoutManager = LinearLayoutManager(activity)
            rvMypost.setHasFixedSize(true)
            rvMypost.adapter = foodAdapter
        }

        username?.let { vieModel.setMypost(it) }
        vieModel.getMypost().observe(viewLifecycleOwner, {
            if (it != null) {
                homeBinding.progressBar.visibility = View.GONE
                foodAdapter.setbread(it)
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
}