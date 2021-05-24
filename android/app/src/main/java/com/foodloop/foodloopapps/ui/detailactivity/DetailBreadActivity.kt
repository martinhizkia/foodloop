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
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.databinding.ActivityDetailBreadBinding

class DetailBreadActivity : AppCompatActivity() {
    companion object{
        const val BREAD_ID = "bread_id"
    }
    private lateinit var detailBreadBinding: ActivityDetailBreadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBreadBinding = ActivityDetailBreadBinding.inflate(layoutInflater)
        setContentView(detailBreadBinding.root)

        supportActionBar?.title = getString(R.string.detail_produk)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
        val adapter = DetailAdapter()
        val extras = intent.extras
        if (extras != null) {
            val moviesId = extras.getString(BREAD_ID)
            if (moviesId != null) {
                viewModel.setSelectedBread(moviesId)
                val data = viewModel.getDetailBread()
                adapter.setbread(data)
                populateMovies(viewModel.getBread())
            }
        }
    }

    private fun dialcontact(bread: BreadEntity) {
        bread.apply {
            var uri: Uri = Uri.parse("tel:$contact")
            startActivity(Intent(Intent.ACTION_DIAL, uri))
        }
    }

    private fun populateMovies(bread: BreadEntity) {
        detailBreadBinding.tvDetailTitle.text = bread.breadName
        detailBreadBinding.tvDetailDes.text = bread.description
        detailBreadBinding.tvDetailLocation.text = bread.address
        detailBreadBinding.tvDetailExpired.text = bread.expired
        detailBreadBinding.tvDetailPrice.text = bread.price
        detailBreadBinding.tvDetailContact.text = bread.contact
        Glide.with(this)
            .load(bread.imagePosterMovies)
            .transform(RoundedCorners(20))
            .into(detailBreadBinding.img)
        Glide.with(this)
            .load(bread.imagePosterMovies)
            .centerCrop()
            .into(detailBreadBinding.imgBg)

        detailBreadBinding.btnContact.setOnClickListener {
            dialcontact(bread)
        }
    }
}
