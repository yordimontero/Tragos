/*
    Nota sobre los valores genéricos:

    Al extender de una clase que contiene valores genéricos, el tipo de dato que se le pase en dicho
    valor genérico "T" se va a inferir en los demás métodos.


    Ejemplo:
    inner class MainViewHolder(itemView: View): BaseViewHolder<VALORGENERICO = Drink>(itemView) {

        override fun bind(item: VALORGENERICO = Drink, position: Int) {
            //En el método bind(item: T, position: Int) el valor genérico "T" inferió de Drink.
        }

    }

*/

package com.circleappsstudio.tragos.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circleappsstudio.tragos.R
import com.circleappsstudio.tragos.base.BaseViewHolder
import com.circleappsstudio.tragos.data.model.Drink
import kotlinx.android.synthetic.main.tragos_row.view.*

class MainAdapter(private val context: Context,
                  private val tragosList: List<Drink>,
                  private val itemClickListener: OnDrinkClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    /*interface OnDrinkClickListener {
        // Interface para clickar un elemento del RecyclerView.
        fun onDrinkClick(drink: Drink, position: Int)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        // Retorno de la clase encargada de bindear cada elemento del RecyclerView.
        // Infla el resouce de vista (layout) creado para mostrar la información.
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.tragos_row, parent, false)
        )

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            // Bindeo de cada elemento del RecyclerView.
            is MainViewHolder -> holder.bind(tragosList[position], position)
        }

    }

    override fun getItemCount(): Int { return tragosList.size }

    inner class MainViewHolder(itemView: View): BaseViewHolder<Drink>(itemView) {

        override fun bind(item: Drink, position: Int) {
            // Creación de cada elemento a "dibujar" en el RecyclerView.
            Glide.with(context).load(item.imagen).centerCrop().into(itemView.img_trago)
            itemView.txt_titulo.text = item.nombre
            itemView.txt_descripcion.text = item.descripcion

            itemView.setOnClickListener {
                itemClickListener.onDrinkClick(item, position)
            }

        }

    }

}