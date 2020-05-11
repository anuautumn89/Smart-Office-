package com.deuron.teacoffeebackend.fragments

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.deuron.teacoffeebackend.GlobalVariables.Companion.OrderResponseArrList
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.fragments.MyItemRecyclerViewAdapter.ItemClickListener
import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import kotlinx.android.synthetic.main.fragment_order_item_list.view.*
import java.util.*
/**
 * A fragment representing a list of Items.
 */
class OrderItemFragment : Fragment(){

    private var columnCount = 1
    private var currentLNG = ""
    private var adapter: MyItemRecyclerViewAdapter? = null
    private lateinit var fragListner: OnItemSelectedListener
    private lateinit var mcontext:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale(currentLNG)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view = inflater.inflate(R.layout.fragment_order_item_list, container, false)
            if (view is RecyclerView) {
                with(view) {
                    adapter = MyItemRecyclerViewAdapter(currentLNG,mcontext,
                        OrderResponseArrList as ArrayList<OrderResponse>
                    )
                    (adapter as MyItemRecyclerViewAdapter).setClickListener(object : ItemClickListener {
                        override fun onItemClick(position: Int, item: OrderResponse?) {
                            fragListner!!.onItemClicked(position, item)
                        }
                    })
                }
            }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       // view.button.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setLocale(localeName: String) {
        var locale = Locale(localeName)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)
        //TODO how to refresh a fragment inside a fragment ??
    }

    interface OnItemSelectedListener {
        fun onItemClicked(position: Int, response:OrderResponse?)
    }

    override fun onAttach(activity: Activity): Unit {
        super.onAttach(activity)
        _onAttach(activity)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _onAttach(context)
    }
    private fun _onAttach(context: Context) {
        if (context is OnItemSelectedListener) {
            fragListner = context
        } else {
            throw ClassCastException(
                context.toString()
                        + " must implemenet MyListFragment.OnItemSelectedListener"
            )
        }
    }
    override fun onDetach() {
        super.onDetach()
      //  fragListner = null
    }
    companion object  {
        @JvmStatic
        fun newInstance(columnCount: Int, s: String, currentLanguage:String,context:Context) =
            OrderItemFragment().apply {
                this.mcontext = context
                currentLNG = currentLanguage
            }
    }
}