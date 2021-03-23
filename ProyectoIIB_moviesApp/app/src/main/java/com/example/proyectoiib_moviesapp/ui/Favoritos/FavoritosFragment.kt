package com.example.proyectoiib_moviesapp.ui.Favoritos

import android.os.Bundle
import android.util.Log
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
import com.example.proyectoiib_moviesapp.AuthUsuario
import com.example.proyectoiib_moviesapp.R
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBClient
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface
import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.example.proyectoiib_moviesapp.ui.home.CategoryViewModel
import com.example.proyectoiib_moviesapp.ui.home.MoviePagedListRepository
import com.example.proyectoiib_moviesapp.ui.home.PopularMoviePagedListAdapter
import com.example.proyectoiib_moviesapp.ui.home.details.MovieDetailsRepository
import com.example.proyectoiib_moviesapp.ui.home.details.SingleMovieViewModel

class FavoritosFragment : Fragment() {
//
//    private lateinit var viewModel: SingleMovieViewModel
//    private lateinit var movieRepository: MovieDetailsRepository
//    lateinit var movieRepositoryList: MoviePagedListRepository
//    var favMovies = arrayListOf<Pelicula>()
//
//
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//
//        val root = inflater.inflate(R.layout.fragment_favorites, container, false)
//        return root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
//
//        for (x in 0 until AuthUsuario.usuario!!.idPeliculaFav!!.size) {
//        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
//        movieRepository =
//                MovieDetailsRepository(
//                        apiService
//                )
//
//
//           viewModel = getViewModel(AuthUsuario.usuario!!.idPeliculaFav!![x].toInt())
//            viewModel.movieDetails.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//                favMovies.add(it)
//                Log.i("movies","${favMovies}")
//            })
//        }
//
//
//       // movieRepository = MoviePagedListRepository(apiService)
//
//      //  viewModel = getViewModel()
//
////        val movieAdapter = PopularMoviePagedListAdapter(requireContext())
////
////        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
////
////        val rv_movie_list = view.findViewById<RecyclerView>(R.id.rv_movie_list)
////        rv_movie_list.layoutManager = gridLayoutManager
////        rv_movie_list.setHasFixedSize(true)
////
////        rv_movie_list.adapter = movieAdapter
////
////        viewModel.moviePagedList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
////            movieAdapter.submitList(it)
////        })
////
////
////        val progress_bar_popular = view.findViewById<ProgressBar>(R.id.progress_bar_popular)
////        val txt_error_popular = view.findViewById<TextView>(R.id.txt_error_popular)
////        viewModel.networkState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
////            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
////            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
////
////            if (!viewModel.listIsEmpty()) {
////                movieAdapter.setNetworkState(it)
////            }
////        })
//
//    }
//
//
//
//    private fun getViewModel(movieId:Int): SingleMovieViewModel {
//        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return SingleMovieViewModel(
//                        movieRepository,
//                        movieId
//                ) as T
//            }
//        })[SingleMovieViewModel::class.java]
//    }
}