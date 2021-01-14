package com.example.examen1b.data

import androidx.room.*


@Dao
interface CelularDAO {

    @Query("SELECT *FROM Celular WHERE idCelular=:id_Celular")
    suspend fun getCelular(id_Celular:Int):List<Celular>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCelular(celular: Celular)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateCelular(celular: Celular)

    @Delete
    fun deleteCelular(celular: Celular)

    @Query("DELETE FROM Celular")
    fun nukeTable()
}