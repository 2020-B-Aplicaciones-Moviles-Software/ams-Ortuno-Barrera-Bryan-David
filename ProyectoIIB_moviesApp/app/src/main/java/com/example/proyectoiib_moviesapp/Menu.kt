package com.example.proyectoiib_moviesapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.InputDevice
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.*
import com.example.proyectoiib_moviesapp.ui.home.search.Busqueda
//import com.example.proyectoiib_moviesapp.ui.home.ViewPagerAdapter
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Menu : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_favoritos, R.id.nav_premium,R.id.nav_cerrar), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val correoUsuarioAuth: TextView = navView.getHeaderView(0).findViewById(R.id.textViewCorreo)
        correoUsuarioAuth.text = AuthUsuario.usuario?.email
               Log.i("log","${AuthUsuario.usuario}")


        navView.getMenu().findItem(R.id.nav_cerrar).setOnMenuItemClickListener()
        { menuItem ->
            pedidoSalir()
            true
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_TEXT
        searchView.setOnQueryTextListener(this)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    fun pedidoSalir() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.i("firebase-firestore", "Salio")
                AuthUsuario.usuario = null
                val intentR = Intent(this,MainActivity::class.java)
                startActivity(intentR)
            }
            .addOnFailureListener {
                Log.i("firebase-firestore", "Hubo problemas en Salir")
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val intentR = Intent(this,Busqueda::class.java).putExtra("nombre",query)
        startActivity(intentR)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}