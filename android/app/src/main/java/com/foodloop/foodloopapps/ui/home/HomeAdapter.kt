package com.foodloop.foodloopapps.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.databinding.ListItemBinding
import com.foodloop.foodloopapps.ui.detailactivity.DetailBreadActivity
import java.util.ArrayList

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.BreadViewHolder>() {
    private var listMovies = ArrayList<BreadEntity>()

    fun setbread(bread: List<BreadEntity>?) {
        if (bread == null) return
        this.listMovies.clear()
        this.listMovies.addAll(bread)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadViewHolder {
        val listItemMoviesBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreadViewHolder(listItemMoviesBinding)
    }

    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size

    class BreadViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bread: BreadEntity) {
            with(binding) {
                tvTitle.text = bread.breadName
                tvAddress.text = bread.address
                tvExpired.text = bread.expired
                tvPrice.text = bread.price
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailBreadActivity::class.java)
                    intent.putExtra(DetailBreadActivity.BREAD_ID, bread.breadId)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(bread.imagePosterMovies)
                    .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher_round))
                    .into(imgPoster)
            }
        }
    }
}