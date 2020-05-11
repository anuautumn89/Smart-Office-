package com.deuron.teacoffeebackend.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.models.orderList.OrderResponse

class EmptyMessage: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_message, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EmptyMessage().apply {
            }
    }

}