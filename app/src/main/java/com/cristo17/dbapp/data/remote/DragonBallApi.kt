package com.cristo17.dbapp.data.remote

import com.cristo17.dbapp.data.remote.dto.CharacterDetailResponse
import com.cristo17.dbapp.data.remote.dto.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): CharacterListResponse

    @GET("characters/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Int
    ): CharacterDetailResponse
}