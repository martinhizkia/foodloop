package com.foodloop.foodloopapps.ui.profil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.SharedPreference
import com.foodloop.foodloopapps.databinding.FragmentProfilBinding
import com.foodloop.foodloopapps.ui.changepassword.ChangePasswordActivity
import com.foodloop.foodloopapps.ui.confirm.ConfirmLogoutFragment
import com.foodloop.foodloopapps.ui.editprofil.EditProfilActivity
import com.foodloop.foodloopapps.ui.settings.SettingsActivity

class ProfilFragment : Fragment() {
    private lateinit var profilFragment: FragmentProfilBinding
    private lateinit var preferences: SharedPreference

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
        preferences = SharedPreference(requireActivity())
        val username = preferences.getStringPreference("USERNAME")
        val name = preferences.getStringPreference("NAME")
        val email = preferences.getStringPreference("EMAIL")

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
        activity?.menuInflater?.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                val mOptionDialogFragment = ConfirmLogoutFragment()
                val mFragmentManager = childFragmentManager
                mOptionDialogFragment.show(mFragmentManager, ConfirmLogoutFragment::class.java.simpleName)
            }
            R.id.menu_settings -> {
                val mIntent = Intent(context, SettingsActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}