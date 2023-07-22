package com.example.projecteximap.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDAO {
    @Query("SELECT * FROM GRPC_REMOTE_KEYS_TABLE WHERE id =:id")
    suspend fun getRemoteKeys(id: String): GRPCRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<GRPCRemoteKeys>)

    @Query("DELETE FROM GRPC_REMOTE_KEYS_TABLE")
    suspend fun deleteAllData()
}