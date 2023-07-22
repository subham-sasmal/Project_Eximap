package com.example.projecteximap.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.projecteximap.api.GRPCResponse

@OptIn(ExperimentalPagingApi::class)
class GRPCMediator(
    private val grpcApi: GRPCResponse,
    private val grpcDatabase: GRPCDatabase
) : RemoteMediator<Int, PostDetails>() {

    private val postDao = grpcDatabase.postDetailsDao()
    private val remoteKeyDao = grpcDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostDetails>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = grpcApi.getFeedValues(pageNumber = currentPage)
            val endOfPaginationReached = response.isEmpty()

            val postDetailsList = response.map { itemApiResponse ->
                PostDetails(
                    postId = itemApiResponse.postId,
                    postAuthorImage = itemApiResponse.authorInfo.photoUrl,
                    postAuthorName = itemApiResponse.authorInfo.name,
                    tags = if (itemApiResponse.tagsList.size > 0) itemApiResponse.tagsList[0] else "",
                    createdOn = itemApiResponse.createdOn,
                    postTitle = itemApiResponse.post,
                    mediaList = itemApiResponse.mediaUrlsList,
                    numLikes = itemApiResponse.numLikes,
                    numReplies = itemApiResponse.numReplies
                )
            }

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            grpcDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDao.deleteItems()
                    remoteKeyDao.deleteAllData()
                }
                val keys = response.map {
                    GRPCRemoteKeys(
                        id = it.postId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                postDao.insertAllData(data = postDetailsList)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PostDetails>
    ): GRPCRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.postId?.let {
                remoteKeyDao.getRemoteKeys(id = it)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PostDetails>
    ): GRPCRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                remoteKeyDao.getRemoteKeys(id = it.postId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PostDetails>
    ): GRPCRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                remoteKeyDao.getRemoteKeys(id = it.postId)
            }
    }
}