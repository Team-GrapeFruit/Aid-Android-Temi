package com.grapefruit.aid_android_temi.presentation.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.DialogSeatReserveBinding
import com.grapefruit.aid_android_temi.presentation.adapter.SeatRecyclerAdapter
import com.grapefruit.aid_android_temi.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        dialog?.window?.setLayout(435, 508)
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

        lifecycleScope.launch {
            viewModel.menuListResponse
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { menuList ->
                    if (menuList != null) {
                        val adapter = SeatRecyclerAdapter(
                            Glide.with(context)
                        )
                        adapter.submitList(menuList)

                        val menuRecyclerView = binding.menuRecycler
                        menuRecyclerView.adapter = adapter
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}