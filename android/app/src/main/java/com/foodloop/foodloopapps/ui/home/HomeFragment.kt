package com.foodloop.foodloopapps.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.FragmentHomeBinding
import com.foodloop.foodloopapps.ui.detailactivity.DetailFoodActivity

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var foodAdapter: HomeAdapter
    private lateinit var vieModel: ViewModelHome
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodAdapter = HomeAdapter()

        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelHome::class.java)

        homeBinding.apply {
            rvFood.layoutManager = LinearLayoutManager(activity)
            rvFood.setHasFixedSize(true)
            rvFood.adapter = foodAdapter
        }
        vieModel.setFoodDetail()
        vieModel.getFoodDetail().observe(viewLifecycleOwner,{
            if (it!=null) {
                homeBinding.progressBar.visibility = View.GONE
                foodAdapter.setbread(it)
            }
        })
        with(foodAdapter) {
            notifyDataSetChanged()
            setOnItemClickCallBack(object : HomeAdapter.OnItemClickCallBack {
                override fun onItemClick(data:InfoDetailRespons) {
                    Intent(activity, DetailFoodActivity::class.java).also {
                        it.putExtra(DetailFoodActivity.BREAD_ID, data.id)
                        startActivity(it)
                    }
                }
            })
        }
    }
}