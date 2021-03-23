package com.example.proyectoiib_moviesapp

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.proyectoiib_moviesapp.dto.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places

class Alquiler : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var tienePermisos = false
    var placesList = arrayListOf<Place>()
    var listNames = arrayListOf<String>()
    var titulo: String? = null
    var latLng: LatLng? = null
    var precio: Double = 1.0
    var distancia: Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alquiler)

        solicitarPermisos()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapa) as SupportMapFragment


        val lv = findViewById<ListView>(R.id.listView)
        val layout = findViewById<LinearLayout>(R.id.linearLayout5)
        val aceptarBtn = findViewById<Button>(R.id.btn_aceptar_ubi)
        val buscar = findViewById<Button>(R.id.btn_buscar_map)

        createPlacesList()


        for (i in placesList){
            listNames.add(i.nombre)
        }

        //ADAPTER
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNames)
        lv.adapter = adapter


        val text = findViewById<TextView>(R.id.ed_places)
        text.setOnClickListener{
            lv.visibility = VISIBLE
            buscar.visibility = INVISIBLE
        }
        text.addTextChangedListener(object :TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.getFilter().filter(s)
            }

        })

        lv.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItem(position) // The item that was clicked
            buscar.visibility = VISIBLE
            lv.visibility = INVISIBLE
            text.text = element

        }

        buscar.setOnClickListener {

            aceptarBtn.visibility = VISIBLE

            for(i in placesList){
                if(text.text.toString() == i.nombre){
                    latLng = LatLng(i.lat1,i.lat2)
                    titulo = i.nombre
                    mapFragment.getMapAsync(this)
                    layout.visibility = VISIBLE
                    //
                }
            }
        }

        aceptarBtn.setOnClickListener {
            calcularPrecio(latLng!!,titulo!!)
            val intent = Intent(this, ConfirmarPedido::class.java)
            intent.putExtra("precio",precio)
            intent.putExtra("distancia", distancia)
            intent.putExtra("direccion", text.text.toString())
            this.startActivity(intent)
        }
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
            val zoom = 17f
            anadirMarcador(latLng!!,titulo!!)
            moverCamaraConZoom(latLng!!,zoom)
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

    fun establecerConfiguracionMapa(mapa: GoogleMap){
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

    fun createPlacesList(){
        val quicentro = Place("Quicentro Norte", -0.17616789688004533, -78.47917268728337)
        val quicentroSur = Place("Quicentro Sur", -0.2857328988746509, -78.543276460303)
        val uers = Place("Marcos Escorza & Julian Estrella", -0.27524931366304634, -78.55565937379313)
        val epn = Place("Escuela Politécnica Nacional", -0.2102813985015342, -78.48853347379318)
        val elRecreo = Place( "El Recreo", -0.25222538376557896, -78.52284538728334)
        placesList.add(quicentro)
        placesList.add(quicentroSur)
        placesList.add(uers)
        placesList.add(epn)
        placesList.add(elRecreo)
    }

    fun calcularPrecio(latLng: LatLng, title: String){

        val locationOrigen = Location("Origen")
        locationOrigen.latitude -0.2102813985015342
        locationOrigen.longitude = -78.48853347379318

        val locatonDestino = Location("Destino")
        locatonDestino.latitude = latLng.latitude
        locatonDestino.longitude = latLng.longitude

        distancia = locationOrigen.distanceTo(locatonDestino)
        distancia = distancia/1000

        precio = 0.3 * distancia

    }


}
