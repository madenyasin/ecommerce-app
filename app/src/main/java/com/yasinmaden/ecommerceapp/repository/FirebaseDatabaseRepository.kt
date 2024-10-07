package com.yasinmaden.ecommerceapp.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.yasinmaden.ecommerceapp.data.model.product.ProductDetails
import javax.inject.Inject

class FirebaseDatabaseRepository @Inject constructor(
    private val databaseReference: DatabaseReference,
) {
    fun addFavoriteItem(user: FirebaseUser, product: ProductDetails){
        val favoriteRef = databaseReference.child(user.uid).child("favorites")
        favoriteRef.child(product.id.toString()).setValue(product)
    }

    fun removeFavoriteItem(user: FirebaseUser, product: ProductDetails) {
        val favoriteRef = databaseReference.child(user.uid).child("favorites")
        favoriteRef.child(product.id.toString()).removeValue()
    }

    fun getAllWishlist(user: FirebaseUser, callback: (List<ProductDetails>) -> Unit, onError: (DatabaseError) -> Unit) {
        val favoriteRef = databaseReference.child(user.uid).child("favorites")
        favoriteRef.get().addOnSuccessListener { dataSnapshot ->
            val favoriteList = mutableListOf<ProductDetails>()
            for (snapshot in dataSnapshot.children) {
                val product = snapshot.getValue(ProductDetails::class.java)
                product?.let { favoriteList.add(it) }
            }
            callback(favoriteList)
        }.addOnFailureListener { exception ->
            onError(DatabaseError.fromException(exception))
        }
    }
}