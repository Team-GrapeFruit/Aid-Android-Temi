package com.grapefruit.aid_android_temi.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.data.dto.CheckSeatDTO
import com.grapefruit.aid_android_temi.databinding.RecyclerviewMenuItemBinding
import com.grapefruit.aid_android_temi.presentation.view.MenuCheckActivity
import com.grapefruit.aid_android_temi.presentation.view.MoveActivity
import com.grapefruit.aid_android_temi.presentation.viewmodel.MainViewModel
import com.grapefruit.aid_android_temi.util.SetItemUtil
import kotlinx.coroutines.launch

class MenuRecyclerAdapter(
    val inflater: LayoutInflater,
    val viewModel: MainViewModel,
    val activity: MenuCheckActivity,
    val glide: RequestManager
) : ListAdapter<CheckSeatDTO.SeatDTO, MenuRecyclerAdapter.ViewHolder>(DiffCallback<CheckSeatDTO.SeatDTO>()) {

    inner class ViewHolder(val binding: RecyclerviewMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val number: TextView
        val foodRecycler: RecyclerView
        val nextBtn: Button

        init {
            number = binding.number
            foodRecycler = binding.foodRecycler
            nextBtn = binding.nextBtn
        }

        fun bind(seatId: Long) {
            viewModel.menuList(seatId)
            activity.lifecycleScope.launch {
                viewModel.menuListResponse.collect { menuList ->
                    if (menuList != null) {
                        val foodRecyclerAdapter =
                            FoodRecyclerAdapter(glide)
                        foodRecyclerAdapter.submitList(menuList)
                        foodRecycler.adapter = foodRecyclerAdapter
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.recyclerview_menu_item, parent, false)
        return ViewHolder(RecyclerviewMenuItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = getItem(position)

        holder.number.text = SetItemUtil.setSeatNum(menu.seatNum)

        holder.bind(menu.seatId)

        holder.nextBtn.setOnClickListener {
            viewModel.moveStart(menu.seatId)
            viewModel.cancel(menu.seatId)

            val intent = Intent(activity, MoveActivity::class.java)
            val seatNum = menu.seatNum.toString()
            intent.putExtra("seatNum", seatNum)
            activity.startActivity(intent)
        }
    }
}


