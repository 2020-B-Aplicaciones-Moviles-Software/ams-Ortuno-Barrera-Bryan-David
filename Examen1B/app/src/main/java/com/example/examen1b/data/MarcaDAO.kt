package com.example.examen1b.data

import androidx.room.*


@Dao
interface MarcaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMarca(marca: Marca)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateMarca(marca: Marca)

    @Delete
    fun deleteMarca(marca: Marca)

    @Query("SELECT *FROM Marca ORDER BY idMarca ASC")
    fun readData(): List<Marca>

    @Query("SELECT *FROM Marca WHERE idMarca=:id_Marca")
        suspend fun getMarca(id_Marca:Int):List<Marca>

    @Transaction
    @Query("SELECT *FROM Marca WHERE idMarca = :id_Marca")
    suspend fun getMarcaConCelulares(id_Marca: Int): List<MarcaConCelulares>

    @Query("DELETE FROM Marca")
    fun nukeTable()
}