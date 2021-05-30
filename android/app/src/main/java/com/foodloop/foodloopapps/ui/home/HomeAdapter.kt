package com.foodloop.foodloopapps.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.foodloop.foodloopapps.BuildConfig.IMAGE_URL
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.ListItemBinding
import com.foodloop.foodloopapps.ui.detailactivity.DetailBreadActivity
import java.util.ArrayList

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.BreadViewHolder>() {
    private var listMovies = ArrayList<InfoDetailRespons>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack (onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setbread(bread: ArrayList<InfoDetailRespons>?) {
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
        fun bind(bread: InfoDetailRespons) {
            with(binding) {
                tvTitle.text = bread.foodname
                tvAddress.text = bread.address
                tvCatagory.text = bread.category
                tvPrice.text = bread.price.toString()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailBreadActivity::class.java)
                    intent.putExtra(DetailBreadActivity.BREAD_ID, bread.id)
                    itemView.context.startActivity(intent)
                }

                val image = StringBuilder("$IMAGE_URL${bread.img}").toString()
                Glide.with(itemView.context)
                    .load(image)
                    .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher_round))
                    .into(imgPoster)
            }
        }
    }

    interface OnItemClickCallBack{
        fun onItemClick(data: InfoDetailRespons)
    }
}