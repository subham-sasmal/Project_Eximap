package com.example.projecteximap.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GRPC_REMOTE_KEYS_TABLE")
data class GRPCRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
