package com.circleappsstudio.tragos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.circleappsstudio.tragos.data.model.DrinkEntity
import com.circleappsstudio.tragos.domain.TragosDao

@Database(entities = arrayOf(DrinkEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tragoDao(): TragosDao

    //Crear solamente una instancia de la base de datos en toda la aplicación.
    //Singleton = verificar si algo fue hecho la primera vez.
    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{

            //Si la instancia es null...
            //"?:" Elvis Operator.
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "tabla_tragos").build()

            return INSTANCE!!

        }

        fun destroyInstance(){
            INSTANCE = null
        }

    }

}