package com.foodloop.foodloopapps.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Build.*
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
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.UserRespons
import com.foodloop.foodloopapps.databinding.FragmentCameraBinding
import com.foodloop.foodloopapps.ml.ConvertedModel
import com.foodloop.foodloopapps.ui.confirm.PopupconfirmFragment
import com.google.firebase.storage.FirebaseStorage
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
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
        private const val GALLERY_CODE = 120
        private const val PERMISSION_CODE = 1001;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        cameraBinding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return cameraBinding.root
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraBinding.imgTap.setOnClickListener {
            dispatchTakePictureIntent()
        }
        cameraBinding.imgGallery.setOnClickListener {
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                if (context?.let { it1 ->
                        checkSelfPermission(
                            it1,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    } ==
                    PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }


        cameraBinding.btnShare.setOnClickListener {
            val nameBread: String = cameraBinding.edName.text.toString().trim()
            val description: String = cameraBinding.edDescription.text.toString().trim()
            val address: String = cameraBinding.edAddress.text.toString().trim()
            val price: String = cameraBinding.edPrice.text.toString().trim()
            val contact: String = cameraBinding.edContact.text.toString().trim()
            val category: String = cameraBinding.edCategory.text.toString().trim()

            if (nameBread.isEmpty()) {
                cameraBinding.edName.error = getString(R.string.name_food_required)
                cameraBinding.edName.requestFocus()
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                cameraBinding.edDescription.error = getString(R.string.description_required)
                cameraBinding.edDescription.requestFocus()
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                cameraBinding.edAddress.error = getString(R.string.address_required)
                cameraBinding.edAddress.requestFocus()
                return@setOnClickListener
            }
            if (price.isEmpty()) {
                cameraBinding.edPrice.error = getString(R.string.price_required)
                cameraBinding.edPrice.requestFocus()
                return@setOnClickListener
            }
            if (contact.isEmpty()) {
                cameraBinding.edContact.error = getString(R.string.contact_required)
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

        cameraBinding.btnPrediction.setOnClickListener {
            var resized = Bitmap.createScaledBitmap(gambar, 224, 224, true)
            val model = context?.let { ConvertedModel.newInstance(it) }

            var tbuffer = TensorImage.fromBitmap(resized)
            var byteBuffer = tbuffer.buffer

// Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model?.process(inputFeature0)
            val outputFeature0 = outputs?.outputFeature0AsTensorBuffer

            var max = outputFeature0?.floatArray?.let { getMax(it) }

            val labels =
                context?.assets?.open("labels.txt")?.bufferedReader().use { it?.readText() }
                    ?.split("\n")

            cameraBinding.edCategory.text = max?.let { labels?.get(it) }

// Releases model resources if no longer used.
            model?.close()
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
                        mOptionDialogFragment.show(
                            mFragmentManager,
                            PopupconfirmFragment::class.java.simpleName
                        )
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

    @RequiresApi(VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
            cameraBinding.imgTap.setImageBitmap(imageBitmap)
            gambar = imageBitmap
        }
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_CODE) {
//            val bitmap = when {
//                Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
//                    context?.contentResolver,
//                    data?.data
//                )
//                else -> {
//                    val source = context?.let { ImageDecoder.createSource(it.contentResolver,
//                        data?.data!!
//                    ) }
//                    ImageDecoder.decodeBitmap(source!!)
//                }
//            }
            val source = context?.let {
                ImageDecoder.createSource(
                    it.contentResolver,
                    data?.data!!
                )
            }
            val bitmap = ImageDecoder.decodeBitmap(source!!)
            cameraBinding.imgTap.setImageBitmap(bitmap)
            gambar = bitmap
        }
    }

    fun getMax(arr: FloatArray): Int {
        var ind = 0;
        var min = 0.0f;

        for (i in 0..1000) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_CODE)
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