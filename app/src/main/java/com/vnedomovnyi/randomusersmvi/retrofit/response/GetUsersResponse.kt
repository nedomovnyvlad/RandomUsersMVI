package com.vnedomovnyi.randomusersmvi.retrofit.response

import com.google.gson.annotations.SerializedName
import com.vnedomovnyi.randomusersmvi.entity.User

data class GetUsersResponse(@SerializedName("results") val users: List<ApiUser>)

data class ApiUser(
    @SerializedName("name") val name: Name,
    @SerializedName("picture") val picture: Picture
)

fun ApiUser.toUser(): User {
    return User(
        firstName = name.first,
        lastName = name.last,
        photoUrl = picture.largePhotoUrl,
    )
}

data class Name(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)

data class Picture(@SerializedName("large") val largePhotoUrl: String)