package com.vnedomovnyi.randomusersmvi.retrofit.response

import com.google.gson.annotations.SerializedName
import com.vnedomovnyi.randomusersmvi.entity.User

data class GetUsersResponse(@SerializedName("results") val users: List<ApiUser>)

data class ApiUser(
    @SerializedName("name") val name: Name,
    @SerializedName("email") val email: String,
    @SerializedName("location") val location: Location,
    @SerializedName("picture") val picture: Picture
)

fun ApiUser.toUser(): User {
    return User(
        firstName = name.first,
        lastName = name.last,
        email = email,
        city = location.city,
        state = location.state,
        photoUrl = picture.largePhotoUrl,
    )
}

data class Name(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)

data class Location(
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String
)

data class Picture(@SerializedName("large") val largePhotoUrl: String)