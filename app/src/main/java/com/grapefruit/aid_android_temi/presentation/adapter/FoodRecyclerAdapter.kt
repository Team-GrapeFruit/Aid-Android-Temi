package com.grapefruit.aid_android_temi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.databinding.RecyclerviewFoodItemBinding
import com.grapefruit.aid_android_temi.util.SetItemUtil

class FoodRecyclerAdapter(
    private val glide: RequestManager
) : ListAdapter<PurchaseDTO, FoodRecyclerAdapter.ViewHolder>(DiffCallback<PurchaseDTO>()) {

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

        fun bind(purchase: PurchaseDTO) {
            glide.load(purchase.purchaseMenu.menuImgUrl).centerCrop().into(menuImg)
            menuName.text = purchase.purchaseMenu.menuName
            menuPrice.text = SetItemUtil.setCost(purchase.purchaseMenu.cost)
            menuNumber.text = SetItemUtil.setQuantity(purchase.quantity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewFoodItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = getItem(position)
        holder.bind(menu)
    }
}
