package com.example.proyectoiib_moviesapp.ui.home


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.proyectoiib_moviesapp.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



class HomeFragment : Fragment() {


    private lateinit var categoryAdapterCollection: CategoryAdapterCollection
    private lateinit var popular: PopularMoviePagedListAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryAdapterCollection = CategoryAdapterCollection(this)
        viewPager = view.findViewById(R.id.pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager.adapter = categoryAdapterCollection

        val tabLayoutMediator = TabLayoutMediator(tabLayout,viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position + 1){
                    1 -> {
                        tab.text = "Popular"
                    }
                    2 -> {
                        tab.text = "Comedia"
                    }
                    3 -> {
                        tab.text = "Animadas"
                    }
                    4 -> {
                        tab.text = "Acción"
                    }
                }
            })
        tabLayoutMediator.attach()

    }
}

