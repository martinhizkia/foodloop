package com.foodloop.foodloopapps.ui.detailactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.databinding.ActivityDetailBreadBinding
import java.util.*

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.BreadViewHolder>() {
    private var listMovies = ArrayList<BreadEntity>()

    fun setbread(bread: List<BreadEntity>?) {
        if (bread == null) return
        this.listMovies.clear()
        this.listMovies.addAll(bread)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadViewHolder {
        val listItemMoviesBinding =
            ActivityDetailBreadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreadViewHolder(listItemMoviesBinding)
    }

    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size

    class BreadViewHolder(private val binding: ActivityDetailBreadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bread: BreadEntity) {
            with(binding) {
                tvDetailTitle.text = bread.breadName
                tvDetailDes.text = bread.description
                tvDetailLocation.text = bread.address
                tvDetailExpired.text = bread.expired
                tvDetailPrice.text = bread.price
                tvDetailContact.text = bread.contact
                Glide.with(itemView.context)
                    .load(bread.imagePosterMovies)
                    .apply(
                        RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher_round)
                    )
                    .into(img)

                Glide.with(itemView.context)
                    .load(bread.imagePosterMovies)
                    .thumbnail()
                    .centerCrop()
                    .into(imgBg)
            }
        }
    }
}