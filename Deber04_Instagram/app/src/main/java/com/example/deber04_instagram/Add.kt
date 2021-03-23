package com.example.deber04_instagram

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*

class Add : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


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
}