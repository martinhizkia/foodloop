package com.foodloop.foodloopapps.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.FragmentHomeBinding
import com.foodloop.foodloopapps.ui.changepassword.ChangePasswordActivity
import com.foodloop.foodloopapps.ui.detailactivity.DetailFoodActivity
import com.foodloop.foodloopapps.ui.listfood.ListFoodActivity

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
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.bg1))
        imageList.add(SlideModel(R.drawable.bg2))
        homeBinding.sliderView.visibility = View.GONE
        homeBinding.btnShowall.visibility = View.GONE


        foodAdapter = HomeAdapter()

        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelHome::class.java)

        homeBinding.apply {
            rvFood.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            rvFood.setHasFixedSize(true)
            rvFood.adapter = foodAdapter
        }
        vieModel.setFoodDetail()
        vieModel.getFoodDetail().observe(viewLifecycleOwner,{
            if (it!=null) {
                homeBinding.progressBar.visibility = View.GONE
                homeBinding.sliderView.visibility = View.VISIBLE
                homeBinding.btnShowall.visibility = View.VISIBLE
                homeBinding.sliderView.setImageList(imageList)
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

        homeBinding.btnShowall.setOnClickListener {
            val mIntent = Intent(activity, ListFoodActivity::class.java)
            startActivity(mIntent)
        }
    }
}