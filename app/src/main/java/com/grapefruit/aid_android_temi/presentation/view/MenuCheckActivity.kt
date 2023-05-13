package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityMenuCheckBinding
import com.grapefruit.aid_android_temi.presentation.adapter.MenuRecyclerAdapter
import com.grapefruit.aid_android_temi.presentation.viewmodel.MainViewModel
import com.robotemi.sdk.Robot

class MenuCheckActivity : AppCompatActivity() {

    lateinit var binding: ActivityMenuCheckBinding
    private val viewModel: MainViewModel by viewModels()
    private val robot = Robot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_check)

        robot.getInstance().setKioskModeOn(true)
        robot.getInstance().hideTopBar()

        val storeId = intent.getLongExtra("storeId", 0)
        viewModel.seatList(storeId)

        viewModel.seatListResponse.observe(this) {
            Log.d("seatList", "$it")

            val pager = binding.pager
            val tabLayout = binding.tabLayout
            val lastPosition = it.singleSeatResponse.lastIndex
            val seatList = it

            pager.adapter = MenuRecyclerAdapter(
                it,
                LayoutInflater.from(this),
                viewModel,
                this,
                Glide.with(this)
            )

            pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

                // Paging 완료되면 호출
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("ViewPagerFragment", "Page ${position+1}")
                }
            })

            TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = (1 + position).toString()
            }.attach()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabLayout.getTabAt(tab!!.position)?.view?.setBackgroundResource(R.drawable.empty_background)

                    viewModel.menuList(seatList.singleSeatResponse[tab!!.position].seatId)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tabLayout.getTabAt(tab!!.position)?.view?.setBackgroundColor(Color.TRANSPARENT)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    tabLayout.getTabAt(tab!!.position)?.view?.setBackgroundResource(R.drawable.empty_background)

                    viewModel.menuList(seatList.singleSeatResponse[tab!!.position].seatId)
                }
            })

            binding.leftBtn.setOnClickListener {
                if (pager.currentItem == 0) {
                    pager.setCurrentItem(lastPosition, true)
                } else {
                    pager.setCurrentItem(pager.currentItem - 1, true)
                }
            }

            binding.rightBtn.setOnClickListener {
                if (pager.currentItem == lastPosition){
                    pager.setCurrentItem(0, true)
                } else {
                    pager.setCurrentItem(pager.currentItem + 1, true)
                }
            }
        }

        binding.main.setOnClickListener {
            val intent = Intent(this, SeatReserveActivity::class.java)
            intent.putExtra("storeId", storeId)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}