package com.squadtechs.markhor.foodapp.trader.fragments.trader_fragment_home


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squadtechs.markhor.foodapp.R
import com.squadtechs.markhor.foodapp.trader.activity_trader_to_customer_chat_main.ActivityTraderToCustomerChatMain
import java.lang.Exception

class TraderFargmentHome : Fragment() {

    private lateinit var viewPackage: ViewPager
    private lateinit var txtAll: TextView
    private lateinit var txtStarters: TextView
    private lateinit var txtMain: TextView
    private lateinit var txtSides: TextView
    private lateinit var txtDeserts: TextView
    private lateinit var txtTitle: TextView
    private lateinit var txtTime: TextView
    private lateinit var txtDeliveryType: TextView
    private lateinit var txtDescription: TextView
    private lateinit var imgCompany: ImageView
    private lateinit var mView: View
    private lateinit var txtNewMessage: TextView
    private lateinit var imgChat: ImageView
    private var serverTimeValue: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.trader_fargment_home, container, false)
        initViews()
        getChatCuont()
        setListeners()
        return mView
    }

    private fun getChatCuont() {
        val dbRef = FirebaseDatabase.getInstance().getReference("companies").child(
            "company${activity!!.getSharedPreferences(
                "user_credentials",
                Context.MODE_PRIVATE
            ).getString("company_id", "none")}"
        ).child("timestamp")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                try {
                    if (p0.exists()) {
                        serverTimeValue = p0.value as Long
                        if (serverTimeValue > activity!!.getSharedPreferences(
                                "trader_time_stamp",
                                Context.MODE_PRIVATE
                            ).getLong("value", 0)
                        ) {
                            txtNewMessage.visibility = View.VISIBLE
                        }
                    }
                } catch (exc: Exception) {

                }
            }
        })
    }

    private fun setListeners() {
        viewPackage.adapter =
            TraderHomePagerAdapter(childFragmentManager)
        viewPackage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        txtAll.setBackgroundResource(R.drawable.tab_back_selected_admin)
                        txtStarters.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtMain.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtSides.setBackgroundResource(R.drawable.tab_back_unselected)
                    }

                    1 -> {
                        txtAll.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtStarters.setBackgroundResource(R.drawable.tab_back_selected_admin)
                        txtMain.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtSides.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtDeserts.setBackgroundResource(R.drawable.tab_back_unselected)
                    }
                    2 -> {
                        txtAll.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtStarters.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtMain.setBackgroundResource(R.drawable.tab_back_selected_admin)
                        txtSides.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtDeserts.setBackgroundResource(R.drawable.tab_back_unselected)
                    }
                    3 -> {
                        txtAll.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtStarters.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtMain.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtSides.setBackgroundResource(R.drawable.tab_back_selected_admin)
                        txtDeserts.setBackgroundResource(R.drawable.tab_back_unselected)
                    }
                    4 -> {
                        txtAll.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtStarters.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtMain.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtSides.setBackgroundResource(R.drawable.tab_back_unselected)
                        txtDeserts.setBackgroundResource(R.drawable.tab_back_selected_admin)
                    }
                }
            }

        })

        imgChat.setOnClickListener {
            val editor =
                activity!!.getSharedPreferences("trader_time_stamp", Context.MODE_PRIVATE).edit()
            editor.putLong("value", serverTimeValue)
            editor.apply()
            takeUserToChatScreen()
        }

    }

    private fun takeUserToChatScreen() {
        val mIntent = Intent(activity!!, ActivityTraderToCustomerChatMain::class.java)
        mIntent.putExtra(
            "company_id",
            activity!!.getSharedPreferences(
                "user_credentials",
                Context.MODE_PRIVATE
            ).getString("company_id", "n/a") as String
        )
        startActivity(mIntent)
        activity!!.finish()
    }


    private fun initViews() {
        imgChat = mView.findViewById(R.id.img_chat)
        txtNewMessage = mView.findViewById(R.id.new_message_badge)
        viewPackage = mView.findViewById(R.id.company_view_pager)
        txtAll = mView.findViewById(R.id.txt_all)
        txtStarters = mView.findViewById(R.id.txt_starters)
        txtMain = mView.findViewById(R.id.txt_main_course)
        txtSides = mView.findViewById(R.id.txt_sides)
        txtDeserts = mView.findViewById(R.id.txt_deserts)
        txtTitle = mView.findViewById(R.id.txt_title)
        txtDescription = mView.findViewById(R.id.txt_description)
        txtTime = mView.findViewById(R.id.txt_time)
        txtDeliveryType = mView.findViewById(R.id.txt_delivery_type)
        imgCompany = mView.findViewById(R.id.img_company_image)
    }

}
