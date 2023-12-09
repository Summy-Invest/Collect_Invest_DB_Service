package com.collect.invest.dao.entity

data class CollectablesEntity(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val photoLink: String,
    val currentPrice: Double,
    val availableShares: Int
);
