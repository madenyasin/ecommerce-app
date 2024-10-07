package com.yasinmaden.ecommerceapp.ui.components

import com.yasinmaden.ecommerceapp.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val iconRes: Int
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        iconRes = R.drawable.home
    )
    data object Wishlist : BottomBarScreen(
        route = "WISHLIST",
        title = "Wishlist",
        iconRes = R.drawable.favorites
    )
    data object Cart : BottomBarScreen(
        route = "CART",
        title = "Cart",
        iconRes = R.drawable.cart
    )
    data object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "Profile",
        iconRes = R.drawable.person
    )
}