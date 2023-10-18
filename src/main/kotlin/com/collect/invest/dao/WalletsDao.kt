package com.collect.invest.dao

import com.collect.invest.dao.entity.WalletsEntity

interface WalletsDao {
    fun createWallet(id: Long)
    fun getById(userId: Long): WalletsEntity?
    fun topupBalance(userId: Long, amount: Int): String
    fun withdrawBalance( userId: Long, amount: Int): String
    fun updateStatus(userId: Long, status: String)

}