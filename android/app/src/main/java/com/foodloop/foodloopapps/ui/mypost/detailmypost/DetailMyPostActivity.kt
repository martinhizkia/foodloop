package com.foodloop.foodloopapps.ui.mypost.detailmypost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.SharedPreference
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.ActivityDetailMyPostBinding
import com.foodloop.foodloopapps.ui.mainactivity.MainActivity

class DetailMyPostActivity : AppCompatActivity() {
    companion object {
        const val BREAD_ID = "bread_id"
    }

    private lateinit var detailBreadBinding: ActivityDetailMyPostBinding
    private lateinit var vieModel: DetailMyPostViewModel
    private lateinit var preferences: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBreadBinding = ActivityDetailMyPostBinding.inflate(layoutInflater)
        setContentView(detailBreadBinding.root)

        supportActionBar?.title = getString(R.string.detail_produk)
        detailBreadBinding.detail.visibility = View.GONE

        val idFood = intent.getIntExtra(BREAD_ID, 0)

        val bundle = Bundle()
        bundle.putString(BREAD_ID, idFood.toString())

        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailMyPostViewModel::class.java)
        if (idFood != null) {
            vieModel.setMypostDetail(idFood)
            vieModel.getMypostDetail().observe(this, {
                if (it != null) {
                    detailBreadBinding.progressBar.visibility = View.GONE
                    populateFood(it)
                    detailBreadBinding.detail.visibility = View.VISIBLE
                }
            })

        }
    }

    private fun deletePost() {
        val idFood = intent.getIntExtra(BREAD_ID, 0)

        val bundle = Bundle()
        bundle.putString(BREAD_ID, idFood.toString())

        preferences = SharedPreference(this)
        val username = preferences.getStringPreference("USERNAME")
        if (username != null) {
            vieModel.deletePost(idFood, username)
            Toast.makeText(this, getString(R.string.delete_post), Toast.LENGTH_SHORT).show()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun populateFood(food: InfoDetailRespons) {
        detailBreadBinding.tvDetailTitle.text = food.foodname
        detailBreadBinding.tvDetailDes.text = food.description
        detailBreadBinding.tvDetailLocation.text = food.address
        detailBreadBinding.tvDetailExpired.text = food.category
        if (food.price == 0) {
            detailBreadBinding.tvDetailPrice.text == "Gratis"
        } else {
            detailBreadBinding.tvDetailPrice.text = "Rp.${food.price}"
        }
        detailBreadBinding.tvDetailContact.text = food.contact
        val image = StringBuilder("${BuildConfig.IMAGE_URL}${food.img}").toString()
        Glide.with(this)
            .load(image)
            .transform(RoundedCorners(20))
            .into(detailBreadBinding.img)
        Glide.with(this)
            .load(image)
            .centerCrop()
            .into(detailBreadBinding.imgBg)

        detailBreadBinding.deleteMypost.setOnClickListener {
            deletePost()
        }
    }
}