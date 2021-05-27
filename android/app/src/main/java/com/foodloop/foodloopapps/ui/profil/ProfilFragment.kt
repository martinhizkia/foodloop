package com.foodloop.foodloopapps.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.FragmentProfilBinding
import com.foodloop.foodloopapps.ui.changepassword.ChangePasswordActivity
import com.foodloop.foodloopapps.ui.editprofil.EditProfilActivity
import com.foodloop.foodloopapps.ui.login.LoginActivity
import com.foodloop.foodloopapps.ui.mainactivity.MainActivity

class ProfilFragment : Fragment() {
    private lateinit var profilFragment: FragmentProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        profilFragment = FragmentProfilBinding.inflate(layoutInflater, container, false)
        return profilFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profilFragment.btnEditProfil.setOnClickListener {
            val mIntent = Intent(activity, EditProfilActivity::class.java)
            startActivity(mIntent)
        }
        profilFragment.btnChangePassword.setOnClickListener {
            val mIntent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(mIntent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        activity?.menuInflater?.inflate(R.menu.menu_logout, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                Intent(activity, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}