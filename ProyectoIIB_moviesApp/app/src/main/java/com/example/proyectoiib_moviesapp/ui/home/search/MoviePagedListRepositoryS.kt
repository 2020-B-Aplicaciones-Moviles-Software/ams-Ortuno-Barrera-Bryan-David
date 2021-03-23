package com.example.proyectoiib_moviesapp.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.proyectoiib_moviesapp.dataVideo.Repository.*
import com.example.proyectoiib_moviesapp.dataVideo.api.POST_PER_PAGE
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface
import com.example.proyectoiib_moviesapp.dto.Pelicula
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepositoryS (private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Pelicula>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactorySearch

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Pelicula>> {
        moviesDataSourceFactory = MovieDataSourceFactorySearch(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }


}