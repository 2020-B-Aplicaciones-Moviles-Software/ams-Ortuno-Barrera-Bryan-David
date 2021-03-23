package com.example.proyectoiib_moviesapp.dataVideo.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.proyectoiib_moviesapp.AuthPelicula
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface
import com.example.proyectoiib_moviesapp.dto.Pelicula
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.example.proyectoiib_moviesapp.dataVideo.api.FIRST_PAGE
import com.example.proyectoiib_moviesapp.dto.Actor

class ActorDataSource (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable,movieId:Int)
    : PageKeyedDataSource<Int, Actor>(){

    private var movieID = movieId

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Actor>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getMovieCast(movieID)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.actorCast, null,null)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("MovieDataSource", it1) }
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Actor>) {
//        networkState.postValue(NetworkState.LOADING)
//
//        compositeDisposable.add(
//            apiService.getPopularMovie(params.key)
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                    {
//                        if(it.totalPages >= params.key) {
//                            callback.onResult(it.movieList, params.key+1)
//                            networkState.postValue(NetworkState.LOADED)
//                        }
//                        else{
//                            networkState.postValue(NetworkState.ENDOFLIST)
//                        }
//                    },
//                    {
//                        networkState.postValue(NetworkState.ERROR)
//                        it.message?.let { it1 -> Log.e("MovieDataSource", it1) }
//                    }
//                )
//        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Actor>) {

    }
}