package com.circleappsstudio.tragos.domain

import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkEntity
import com.circleappsstudio.tragos.vo.Resource

interface DataSource {

    suspend fun getTragoByName(tragoName: String): Resource<List<Drink>>

    suspend fun insertTragoIntoRoom(trago: DrinkEntity)

    suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>>

    suspend fun deleteDrink(drink: DrinkEntity)

}