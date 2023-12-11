package com.collect.invest.dao.entity

import java.time.LocalDateTime

data class StockPortfolioEntity(
    val id: Long,
    val date: LocalDateTime,
    val count: Int,
    val collectibleId: Long,
    val userId: Long,
    val totalPrice: Double,
    val transactionId: Long
)
