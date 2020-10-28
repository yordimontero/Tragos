package com.circleappsstudio.tragos.domain

import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("search.php")
    suspend fun getTragoByName(@Query("s") tragoName: String): DrinkList

}