package com.collect.invest.dao.entity

data class StockPortfolioEntity(
    val id: Long,
    val date: String,
    val count: Int,
    val collectibleId: Long,
    val userId: Long,
    val totalPrice: Double,
    val transactionId: Long
)
