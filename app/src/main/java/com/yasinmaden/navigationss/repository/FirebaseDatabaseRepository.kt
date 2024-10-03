package com.yasinmaden.navigationss.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.yasinmaden.navigationss.data.model.product.ProductDetails
import javax.inject.Inject

class FirebaseDatabaseRepository @Inject constructor(
    private val databaseReference: DatabaseReference,
) {
    fun setValue(data: Any){
        databaseReference.setValue(data)
    }
    fun setFavoriteItem(user: FirebaseUser, product: ProductDetails){
        val favoriteRef = databaseReference.child(user.uid).child("favorites")
        favoriteRef.push().setValue(product)
    }
}