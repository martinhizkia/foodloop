package com.foodloop.foodloopapps.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.databinding.FragmentCameraBinding
import com.foodloop.foodloopapps.databinding.PopupThanksBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : Fragment() {
    private lateinit var cameraBinding: FragmentCameraBinding
    private lateinit var popupBinding: PopupThanksBinding
    private lateinit var gambar: Bitmap
    private lateinit var DOWNLOAD_URL: String
    private var photoFile: File? = null

    companion object {
        private const val FILE_NAME = "photo.jpg"
        private const val REQUEST_CODE = 100
        private const val REQUEST_IMAGE_CAPTURE = 101;
//        private lateinit var photoFile: File
        private var pictId: Int =  0
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
            dispatchTakePictureIntent()
//            intentCamera()
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            photoFile = getPhotoFile(FILE_NAME)
//
//
//            val fileProvider = activity?.let { it1 ->
//                FileProvider.getUriForFile(
//                        it1,
//                        "com.foodloop.foodloopapps.fileprovider",
//                        photoFile
//                )
//            }
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
//            if (activity?.let { it1 -> takePictureIntent.resolveActivity(it1.packageManager) } != null) {
//                startActivityForResult(takePictureIntent, REQUEST_CODE)
//            } else {
//                Toast.makeText(activity, "Unable to open camera", Toast.LENGTH_SHORT).show()
//            }
        }


        cameraBinding.btnShare.setOnClickListener {
//            dispatchTakePictureIntent()
//            uploadImage(gambar, "gambarhigh")
//            saveImageInFirebase(gambar)
//            val pathToFile: Path = Paths.get(filename)
//            Toast.makeText(activity, "Gambar berhasil diupload!", Toast.LENGTH_SHORT).show();
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
        }
    }
    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let { packageManager ->
                intent.resolveActivity(packageManager).also {
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }
    private fun uploadImage(img: Bitmap, pictName: String) {
        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://foodloop-313715.appspot.com")
        val imagePath = "$pictName.jpg"
        val imageRef = storgaRef.child("img/$imagePath")
        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(activity, "Failed to Upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            DOWNLOAD_URL = "https://storage.googleapis.com/foodloop-313715.appspot.com/img/${imagePath}"
            Toast.makeText(activity, DOWNLOAD_URL, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            val imgBitmap = data?.extras?.get("data") as Bitmap
//            val imgURI = imgBitmap
//            cameraBinding.imgTap.setImageBitmap(imgBitmap)
//            gambar = imgBitmap
            val imageBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
            cameraBinding.imgTap.setImageBitmap(imageBitmap)
            gambar = imageBitmap
            uploadImage(gambar, "gambarhigh")
        }
    }
    lateinit var currentPhotoPath: String

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
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                        }
                    }
                }
            }
        }
    }

}