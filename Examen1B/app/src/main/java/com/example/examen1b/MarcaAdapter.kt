package com.example.examen1b

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.examen1b.data.Marca
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.adapter_marca.view.*


class MarcaAdapter(private val marcas:ArrayList<Marca>, private val listener: OnAdapterListener)
    :RecyclerView.Adapter<MarcaAdapter.MarcaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarcaViewHolder {
        return MarcaViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_marca,parent,false)
        )
    }

    override fun getItemCount() = marcas.size

    override fun onBindViewHolder(holder: MarcaViewHolder, position: Int) {
        val marca = marcas[position]
        holder.view.text_title.text = marca.nombre
        holder.view.text_title.setOnLongClickListener{
            listener.onClick(marca,it)
        }
        holder.view.text_title.setOnClickListener{
            listener.onSimpleClick(marca)
        }
    }

    class MarcaViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Marca>) {
        marcas.clear()
        marcas.addAll(newList)
    }

    interface OnAdapterListener{
        fun onClick(marcas: Marca,view:View):Boolean
        fun onSimpleClick(marcas: Marca)
    }
}