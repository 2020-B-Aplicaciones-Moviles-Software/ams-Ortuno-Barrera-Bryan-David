package com.example.firebase

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(),
OnMapReadyCallback{
    private lateinit var mMap: GoogleMap
    var tienePermisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        solicitarPermisos()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    fun solicitarPermisos(){
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            Log.i("mapa","Tiene permisos FINE LOCATION")
            this.tienePermisos = true
        } else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null){
            mMap = googleMap
            establecerConfiguracionMapa(mMap)
            val quicentro = LatLng(-0.2855855756024791, -78.54325453833965)
            val titulo = "QUicentro"
            val zoom = 17f
            anadirMarcador(quicentro,titulo)
            moverCamaraConZoom(quicentro,zoom)

            val poliLineaUno = googleMap
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.2845150267169503, -78.53794454977434),
                            LatLng(-0.27941921266659364, -78.54573824266859),
                            LatLng(-0.2834658887127714, -78.5497421453368)
                        )
                )
            poliLineaUno.tag = "Linea-1"
            //Poligono
            val poligonoUno = googleMap
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.2821598188196892, -78.54809347953224),
                            LatLng(-0.28344447773209913, -78.54558836395908),
                            LatLng(-0.2868488231591276, -78.54813630202067)
                        )
                )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-2"

        }
    }
    fun anadirMarcador(latLng: LatLng, title: String){
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }
    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f){
        mMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    fun establecerConfiguracionMapa(mapa:GoogleMap){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
            }

            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }


}