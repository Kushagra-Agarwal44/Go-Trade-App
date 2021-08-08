package com.kushagrawithmdg.gotrade.Models

import java.io.Serializable

data class User(val uid: String = "",
                val displayName: String? = "",
                val imageUrl: String = "",
                var currencycode : String = "",
                val likedcrypto: ArrayList<String> = ArrayList()):Serializable