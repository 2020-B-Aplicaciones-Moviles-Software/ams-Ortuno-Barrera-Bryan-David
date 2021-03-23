package com.example.proyectoiib_moviesapp.dataVideo.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface
import com.example.proyectoiib_moviesapp.dto.Actor
import io.reactivex.disposables.CompositeDisposable

class ActorDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable,movieId:Int)
    : DataSource.Factory<Int, Actor>() {

    val movieId = movieId
    val moviesLiveDataSource =  MutableLiveData<ActorDataSource>()

    override fun create(): DataSource<Int, Actor> {

        val actorDataSource = ActorDataSource(apiService,compositeDisposable,movieId)

        moviesLiveDataSource.postValue(actorDataSource)
        return actorDataSource
    }

}