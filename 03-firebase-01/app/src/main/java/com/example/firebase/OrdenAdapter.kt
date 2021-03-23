package com.example.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.dto.FirestoreOrdenDto
import kotlinx.android.synthetic.main.adapter_orden.view.*



class OrdenAdapter(private val ordenes:ArrayList<FirestoreOrdenDto>)
    :RecyclerView.Adapter<OrdenAdapter.OrdenViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenViewHolder {
        return OrdenViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_orden,parent,false)
        )
    }

    override fun getItemCount() = ordenes.size

    override fun onBindViewHolder(holder: OrdenViewHolder, position: Int) {
        val orden = ordenes[position]
        holder.view.text_title.text = orden.toString()
    }

    class OrdenViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<FirestoreOrdenDto>) {
        ordenes.clear()
        ordenes.addAll(newList)
    }

}