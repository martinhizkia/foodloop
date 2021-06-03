package com.foodloop.foodloopapps.ui.editprofil

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityEditProfilBinding

class EditProfilActivity : AppCompatActivity() {
    private lateinit var editProfilBinding: ActivityEditProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editProfilBinding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(editProfilBinding.root)

        supportActionBar?.title = getString(R.string.edit_profil)
        editProfilBinding.btnSave.setOnClickListener {
            Toast.makeText(this, getString(R.string.menu_invalid), Toast.LENGTH_SHORT).show()
        }
    }
}