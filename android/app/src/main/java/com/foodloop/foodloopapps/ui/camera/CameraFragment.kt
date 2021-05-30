package com.foodloop.foodloopapps.ui.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.UserRespons
import com.foodloop.foodloopapps.databinding.FragmentCameraBinding
import com.foodloop.foodloopapps.ui.confirm.PopupconfirmFragment
import com.foodloop.foodloopapps.ui.home.HomeFragment
import com.foodloop.foodloopapps.ui.registration.RegistrationActivity
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CameraFragment : Fragment() {
    private lateinit var cameraBinding: FragmentCameraBinding
    private lateinit var gambar: Bitmap
    private lateinit var DOWNLOAD_URL: String
    lateinit var preferences: SharedPreferences
    private var photoFile: File? = null
    lateinit var currentPhotoPath: String

    companion object {
        private const val REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        cameraBinding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return cameraBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraBinding.imgTap.setOnClickListener {
            dispatchTakePictureIntent()
        }

        cameraBinding.btnShare.setOnClickListener {
            val nameBread: String = cameraBinding.edName.text.toString().trim()
            val description: String = cameraBinding.edDescription.text.toString().trim()
            val address: String = cameraBinding.edAddress.text.toString().trim()
            val price: String = cameraBinding.edPrice.text.toString().trim()
            val contact: String = cameraBinding.edContact.text.toString().trim()
            val category: String = cameraBinding.edCategory.text.toString().trim()

            if (nameBread.isEmpty()) {
                cameraBinding.edName.error = "Name Bread is required"
                cameraBinding.edName.requestFocus()
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                cameraBinding.edDescription.error = "Description is required"
                cameraBinding.edDescription.requestFocus()
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                cameraBinding.edAddress.error = "Address is required"
                cameraBinding.edAddress.requestFocus()
                return@setOnClickListener
            }
            if (price.isEmpty()) {
                cameraBinding.edPrice.error = "Price is required"
                cameraBinding.edPrice.requestFocus()
                return@setOnClickListener
            }
            if (contact.isEmpty()) {
                cameraBinding.edContact.error = "Contact is required"
                cameraBinding.edContact.requestFocus()
                return@setOnClickListener
            }
            if (category.isEmpty()) {
                cameraBinding.edCategory.error = "Catagory is required"
                cameraBinding.edCategory.requestFocus()
                return@setOnClickListener
            }
            postInfo()

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun postInfo() {
        preferences =
            this.activity?.getSharedPreferences("SHARE LOGIN", Context.MODE_PRIVATE) ?: preferences
        val username = preferences.getString("USERNAME", "")

        val nameBread = cameraBinding.edName.text.toString()
        val description = cameraBinding.edDescription.text.toString()
        val address = cameraBinding.edAddress.text.toString()
        val price = cameraBinding.edPrice.text.toString()
        val contact = cameraBinding.edContact.text.toString()
        val category = cameraBinding.edCategory.text.toString()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH-mm-ss")
        val formatted = current.format(formatter)
        val nameImg = "$username$formatted.jpg"

        uploadImage(gambar, "$nameImg")

        val user = ApiConfig.getApiService(BuildConfig.INFO_URL).create(ApiService::class.java)
        user.postInfo(username, nameBread, address, description, price, contact, category, nameImg)
            .enqueue(object : Callback<UserRespons> {
                override fun onFailure(call: Call<UserRespons>, t: Throwable) {
                    Log.e("POST INFO", "Failed: ${t.message.toString()}")
                }

                override fun onResponse(call: Call<UserRespons>, response: Response<UserRespons>) {
                    val user = response.body()
                    user?.status?.let { Log.d("POST INFO", it) }
                    if (user?.status == "Info Uploaded") {
                        Toast.makeText(
                            activity,
                            user?.status,
                            Toast.LENGTH_SHORT
                        ).show()
                        val mOptionDialogFragment = PopupconfirmFragment()
                        val mFragmentManager = childFragmentManager
                        mOptionDialogFragment.show(mFragmentManager, PopupconfirmFragment::class.java.simpleName)
                    } else {
                        Toast.makeText(
                            activity,
                            user?.status,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun uploadImage(img: Bitmap, pictName: String) {
        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://foodloop-313715.appspot.com")
        val imagePath = "$pictName"
        val imageRef = storgaRef.child("img/$imagePath")
        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(activity, "fail to upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            DOWNLOAD_URL =
                "https://storage.googleapis.com/foodloop-313715.appspot.com/img/${imagePath}"
            Toast.makeText(activity, DOWNLOAD_URL, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
            cameraBinding.imgTap.setImageBitmap(imageBitmap)
            gambar = imageBitmap
        }

    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            context?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    photoFile = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        null
                    }
                    photoFile?.also {
                        context?.let { context ->
                            val photoURI: Uri = FileProvider.getUriForFile(
                                context,
                                "com.foodloop.foodloopapps.fileprovider",
                                it
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, REQUEST_CODE)
                        }
                    }
                }
            }
        }
    }
}