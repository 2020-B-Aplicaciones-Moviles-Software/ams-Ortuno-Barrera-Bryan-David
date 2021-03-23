package com.example.deber04_instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_navigation_view.*
import kotlinx.android.synthetic.main.feed_item.*

class MainActivity : AppCompatActivity() {
    val arrayValues = arrayListOf<FeedPost>()
    var likes = 1000
    private lateinit var adaptador: FeedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        arrayValues.add(FeedPost("12","Abdala Bucaram",R.drawable.imagen6,"Pasando el rato","${likes} likes",R.drawable.imagen7))
        arrayValues.add(FeedPost("13","BryanGalindo",R.drawable.imagen1,"Eterno Presidente","1000 likes",R.drawable.imagen2))
        arrayValues.add(FeedPost("14","JuanTopo",R.drawable.imagen2,"Si no compartes este idolo de la suerte tu mamà se muere!","152.362 likes",R.drawable.imagen3))
        arrayValues.add(FeedPost("15","Abdala Bucaram",R.drawable.imagen6,"Si no sueltas el pasado, ¿con qué mano agarras el futuro?","800.362 likes",R.drawable.imagen5))

        setupRecyclerView()

        //val bottom_nav = findViewById<BottomNavigationItemView>(R.id.toolbar)
        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item_search -> {
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_item_profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_item_share -> {
                    val intent = Intent(this, Add::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_item_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_item_likes ->{
                    val intent = Intent(this, Notification::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


    }

    override fun onStart() {
        super.onStart()
        adaptador.setData(arrayValues)
    }

    fun setupRecyclerView(){
        adaptador = FeedAdapter(arrayListOf(),object : FeedAdapter.OnAdapterListener{
            override fun onSimpleClick(post: FeedPost){
                    like_image.setImageResource(R.drawable.ic_likes_active)
                    likes = likes +1
                    likes_text.text = "${likes} likes"
                }
            })

        feed_recycler.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adaptador
        }
    }



}