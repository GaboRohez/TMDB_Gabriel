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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapsFragment : Fragment(), OnMapReadyCallback {

    private val TAG = "MapsFragment"

    val db = Firebase.firestore
    val collectionRef = db.collection(COORDINATES)
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
        collectionRef.get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                addMarker(document)
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
    }

    private fun addMarker(document: QueryDocumentSnapshot?) {
        val latitude = document?.getDouble("latitude")
        val longitude = document?.getDouble("longitude")
        if (latitude != null && longitude != null) {
            val marker = LatLng(latitude, longitude)
            val markerOptions = MarkerOptions().position(marker).title(document.getString("date")!!)
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    marker,
                    15f
                )
            ) // Change zoom level as desired
            map.addMarker(markerOptions)
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