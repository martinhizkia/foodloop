package com.foodloop.foodloopapps.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.foodloop.foodloopapps.BuildConfig.IMAGE_URL
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.ListFoodBinding
import com.foodloop.foodloopapps.ui.detailactivity.DetailFoodActivity
import java.util.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.BreadViewHolder>() {
    private var listFood = ArrayList<InfoDetailRespons>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setbread(bread: ArrayList<InfoDetailRespons>?) {
        if (bread == null) return
        listFood.clear()
        listFood.addAll(bread)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadViewHolder {
        val listItemFoodBinding =
            ListFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreadViewHolder(listItemFoodBinding)
    }

    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val foods = listFood[position]
        holder.bind(foods)
    }

    override fun getItemCount(): Int = listFood.size

    class BreadViewHolder(private val binding: ListFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bread: InfoDetailRespons) {
            with(binding) {
                tvTitle.text = bread.foodname
                tvCatagory.text = bread.category
                if (bread.price == 0) {
                    tvPrice.text = "Gratis"
                } else {
                    tvPrice.text = "Rp.${bread.price}"
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.BREAD_ID, bread.id)
                    itemView.context.startActivity(intent)
                }

                val image = StringBuilder("$IMAGE_URL${bread.img}").toString()
                Glide.with(itemView.context)
                    .load(image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                            .override(200, 200)
                    )
                    .into(imgPoster)
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClick(data: InfoDetailRespons)
    }
}