package com.example.projecteximap.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlang.social.MediaUrl

@Entity(tableName = "PostDetails")
data class PostDetails (
    @PrimaryKey(autoGenerate = false)
    var postId: String,
    var postAuthorImage: String,
    var postAuthorName: String,
    var tags: String,
    var createdOn: Long,
    var postTitle: String,
    var mediaList: MutableList<MediaUrl>,
    var numLikes: Int,
    var numReplies: Int
)