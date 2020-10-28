package com.circleappsstudio.tragos.data

import com.circleappsstudio.tragos.AppDatabase
import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkEntity
import com.circleappsstudio.tragos.domain.DataSource
import com.circleappsstudio.tragos.vo.Resource
import com.circleappsstudio.tragos.vo.RetrofitClient

class DataSourceImpl(private val appDatabase: AppDatabase): DataSource {

    override suspend fun getTragoByName(tragoName: String): Resource<List<Drink>> {
        return Resource.Success(RetrofitClient.webservice.getTragoByName(tragoName).drinkList)
    }

    override suspend fun insertTragoIntoRoom(trago: DrinkEntity) {
        appDatabase.tragoDao().insertFavorite(trago)
    }

    override suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>> {
        return Resource.Success(appDatabase.tragoDao().getAllFavoriteDrinks())
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        appDatabase.tragoDao().deleteDrink(drink)
    }

}