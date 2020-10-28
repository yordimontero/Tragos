package com.circleappsstudio.tragos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.circleappsstudio.tragos.AppDatabase
import com.circleappsstudio.tragos.R
import com.circleappsstudio.tragos.data.DataSourceImpl
import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkEntity
import com.circleappsstudio.tragos.domain.RepoImpl
import com.circleappsstudio.tragos.ui.viewmodel.MainViewModel
import com.circleappsstudio.tragos.ui.viewmodel.VMFactory
import kotlinx.android.synthetic.main.fragment_tragos_detalle.*

class TragosDetalleFragment : Fragment() {

    private lateinit var drink: Drink
    private lateinit var navController: NavController

    //"activityViewModels<ViewModel>" atacha el ViewModel a la Activity. Se utiliza para usar la misma instancia del ViewModel en toda la aplicación.
    private val viewModel by activityViewModels<MainViewModel>{ VMFactory(RepoImpl(DataSourceImpl(
        AppDatabase.getDatabase(requireActivity().applicationContext)))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            drink = it.getParcelable("drink")!!
        }

        onBackPressed()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tragos_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        Glide.with(requireContext()).load(drink.imagen).centerCrop().into(trago_img)
        trago_title.text = drink.nombre
        trago_desc.text = drink.descripcion

        if (drink.hasAlcohol == "Non_Alcoholic"){
            has_alcohol.text = "¡Contiene Alcohol"
        } else {
            has_alcohol.text = "¡No Contiene Alcohol"
        }

        btn_guardar_trago.setOnClickListener {
            viewModel.guardarTrago(DrinkEntity(drink.tragoId, drink.imagen, drink.nombre, drink.descripcion, drink.hasAlcohol))
            Toast.makeText(requireContext(), "Trago guardado en favoritos.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun onBackPressed(){

        val onBackPressedCallback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                Toast.makeText(requireContext(), "onBackPressed", Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

}