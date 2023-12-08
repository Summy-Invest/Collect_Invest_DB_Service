package com.collect.invest.dao.entity

import java.time.LocalDateTime

data class StockPortfolioEntity(
    val id: Int,
    val date: LocalDateTime,
    val count: Int,
    val collectibleId: Int,
    val userId: Int,
    val totalPrice: Double,
    val transactionId: Int
)
