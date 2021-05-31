package com.foodloop.foodloopapps.ui.detailactivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.ActivityDetailBreadBinding
import com.foodloop.foodloopapps.databinding.ListItemBinding
import com.foodloop.foodloopapps.ui.home.ViewModelHome

class DetailFoodActivity : AppCompatActivity() {

    companion object{
        const val BREAD_ID = "bread_id"
    }
    private lateinit var detailBreadBinding: ActivityDetailBreadBinding
    private lateinit var vieModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBreadBinding = ActivityDetailBreadBinding.inflate(layoutInflater)
        setContentView(detailBreadBinding.root)

        supportActionBar?.title = getString(R.string.detail_produk)
        detailBreadBinding.detail.visibility = View.GONE

        val idFood = intent.getIntExtra(BREAD_ID,0)

        val bundle = Bundle()
        bundle.putString(BREAD_ID, idFood.toString())

        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        if (idFood != null) {
            vieModel.setFoodDetail(idFood)
            vieModel.getFoodDetail().observe(this, {
                if (it != null) {
                    detailBreadBinding.progressBar.visibility = View.GONE
                    populateFood(it)
                    detailBreadBinding.detail.visibility = View.VISIBLE
                }
            })

        }
    }
    private fun dialcontact(bread: InfoDetailRespons) {
        bread.apply {
            var uri: Uri = Uri.parse("tel:$contact")
            startActivity(Intent(Intent.ACTION_DIAL, uri))
        }
    }

    private fun populateFood(food: InfoDetailRespons) {
        detailBreadBinding.tvDetailTitle.text = food.foodname
        detailBreadBinding.tvDetailDes.text = food.description
        detailBreadBinding.tvDetailLocation.text = food.address
        detailBreadBinding.tvDetailExpired.text = food.category
        if (food.price == 0){
            detailBreadBinding.tvDetailPrice.text == "Gratis"
        }else{
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

        detailBreadBinding.btnContact.setOnClickListener {
            dialcontact(food)
        }
    }
}
