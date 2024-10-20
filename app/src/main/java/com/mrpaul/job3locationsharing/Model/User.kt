package com.mrpaul.job3locationsharing.Model

import com.google.firebase.database.PropertyName

data class User(
    val userId:String,
    @get:PropertyName("DisplayName")
    @set:PropertyName("DisplayName")
    var displayname:String="",


    @get:PropertyName("email")
    @set:PropertyName("email")
    var email:String ="",

    @get:PropertyName("location")
    @set:PropertyName("location")
    var location:String =""

){
    constructor():this("","","")
}
