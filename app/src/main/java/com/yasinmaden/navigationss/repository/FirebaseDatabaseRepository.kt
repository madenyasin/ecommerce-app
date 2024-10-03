package com.yasinmaden.navigationss.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.yasinmaden.navigationss.data.model.product.ProductDetails
import javax.inject.Inject

class FirebaseDatabaseRepository @Inject constructor(
    private val databaseReference: DatabaseReference,
) {
    fun addFavoriteItem(user: FirebaseUser, product: ProductDetails){
        val favoriteRef = databaseReference.child(user.uid).child("favorites")
        favoriteRef.child(product.id.toString()).setValue(product)
    }

    // Remove the favorite item from Firebase
    fun removeFavoriteItem(user: FirebaseUser, product: ProductDetails) {
        val favoriteRef = databaseReference.child(user.uid).child("favorites")
        favoriteRef.child(product.id.toString()).removeValue() // Use product.id to directly remove the item
    }
}