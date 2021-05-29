package com.foodloop.foodloopapps.ui.profil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    lateinit var preferences: SharedPreferences

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

        preferences = this.activity?.getSharedPreferences("SHARE LOGIN", Context.MODE_PRIVATE) ?: preferences
        val username = preferences.getString("USERNAME", "")
        val name = preferences.getString("NAME", "")
        val email = preferences.getString("EMAIL", "")

        profilFragment.tvProfilUsername.text = username
        profilFragment.tvProfilFullname.text = name
        profilFragment.tvProfilEmail.text = email

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