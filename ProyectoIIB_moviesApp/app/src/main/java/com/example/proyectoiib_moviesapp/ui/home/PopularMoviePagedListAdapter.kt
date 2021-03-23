package com.example.proyectoiib_moviesapp.ui.home

import android.content.Context
import android.content.Intent
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
import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.example.proyectoiib_moviesapp.ui.home.details.MovieDetails


class PopularMoviePagedListAdapter(val context: Context) : PagedListAdapter<Pelicula, RecyclerView.ViewHolder>(MovieDiffCallback()), Filterable {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
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


    class MovieDiffCallback : DiffUtil.ItemCallback<Pelicula>() {
        override fun areItemsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem.idPelicula == newItem.idPelicula
        }

        override fun areContentsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem == newItem
        }
    }


    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pelicula: Pelicula?, context: Context) {

            itemView.findViewById<TextView>(R.id.cv_movie_title).text = pelicula?.nombre
            itemView.findViewById<TextView>(R.id.cv_movie_release_date).text =  pelicula?.fechaLanzamiento

            val moviePosterURL = POSTER_BASE_URL + pelicula?.uriPortada
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.findViewById<ImageView>(R.id.cv_iv_movie_poster));

            itemView.setOnClickListener{
                val intent = Intent(context, MovieDetails::class.java)
                intent.putExtra("id", pelicula?.idPelicula)
                context.startActivity(intent)
            }
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


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val query = charSequence.toString()
                var filtered: PagedList<Pelicula?>? = null
                if (query.isEmpty()) {
                    filtered = currentList
                } else {
                    for (pelicua in filtered!!) {
                        if (pelicua!!.nombre.toLowerCase().contains(query.toLowerCase())) {
                            filtered!!.add(pelicua)
                        }
                    }
                }
                val results = FilterResults()
                results.count = filtered!!.size
                results.values = filtered
                return results
            }

            override fun publishResults(charSequence: CharSequence?, results: FilterResults) {
                submitList(results.values as PagedList<Pelicula?>)
            }
        }
    }

}