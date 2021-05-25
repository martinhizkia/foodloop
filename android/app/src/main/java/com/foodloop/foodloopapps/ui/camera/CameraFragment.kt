package com.foodloop.foodloopapps.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.databinding.FragmentCameraBinding
import com.foodloop.foodloopapps.databinding.PopupThanksBinding
import java.io.File

class CameraFragment : Fragment() {
    private lateinit var cameraBinding: FragmentCameraBinding
    private lateinit var popupBinding: PopupThanksBinding

    companion object {
        private const val FILE_NAME = "photo.jpg"
        private const val REQUEST_CODE = 42
        private lateinit var photoFile: File
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        cameraBinding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return cameraBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraBinding.imgTap.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)


            val fileProvider = activity?.let { it1 ->
                FileProvider.getUriForFile(
                    it1,
                    "com.foodloop.foodloopapps.fileprovider",
                    photoFile
                )
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (activity?.let { it1 -> takePictureIntent.resolveActivity(it1.packageManager) } != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(activity, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }

//        cameraBinding.btnShare.setOnClickListener {
//            val view = View.inflate(activity,R.layout.popup_thanks,  null)
//            val builder = AlertDialog.Builder(activity)
//            builder.setView(view)
//
//            val dialog = builder.create()
//            dialog.show()
//            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//            view.apply {
//                popupBinding.btnConfirm.setOnClickListener {
//                    val mIntent = Intent(activity, HomeFragment::class.java)
//                    startActivity(mIntent)
//                }
//            }
//        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            cameraBinding.imgTap.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}