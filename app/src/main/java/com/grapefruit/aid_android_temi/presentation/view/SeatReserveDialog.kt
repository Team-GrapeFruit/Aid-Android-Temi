package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.DialogSeatReserveBinding
import com.grapefruit.aid_android_temi.presentation.adapter.SeatRecyclerAdapter
import com.grapefruit.aid_android_temi.presentation.viewmodel.MainViewModel

class SeatReserveDialog(
    private val context: SeatReserveActivity,
    private val seatNum: Long,
    private val seatId: Long
) : DialogFragment() {

    private var _binding: DialogSeatReserveBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSeatReserveBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(true)

        initDialog()
        return view
    }

    fun initDialog() {
        with(binding) {
            number.text = getString(R.string.table_number, seatNum.toString())
            cancelBtn.setOnClickListener {
                dialog?.dismiss()
            }

            nextBtn.setOnClickListener {
                viewModel.moveStart(seatId)
                viewModel.cancel(seatId)

                val intent = Intent(context, MoveActivity::class.java)
                intent.putExtra("seatNum", seatNum.toString())
                context.startActivity(intent)
            }
        }

        viewModel.menuList(seatId)

        viewModel.menuListResponse.observe(context) {
            Log.d("menuList", "$it")
            val menuRecyclerView = binding.menuRecycler
            menuRecyclerView.adapter = SeatRecyclerAdapter(
                it,
                LayoutInflater.from(context),
                Glide.with(context)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}