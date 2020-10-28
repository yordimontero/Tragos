package com.circleappsstudio.tragos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.circleappsstudio.tragos.AppDatabase
import com.circleappsstudio.tragos.R
import com.circleappsstudio.tragos.data.DataSourceImpl
import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.domain.RepoImpl
import com.circleappsstudio.tragos.ui.viewmodel.MainViewModel
import com.circleappsstudio.tragos.ui.viewmodel.VMFactory
import com.circleappsstudio.tragos.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainAdapter.OnTragoClickListener {

    private lateinit var navController: NavController

    //"activityViewModels<ViewModel>" atacha el ViewModel a la Activity. Se utiliza para usar la misma instancia del ViewModel en toda la aplicación.
    private val viewModel by activityViewModels<MainViewModel> { VMFactory(RepoImpl(DataSourceImpl(
        AppDatabase.getDatabase(requireActivity().applicationContext)))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setupRecyclerView()

        setupSearchView()

        setupObservers()

        irAFavoritos()

    }

    override fun onTragoClick(drink: Drink, position: Int) {

        val bundle = Bundle()

        bundle.putParcelable("drink", drink)

        findNavController().navigate(R.id.action_mainFragment_to_tragosDetalleFragment, bundle)
    }

    private fun setupObservers() {

        viewModel.fetchTragosList.observe(viewLifecycleOwner, Observer { result ->

            when (result) {

                is Resource.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    progress_bar.visibility = View.GONE
                    rv_tragos.adapter = MainAdapter(requireContext(), result.data, this)
                }

                is Resource.Failure -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        })

    }

    private fun setupRecyclerView() {

        rv_tragos.layoutManager = LinearLayoutManager(requireContext())
        rv_tragos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

    }

    private fun setupSearchView() {

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.setTrago(p0!!)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

    }

    fun irAFavoritos(){
        btn_ir_favoritos.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_favoritosFragment)
        }
    }

}