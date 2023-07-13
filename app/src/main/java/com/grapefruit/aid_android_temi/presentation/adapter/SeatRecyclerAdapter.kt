package com.grapefruit.aid_android_temi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.grapefruit.aid_android_temi.databinding.RecyclerviewSeatItemBinding
import com.grapefruit.aid_android_temi.data.dto.PurchaseDTO
import com.grapefruit.aid_android_temi.util.SetItemUtil

class SeatRecyclerAdapter(
    private val glide: RequestManager
) : ListAdapter<PurchaseDTO, SeatRecyclerAdapter.ViewHolder>(DiffCallback<PurchaseDTO>()) {

    inner class ViewHolder(private val binding: RecyclerviewSeatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val menuImg: ImageView
        private val menuName: TextView
        private val menuPrice: TextView
        private val menuNumber: TextView

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
        val binding = RecyclerviewSeatItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val purchase = getItem(position)
        holder.bind(purchase)
    }
}
