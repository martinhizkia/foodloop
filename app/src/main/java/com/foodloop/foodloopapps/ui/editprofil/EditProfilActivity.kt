package com.foodloop.foodloopapps.ui.editprofil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityEditProfilBinding
import com.foodloop.foodloopapps.ui.login.LoginActivity
import com.foodloop.foodloopapps.ui.profil.ProfilFragment

class EditProfilActivity : AppCompatActivity() {
    private lateinit var editProfilBinding: ActivityEditProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editProfilBinding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(editProfilBinding.root)

        supportActionBar?.title="Edit Profil"
        editProfilBinding.btnSave.setOnClickListener {
            val mIntent = Intent(this, ProfilFragment::class.java)
            startActivity(mIntent)
        }
    }
}