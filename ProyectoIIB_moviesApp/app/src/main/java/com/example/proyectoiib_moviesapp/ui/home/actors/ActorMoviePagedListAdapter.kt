package com.example.proyectoiib_moviesapp.ui.home.actors

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectoiib_moviesapp.R
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dataVideo.api.POSTER_BASE_URL
import com.example.proyectoiib_moviesapp.dto.Actor
import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.example.proyectoiib_moviesapp.ui.home.details.MovieDetails


class ActorMoviePagedListAdapter(val context: Context) : PagedListAdapter<Actor, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.actor_list_item, parent, false)
            return ActorItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as ActorItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }


    class MovieDiffCallback : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.idActor == newItem.idActor
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }
    }


    class ActorItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(actor: Actor?, context: Context) {

            Log.i("info","${actor}")
            itemView.findViewById<TextView>(R.id.cv_actor_title).text = actor?.nombre
            itemView.findViewById<TextView>(R.id.cv_movie_character_title).text =  actor?.personaje

            val moviePosterURL = POSTER_BASE_URL + actor?.foto
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.findViewById<ImageView>(R.id.cv_iv_actor_poster));

//            itemView.setOnClickListener{
//                val intent = Intent(context, MovieDetails::class.java)
//                intent.putExtra("id", actor?.idPelicula)
//                context.startActivity(intent)
//            }
        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {


        fun bind(networkState: NetworkState?) {
            val progress_bar_item = itemView.findViewById<ProgressBar>(R.id.progress_bar_item)
            val error_msg_item = itemView.findViewById<TextView>(R.id.error_msg_item)
            if (networkState != null && networkState == NetworkState.LOADING) {
                progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                error_msg_item.visibility = View.VISIBLE;
                error_msg_item.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                error_msg_item.visibility = View.VISIBLE;
                error_msg_item.text = networkState.msg;
            }
            else {
                error_msg_item.visibility = View.GONE;
            }
        }
    }


//    fun setNetworkState(newNetworkState: NetworkState) {
//        val previousState = this.networkState
//        val hadExtraRow = hasExtraRow()
//        this.networkState = newNetworkState
//        val hasExtraRow = hasExtraRow()
//
//        if (hadExtraRow != hasExtraRow) {
//            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
//                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
//            } else {                                       //hasExtraRow is true and hadExtraRow false
//                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
//            }
//        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
//            notifyItemChanged(itemCount - 1)       //add the network message at the end
//        }
//
//    }

}