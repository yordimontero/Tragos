package com.circleappsstudio.tragos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.circleappsstudio.tragos.AppDatabase
import com.circleappsstudio.tragos.R
import com.circleappsstudio.tragos.data.DataSourceImpl
import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkEntity
import com.circleappsstudio.tragos.domain.RepoImpl
import com.circleappsstudio.tragos.ui.viewmodel.MainViewModel
import com.circleappsstudio.tragos.ui.viewmodel.VMFactory
import com.circleappsstudio.tragos.vo.Resource
import kotlinx.android.synthetic.main.fragment_favoritos.*

class FavoritosFragment : Fragment(), MainAdapter.OnTragoClickListener {

    //"activityViewModels<ViewModel>" atacha el ViewModel a la Activity. Se utiliza para usar la misma instancia del ViewModel en toda la aplicación.
    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            RepoImpl(
                DataSourceImpl(
                    AppDatabase.getDatabase(requireActivity().applicationContext)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupObservers()

    }

    private fun setupObservers(){

        viewModel.getTragosFavoritos().observe(viewLifecycleOwner, Observer { result ->

            when (result) {

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val lista = result.data.map {
                        //Mapeo de DrinkEntity (it) a Drink (lista)
                        Drink(it.tragoId, it.imagen, it.nombre, it.descripcion, it.hasAlcohol)
                    }
                    rv_tragos_favoritos.adapter = MainAdapter(requireContext(), lista, this)
                }

                is Resource.Failure -> {

                }

            }

        })

    }

    private fun setupRecyclerView(){
        rv_tragos_favoritos.layoutManager = LinearLayoutManager(requireContext())
        rv_tragos_favoritos.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onTragoClick(drink: Drink, position: Int) {

        val elemento = DrinkEntity(drink.tragoId, drink.imagen, drink.nombre, drink.descripcion, drink.hasAlcohol)

        viewModel.deleteDrink(elemento)

        rv_tragos_favoritos.adapter?.notifyItemRemoved(position)

        rv_tragos_favoritos.adapter?.notifyItemRangeRemoved(position, rv_tragos_favoritos.adapter?.itemCount!!)

        Toast.makeText(requireContext(), "${elemento.nombre} eliminado de Favoritos", Toast.LENGTH_SHORT).show()

    }

}