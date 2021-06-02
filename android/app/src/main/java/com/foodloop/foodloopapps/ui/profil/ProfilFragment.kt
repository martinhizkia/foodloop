package com.foodloop.foodloopapps.ui.profil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.SharedPreference
import com.foodloop.foodloopapps.databinding.FragmentProfilBinding
import com.foodloop.foodloopapps.ui.changepassword.ChangePasswordActivity
import com.foodloop.foodloopapps.ui.confirm.ConfirmLogoutFragment
import com.foodloop.foodloopapps.ui.editprofil.EditProfilActivity

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
        profilFragment.btnSettingsLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        profilFragment.btnKebijakan.setOnClickListener {
            val mIntent = Intent(activity, PrivacyPolicyActivity::class.java)
            startActivity(mIntent)
        }
        profilFragment.btnHelp.setOnClickListener {
            val mIntent = Intent(activity, HelpActivity::class.java)
            startActivity(mIntent)
        }
        profilFragment.btnAbout.setOnClickListener {
            val mIntent = Intent(activity, AboutActivity::class.java)
            startActivity(mIntent)
        }
        profilFragment.btnLogout.setOnClickListener {
            val mOptionDialogFragment = ConfirmLogoutFragment()
            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(mFragmentManager, ConfirmLogoutFragment::class.java.simpleName)
        }
    }
}