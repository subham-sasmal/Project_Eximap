package com.example.projecteximap.api

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.projecteximap.constant.ConstantsVal.Companion.metaData
import com.kotlang.social.GetFeedRequest
import com.kotlang.social.GetPostRequest
import com.kotlang.social.UserPostGrpc
import com.kotlang.social.UserPostProto
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.MetadataUtils

class GRPCResponse {
    private val channel = ManagedChannelBuilder.forAddress("social.navachar.co", 50051)
        .intercept(MetadataUtils.newAttachHeadersInterceptor(metaData))
        .usePlaintext() // for testing, not recommended in production
        .build()

    private val client = UserPostGrpc.newBlockingStub(channel)


    suspend fun getFeedValues(pageNumber: Int): MutableList<UserPostProto> {
        val feedRequest = GetFeedRequest.newBuilder()
//            .setPageSize() // Set the page size
            .setPageNumber(pageNumber) // Set the page number
            .build()

        val response = client.getFeed(feedRequest)
        return response.postsList
    }

//    suspend fun getPostDetails(postID: String): UserPostProto {
//        val postRequest = GetPostRequest.newBuilder()
//            .setPostId(postID)
//            .build()
//
//        return client.getPost(postRequest)
//    }

}