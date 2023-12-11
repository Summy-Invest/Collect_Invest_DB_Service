package com.collect.invest.dao.entity

data class TransactionsEntity (
    val id: Long,
    val amount: Double,
    val status: String,
    val walletId: Long
    )
