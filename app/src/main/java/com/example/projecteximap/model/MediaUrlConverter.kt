package com.example.projecteximap.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlang.social.MediaUrl

class MediaUrlConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMediaList(
        mediaList: MutableList<MediaUrl>
    ): String {
        return gson.toJson(mediaList)
    }

    @TypeConverter
    fun toMediaList(
        mediaListJson: String
    ): MutableList<MediaUrl> {
        val listType = object : TypeToken<MutableList<MediaUrl>>() {}.type
        return gson.fromJson(mediaListJson, listType)
    }
}