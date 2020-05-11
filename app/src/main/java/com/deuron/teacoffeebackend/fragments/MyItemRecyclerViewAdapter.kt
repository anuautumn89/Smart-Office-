package com.deuron.teacoffeebackend.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.models.orderList.OrderItemsInfo
import com.deuron.teacoffeebackend.models.orderList.OrderPayload
import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import com.deuron.teacoffeebackend.views.MainActivity
import kotlinx.android.synthetic.main.fragment_order_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class MyItemRecyclerViewAdapter// data is passed into the constructor
internal constructor(lang:String ,context : Context, private val orderArrItems: ArrayList<OrderResponse>) :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    private var language: String = lang
    internal var currentItemObj: OrderResponse? = null
    init {
        this.mInflater = LayoutInflater.from(context)
    }
    /* inflates the row layout from xml when needed */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.fragment_order_item, parent, false)
        return ViewHolder(view)
    }

    // bind data to the view and textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item  = orderArrItems[position]
        currentItemObj = item
        holder.orderNumber.text = item.getId()
        holder.deliveryTime.text = languageStrng(
            item.getPayload()!!.getDelivery()!!.getDeliverySi(),
            item.getPayload()!!.getDelivery()!!.getDeliverySi(),
            item.getPayload()!!.getDelivery()!!.getDeliverySi(),
                language)
        holder.destination.text = languageStrng(
            item.getPayload()!!.getLocationName()!!.getLocationNameSi(),
            item.getPayload()!!.getLocationName()!!.getLocationNameEn(),
            item.getPayload()!!.getLocationName()!!.getLocationNameTl(),
                language)
        holder.totalOrderItemCount.text = gettotalOrderCount(item.getPayload()!!.getOrderItems())
    }

    // returns size of the list
    override fun getItemCount(): Int {
        return orderArrItems.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val orderNumber: TextView = itemView.txtOrderNumber
        val totalOrderItemCount: TextView = itemView.txtTotalOrderCount
        val destination: TextView = itemView.txtDestination
        val deliveryTime: TextView = itemView.txtDeliveryTime
        private val lutOrderRow: LinearLayout = itemView.lutOrderRow
        val lutViewOrder:LinearLayout = itemView.lutViewOrder

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            if (mClickListener != null) {
                mClickListener!!.onItemClick(adapterPosition,currentItemObj)
            }
        }
    }
        // allows clicks events to be caught
        fun setClickListener(itemClickListener:ItemClickListener) {
            this.mClickListener = itemClickListener
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            fun onItemClick(
                position: Int,
                item:OrderResponse?
            )
        }
        private fun languageStrng(
            strSi: String?,
            strEn: String?,
            strTl: String?,
            lang: String
        ): String? {
            var languageStrng: String?
            if (lang == "si") {
                languageStrng = strSi
            } else if (lang == "en") {
                languageStrng = strEn
            } else {
                languageStrng = strTl
            }
            return languageStrng
        }

        private fun gettotalOrderCount(orderItemsInfo: OrderItemsInfo?): String {
            var total =
                orderItemsInfo!!.getBLackTeaS()!!.toInt() + orderItemsInfo!!.getBlackTeaNs()!!.toInt() +
                        orderItemsInfo!!.getCoffeeNs()!!.toInt() + orderItemsInfo!!.getCoffeeS()!!.toInt() +
                        orderItemsInfo!!.getMilkNs()!!.toInt() + orderItemsInfo!!.getMilkS()!!.toInt() +
                        orderItemsInfo!!.getMilkTeaNs()!!.toInt() + orderItemsInfo!!.getMilkTeaS()!!.toInt()

            return total.toString()
        }

        private fun getDeliveryTime(orderPlacedTime: String, deliveryTime: String?): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date: Date = sdf.parse(orderPlacedTime)
            var deliveryin: Int = 5
            if (deliveryTime!!.toInt() != deliveryin) deliveryin = deliveryTime.toInt()
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.MINUTE, deliveryin)
            return calendar.time.toString()
        }

}
