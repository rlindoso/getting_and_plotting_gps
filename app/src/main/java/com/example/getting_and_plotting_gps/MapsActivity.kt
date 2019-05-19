package com.example.getting_and_plotting_gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.pm.PackageManager
import android.widget.Toast





class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    // Define a listener that responds to location updates
                    val locationListener = object : LocationListener {

                        override fun onLocationChanged(location: Location) {
                            // Called when a new location is found by the network location provider.
                            makeUseOfNewLocation(location)
                        }

                        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                        }

                        override fun onProviderEnabled(provider: String) {
                        }

                        override fun onProviderDisabled(provider: String) {
                        }
                    }
                    // Register the listener with the Location Manager to receive location updates
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f,locationListener)

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    val toast = Toast.makeText(this, "O app não funciona sem permição de localização", Toast.LENGTH_LONG)
                    toast.show()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    fun makeUseOfNewLocation(location: Location){
        // Add a marker in local and move the camera
        val local = LatLng(location.latitude, location.longitude)
        mMap.addMarker(MarkerOptions().position(local).title(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(local))

    }
}
