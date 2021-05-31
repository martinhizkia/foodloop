package com.foodloop.foodloopapps.ui.mypost

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.databinding.ListMyfoodBinding
import com.foodloop.foodloopapps.ui.detailactivity.DetailFoodActivity
import com.foodloop.foodloopapps.ui.mypost.detailmypost.DetailMyPostActivity
import java.util.*

class MyPostAdapter : RecyclerView.Adapter<MyPostAdapter.BreadViewHolder>() {
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
        val listItemBinding =
            ListMyfoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreadViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val foods = listFood[position]
        holder.bind(foods)
    }

    override fun getItemCount(): Int = listFood.size

    class BreadViewHolder(private val binding: ListMyfoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: InfoDetailRespons) {
            with(binding) {
                tvTitle.text = food.foodname
                tvAddress.text = food.address
                tvCatagory.text = food.category
                if (food.price == 0) {
                    tvPrice.text = "Gratis"
                } else {
                    tvPrice.text = "Rp.${food.price}"
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMyPostActivity::class.java)
                    intent.putExtra(DetailMyPostActivity.BREAD_ID, food.id)
                    itemView.context.startActivity(intent)
                }

                val image = StringBuilder("${BuildConfig.IMAGE_URL}${food.img}").toString()
                Glide.with(itemView.context)
                    .load(image)
                    .apply(
                        RequestOptions.circleCropTransform().placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClick(data: InfoDetailRespons)
    }

}