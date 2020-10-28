package com.circleappsstudio.tragos.domain

import androidx.room.*
import com.circleappsstudio.tragos.data.model.Drink
import com.circleappsstudio.tragos.data.model.DrinkEntity

@Dao
interface TragosDao {
    //Dao = Data Access Object.

    @Query("SELECT * FROM tragosEntity")
    suspend fun getAllFavoriteDrinks() : List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(trago: DrinkEntity)

    /*@Delete
    suspend fun deleteDrink(trago: Drink)*/

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)

}