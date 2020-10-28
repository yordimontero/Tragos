package com.circleappsstudio.tragos.domain

import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkEntity
import com.circleappsstudio.tragos.vo.Resource

interface Repo {

    suspend fun getTragosList(tragoName: String) : Resource<List<Drink>>

    suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>>

    suspend fun insertTrago(trago: DrinkEntity)

    suspend fun deleteDrink(drink: DrinkEntity)

}