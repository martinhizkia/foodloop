package com.foodloop.foodloopapps.ui.confirm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.FragmentPopupconfirmBinding
import com.foodloop.foodloopapps.ui.camera.CameraFragment
import com.foodloop.foodloopapps.ui.login.LoginActivity
import com.foodloop.foodloopapps.ui.mainactivity.MainActivity

class PopupconfirmFragment : DialogFragment(), View.OnClickListener {
    private lateinit var popupBinding: FragmentPopupconfirmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        popupBinding = FragmentPopupconfirmBinding.inflate(layoutInflater, container, false)
        return popupBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popupBinding.btnConfirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_confirm){
            Intent(activity, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
