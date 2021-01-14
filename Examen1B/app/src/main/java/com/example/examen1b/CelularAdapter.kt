package com.example.examen1b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen1b.data.Celular
import com.example.examen1b.data.MarcaConCelulares
import kotlinx.android.synthetic.main.adapter_marca.view.*


class CelularAdapter(private val celulares:ArrayList<Celular>, private val listener: CelularAdapter.OnAdapterListener) :
    RecyclerView.Adapter<CelularAdapter.CelularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelularViewHolder {
        return CelularViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_celular,parent,false)
        )
    }

    override fun getItemCount() = celulares.size

    override fun onBindViewHolder(holder: CelularViewHolder, position: Int) {
        val celular =  celulares[position]
        holder.view.text_title.text = celular.nombre
        holder.view.text_title.setOnLongClickListener{
             listener.onClick(celular,it)
        }
        holder.view.text_title.setOnClickListener{
            listener.onSimpleClick(celular)
        }
    }

    class CelularViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Celular>) {
        celulares.clear()
        celulares.addAll(newList)
    }

    interface OnAdapterListener{
        fun onClick(celular: Celular, view: View):Boolean
        fun onSimpleClick(celular: Celular)
    }
}