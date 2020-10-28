package com.circleappsstudio.tragos.ui.viewmodel

import androidx.lifecycle.*
import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkEntity
import com.circleappsstudio.tragos.domain.Repo
import com.circleappsstudio.tragos.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo : Repo) : ViewModel(){

    //Prueba GIT

    private val tragosData = MutableLiveData<String>()

    fun setTrago(tragoName: String){
        tragosData.value = tragoName
    }

    init {
        setTrago("margarita")
    }

    var fetchTragosList = tragosData.distinctUntilChanged().switchMap { nombreTrago ->

        liveData(Dispatchers.IO) {

            emit(Resource.Loading())

            try {

                emit(repo.getTragosList(nombreTrago))

            } catch (e : Exception) {
                emit(Resource.Failure(e))
            }

        }

    }

    fun guardarTrago(trago: DrinkEntity){

        //"viewModelScope" se encarga de limpiar su proceso cuando el ViewModel es destruido.
        //".launch" da un Scope de Corutinas, que nos permite utilizar Corutinas.
        viewModelScope.launch {
            repo.insertTrago(trago)
        }

    }

    fun getTragosFavoritos() = liveData(Dispatchers.IO) {

        emit(Resource.Loading())

        try {

            emit(repo.getTragosFavoritos())

        } catch (e : Exception) {
            emit(Resource.Failure(e))
        }

    }

    fun deleteDrink(drink: DrinkEntity){

        viewModelScope.launch {
            repo.deleteDrink(drink)
        }

    }

}