package com.collect.invest.dao.entity

data class WalletsEntity (
    val id: Long,
    val userId: Long,
    val balance: Double,
    val status: String,
)
