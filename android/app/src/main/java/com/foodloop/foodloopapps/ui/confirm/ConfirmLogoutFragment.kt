package com.foodloop.foodloopapps.ui.confirm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.SharedPreference
import com.foodloop.foodloopapps.databinding.FragmentConfirmLogoutBinding
import com.foodloop.foodloopapps.databinding.FragmentPopupconfirmBinding
import com.foodloop.foodloopapps.ui.camera.CameraFragment
import com.foodloop.foodloopapps.ui.login.LoginActivity

class ConfirmLogoutFragment : DialogFragment(), View.OnClickListener {
    private lateinit var popupBinding: FragmentConfirmLogoutBinding
    private lateinit var sharedPref: SharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        popupBinding = FragmentConfirmLogoutBinding.inflate(layoutInflater, container, false)
        return popupBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popupBinding.btnConfirm.setOnClickListener(this)
        popupBinding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        sharedPref = SharedPreference(requireActivity())
        if (v?.id == R.id.btn_confirm){
            sharedPref.clearSharedPreference()
            Intent(activity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        if (v?.id == R.id.btn_cancel){
            dialog?.cancel()
        }
    }
}