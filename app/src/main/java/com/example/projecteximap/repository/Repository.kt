package com.example.projecteximap.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.projecteximap.api.GRPCResponse
import com.example.projecteximap.model.GRPCDatabase
import com.example.projecteximap.model.GRPCMediator
import com.example.projecteximap.model.PostDetails
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class Repository(private val apiInstance: GRPCResponse, private val database: GRPCDatabase) {

//    suspend fun feedDetailsRequest(pageNumber: Int): MutableState<MutableList<UserPostProto>> {
//        return apiInstance.getFeedValues(pageNumber)
//    }
//
//    suspend fun postDetailsRequest(postId: String): UserPostProto {
//        return apiInstance.getPostDetails(postId)
//    }

    fun getAllPosts(): Flow<PagingData<PostDetails>> {
        val pagingSourceFactory = { database.postDetailsDao().getAllData() }
        return Pager(
            config = PagingConfig(10),
            remoteMediator = GRPCMediator(apiInstance, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}