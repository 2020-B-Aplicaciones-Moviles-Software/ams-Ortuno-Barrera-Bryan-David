package com.example.proyectoiib_moviesapp.ui.home.actors

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.proyectoiib_moviesapp.dataVideo.Repository.*
import com.example.proyectoiib_moviesapp.dataVideo.api.POST_PER_PAGE
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface
import com.example.proyectoiib_moviesapp.dto.Actor
import com.example.proyectoiib_moviesapp.dto.Pelicula
import io.reactivex.disposables.CompositeDisposable

class ActorPagedListRepository (private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Actor>>
    lateinit var moviesCastDataSourceFactory: ActorDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable, movieId:Int) : LiveData<PagedList<Actor>> {
        moviesCastDataSourceFactory = ActorDataSourceFactory(apiService, compositeDisposable,movieId)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesCastDataSourceFactory, config).build()

        return moviePagedList
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesCastDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
//    }


}