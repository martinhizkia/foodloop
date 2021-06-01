package com.foodloop.foodloopapps.ui.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.FragmentConfirmDeleteBinding
import com.foodloop.foodloopapps.databinding.FragmentConfirmLogoutBinding
import com.foodloop.foodloopapps.ui.home.HomeFragment
import com.foodloop.foodloopapps.ui.login.LoginActivity
import com.foodloop.foodloopapps.ui.mainactivity.MainActivity
import com.foodloop.foodloopapps.ui.mypost.detailmypost.DetailMyPostActivity

class ConfirmDeleteFragment : DialogFragment(), View.OnClickListener {
    private lateinit var popupBinding: FragmentConfirmDeleteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        popupBinding = FragmentConfirmDeleteBinding.inflate(layoutInflater, container, false)
        return popupBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popupBinding.btnConfirm.setOnClickListener(this)
        popupBinding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_confirm){
//            val idFood = intent.getIntExtra(DetailMyPostActivity.BREAD_ID, 0)
//
//            val bundle = Bundle()
//            bundle.putString(DetailMyPostActivity.BREAD_ID, idFood.toString())
//
//            preferences = this.getSharedPreferences("SHARE LOGIN", Context.MODE_PRIVATE) ?: preferences
//            val username = preferences.getString("USERNAME", "")
//            if (username != null) {
//                vieModel.deletePost(idFood, username)
//            }
            Intent(activity, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        if (v?.id == R.id.btn_cancel){
            dialog?.cancel()
        }
    }
}