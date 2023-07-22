package com.example.projecteximap.model

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDetailsDAO {
    @Query("SELECT * FROM PostDetails")
    fun getAllData(): PagingSource<Int, PostDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(data: List<PostDetails>)

    @Query("DELETE FROM PostDetails")
    suspend fun deleteItems()
}