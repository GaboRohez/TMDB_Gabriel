package com.gmail.gabow95k.tmdb.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.gabow95k.tmdb.COORDINATES
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MapsFragment : Fragment(), OnMapReadyCallback {

    private val TAG = "MapsFragment"

    val db = FirebaseFirestore.getInstance()
    val docRef = db.collection(COORDINATES)
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setUpEvents()
    }

    private fun setUpEvents() {
        docRef.addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Error al obtener la colección.", e)
                return@addSnapshotListener
            }

            for (document in value!!.documentChanges) {
                if (document.type == DocumentChange.Type.ADDED) {
                    val data = document.document.data
                    if (data["latitude"] != null && data["longitude"] != null) {
                        val latitude = data["latitude"] as Double
                        val longitude = data["longitude"] as Double
                        val latLng = LatLng(latitude, longitude)
                        map.addMarker(
                            MarkerOptions().position(latLng).title(data["date"] as String)
                        )

                        val cameraPosition = CameraPosition.Builder()
                            .target(latLng)
                            .zoom(15f)
                            .build()
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}