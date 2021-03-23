package com.example.proyectoiib_moviesapp.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiib_moviesapp.AuthPelicula
import com.example.proyectoiib_moviesapp.R
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dataVideo.api.GENRE
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBClient
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface




class DemoObjectFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    lateinit var movieRepository: MoviePagedListRepository


    companion object {
        private const val ARG_OBJECT = "object"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {


            AuthPelicula.pelicula = null
            val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()

            movieRepository = MoviePagedListRepository(apiService)

            viewModel = getViewModel()

            val movieAdapter = PopularMoviePagedListAdapter(requireContext())

            val gridLayoutManager = GridLayoutManager(requireContext(), 3)

            val rv_movie_list = view.findViewById<RecyclerView>(R.id.rv_movie_list)
            rv_movie_list.layoutManager = gridLayoutManager
            rv_movie_list.setHasFixedSize(true)

            if(getInt(ARG_OBJECT) == 1) {
                GENRE = ""
                rv_movie_list.adapter = movieAdapter
            }
            if(getInt(ARG_OBJECT) == 3){
                GENRE = 16.toString()
                rv_movie_list.adapter = movieAdapter
            }
            if(getInt(ARG_OBJECT) == 2){
                GENRE = 35.toString()
                rv_movie_list.adapter = movieAdapter
            }
            if(getInt(ARG_OBJECT) == 4){
                GENRE = 28.toString()
                rv_movie_list.adapter = movieAdapter
            }

            viewModel.moviePagedList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                movieAdapter.submitList(it)
            })


            val progress_bar_popular = view.findViewById<ProgressBar>(R.id.progress_bar_popular)
            val txt_error_popular = view.findViewById<TextView>(R.id.txt_error_popular)
            viewModel.networkState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
                txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

                if (!viewModel.listIsEmpty()) {
                    movieAdapter.setNetworkState(it)
                }
            })

        }


    }


    private fun getViewModel(): CategoryViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(movieRepository) as T
            }
        })[CategoryViewModel::class.java]
    }

}