package com.collect.invest.dao

import com.collect.invest.dao.entity.UsersEntity
import com.collect.invest.dao.entity.WalletsEntity

interface WalletsDao {
    fun createWallet(entity: WalletsEntity)
    fun getById(userId: Long): WalletsEntity?
    fun topupBalance(amount: Int, userId: Long): String
    fun withdrawBalance(amount: Int, userId: Long): String
    fun updateStatus(userId: Long, status: String)

}