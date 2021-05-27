package com.foodloop.foodloopapps.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.foodloop.foodloopapps.databinding.FragmentCameraBinding
import com.foodloop.foodloopapps.databinding.PopupThanksBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File


class CameraFragment : Fragment() {
    private lateinit var cameraBinding: FragmentCameraBinding
    private lateinit var popupBinding: PopupThanksBinding
    private lateinit var gambar: Bitmap

    companion object {
        private const val FILE_NAME = "photo.jpg"
        private const val REQUEST_CODE = 100
        private lateinit var photoFile: File
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
            intentCamera()
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
    private fun saveImageInFirebase(img: Bitmap) {
        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://foodloop-313715.appspot.com")
        val imagePath = "Photo" + ".jpg"
        val imageRef = storgaRef.child("img/$imagePath")
        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(activity, "fail to upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            Toast.makeText(activity, "success", Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadImage(img: Bitmap){
        val baos = ByteArrayOutputStream()
        val user = "user"
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        val storage = FirebaseStorage.getInstance()
//      val storgaRef = storage.getReferenceFromUrl("gs://foodloop-313715.appspot.com")
        val storgaRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://foodloop-313715.appspot.com")
        val imagePath = "Photo - " + pictId + ".jpg"
        val imageRef = storgaRef.child("foodImg/$imagePath")

        val uploadTask = imageRef.putBytes(image)
        uploadTask.addOnFailureListener {
            Toast.makeText(activity, "Fail to upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            Toast.makeText(activity, "SUCCESS!!!", Toast.LENGTH_LONG).show()
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            val imgURI = imgBitmap
            cameraBinding.imgTap.setImageBitmap(imgBitmap)
//            UploadObject.uploadObject("phot.jpg")
            gambar = imgBitmap
            saveImageInFirebase(gambar)
//            Log.wtf("AAA", imgBitmap2.toString())
//            UploadObject.uploadObject("gambarnya.jpg",)
        }
    }



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//                val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
//                val imgBitmap = data?.extras?.get("data") as Bitmap
//                cameraBinding.imgTap.setImageBitmap(takenImage)
//                uploadImage(imgBitmap)
//            } else {
//                super.onActivityResult(requestCode, resultCode, data)
//            }
//
//    }

}