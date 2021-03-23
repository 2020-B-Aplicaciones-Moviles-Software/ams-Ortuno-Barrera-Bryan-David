package com.example.proyectoiib_moviesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val ARG_OBJECT = "object"

class CategoryAdapterCollection(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4


    override fun createFragment(position: Int): Fragment {

        val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }
}