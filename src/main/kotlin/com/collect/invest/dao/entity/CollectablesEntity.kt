package com.collect.invest.dao.entity

data class CollectablesEntity(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val photoLink: String,
    val currentPrice: Double,
    var availableShares: Int
);
