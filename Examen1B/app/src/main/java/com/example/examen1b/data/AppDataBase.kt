package com.example.examen1b.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.examen1b.Converters


@Database(entities = [Marca::class,Celular::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun celularDAO(): CelularDAO
    abstract fun marcaDAO(): MarcaDAO

    companion object {
        @Volatile private var instance : AppDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "AppDataBase"
        ).fallbackToDestructiveMigration().build()
    }
}