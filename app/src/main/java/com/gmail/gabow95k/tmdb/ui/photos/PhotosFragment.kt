package com.gmail.gabow95k.tmdb.ui.photos

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.databinding.FragmentPhotosBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class PhotosFragment : Fragment() {

    private val TAG = "PhotosFragment"

    private lateinit var imageUri: Uri
    private val REQUEST_CODE_IMAGE = 1
    private var bitmap: Bitmap? = null
    private val binding get() = _binding!!
    private val REQUEST_CODE_CAPTURE_IMAGE = 2
    val storage = FirebaseStorage.getInstance()
    private val CAMERA_PERMISSION_REQUEST_CODE = 200
    private var _binding: FragmentPhotosBinding? = null
    val storageRef = storage.reference.child("images/")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvents()
    }

    private fun setUpEvents() {
        binding.btnGallery.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                dispatchTakePictureIntent()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }

        binding.btnCancel.setOnClickListener {
            resetView()
        }

        binding.btnUpload.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.sending), Toast.LENGTH_SHORT).show()
            binding.btnCancel.isEnabled = false
            binding.btnUpload.isEnabled = false

            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            storageRef.child("${Date()}.jpg").putBytes(data)
                .addOnSuccessListener {
                    resetView()
                    Snackbar.make(
                        binding.root,
                        getString(R.string.image_save_in_fb),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error al cargar imagen", e) }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun resetView() {
        changeButtons(false)
        Glide.with(requireContext())
            .load(requireContext().resources.getDrawable(R.drawable.placeholder))
            .into(binding.imageView)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE)
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            uri = createImageUri()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            val photoFile = createImageFile()
            uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                photoFile
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }
        imageUri = uri
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    @SuppressLint("InlinedApi")
    private fun createImageUri(): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val resolver = requireContext().contentResolver
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.camera_permission_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
            Glide.with(requireContext())
                .asBitmap()
                .load(bitmap)
                .into(binding.imageView)

            changeButtons(true)
        }
        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            val contentResolver = requireContext().contentResolver
            val inputStream = contentResolver.openInputStream(imageUri)
            bitmap = BitmapFactory.decodeStream(inputStream)

            Glide.with(requireContext())
                .asBitmap()
                .load(bitmap)
                .into(binding.imageView)

            changeButtons(true)
        }
    }

    private fun changeButtons(flag: Boolean) {
        binding.btnCamera.visibility = if (flag) View.GONE else View.VISIBLE
        binding.btnGallery.visibility = if (flag) View.GONE else View.VISIBLE
        binding.btnCancel.visibility = if (flag) View.VISIBLE else View.GONE
        binding.btnUpload.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}