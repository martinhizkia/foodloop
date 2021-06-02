package com.foodloop.foodloopapps.ui.listfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.ActivityListFoodBinding
import com.foodloop.foodloopapps.databinding.FragmentHomeBinding
import com.foodloop.foodloopapps.ui.detailactivity.DetailFoodActivity
import com.foodloop.foodloopapps.ui.home.HomeAdapter
import com.foodloop.foodloopapps.ui.home.ViewModelHome

class ListFoodActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityListFoodBinding
    private lateinit var foodAdapter: ListFoodAdapter
    private lateinit var vieModel: ViewModelHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        supportActionBar?.title = getString(R.string.show_all)

        foodAdapter = ListFoodAdapter()

        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelHome::class.java)

        homeBinding.apply {
            rvFood.layoutManager = LinearLayoutManager(this@ListFoodActivity, LinearLayoutManager.VERTICAL, false)
            rvFood.setHasFixedSize(true)
            rvFood.adapter = foodAdapter
        }
        vieModel.setFoodDetail()
        vieModel.getFoodDetail().observe(this,{
            if (it!=null) {
                homeBinding.progressBar.visibility = View.GONE
                foodAdapter.setbread(it)
            }
        })
        with(foodAdapter) {
            notifyDataSetChanged()
            setOnItemClickCallBack(object : ListFoodAdapter.OnItemClickCallBack {
                override fun onItemClick(data: InfoDetailRespons) {
                    Intent(this@ListFoodActivity, DetailFoodActivity::class.java).also {
                        it.putExtra(DetailFoodActivity.BREAD_ID, data.id)
                        startActivity(it)
                    }
                }
            })
        }
    }
    }