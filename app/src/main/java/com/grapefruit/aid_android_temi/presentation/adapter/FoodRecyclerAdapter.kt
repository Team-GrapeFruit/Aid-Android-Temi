package com.grapefruit.aid_android_temi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.data.model.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.databinding.RecyclerviewFoodItemBinding

class FoodRecyclerAdapter(
    val foodList: List<PurchaseDTO>,
    val inflater: LayoutInflater,
    val glide: RequestManager

) : RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RecyclerviewFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val menuImg: ImageView
        val menuName: TextView
        val menuPrice: TextView
        val menuNumber: TextView

        init {
            menuImg = binding.menuImg
            menuName = binding.menuName
            menuPrice = binding.menuPrice
            menuNumber = binding.menuNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.recyclerview_food_item, parent, false)
        return ViewHolder(RecyclerviewFoodItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = foodList[position]

        menu.purchaseMenu.menuImgUrl.let {
            glide.load(it).centerCrop().into(holder.menuImg)
        }

        holder.menuName.text = menu.purchaseMenu.menuName

        holder.menuPrice.text = menu.purchaseMenu.cost.toString() + "원"

        holder.menuNumber.text = menu.quantity.toString() + "개"
    }
}