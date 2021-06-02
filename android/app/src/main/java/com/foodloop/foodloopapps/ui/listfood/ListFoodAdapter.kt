package com.foodloop.foodloopapps.ui.listfood

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
import com.foodloop.foodloopapps.ui.home.HomeAdapter
import java.util.ArrayList

class ListFoodAdapter : RecyclerView.Adapter<ListFoodAdapter.BreadViewHolder>() {
    private var listFood = ArrayList<InfoDetailRespons>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack (onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setbread(bread: ArrayList<InfoDetailRespons>?) {
        if (bread == null) return
        listFood.clear()
        listFood.addAll(bread)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadViewHolder {
        val listItemFoodBinding = ListMyfoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreadViewHolder(listItemFoodBinding)
    }

    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val foods = listFood[position]
        holder.bind(foods)
    }

    override fun getItemCount(): Int = listFood.size

    class BreadViewHolder(private val binding: ListMyfoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bread: InfoDetailRespons) {
            with(binding) {
                tvTitle.text = bread.foodname
                tvAddress.text = bread.address
                tvCatagory.text = bread.category
                if (bread.price == 0){
                    tvPrice.text = "Gratis"
                }else{
                    tvPrice.text = "Rp.${bread.price}"
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.BREAD_ID, bread.id)
                    itemView.context.startActivity(intent)
                }

                val image = StringBuilder("${BuildConfig.IMAGE_URL}${bread.img}").toString()
                Glide.with(itemView.context)
                    .load(image)
                    .apply(
                        RequestOptions.circleCropTransform().placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

    interface OnItemClickCallBack{
        fun onItemClick(data: InfoDetailRespons)
    }
}