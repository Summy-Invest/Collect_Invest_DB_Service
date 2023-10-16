package com.collect.invest.dao

import com.collect.invest.dao.entity.UsersEntity
import com.collect.invest.dao.entity.WalletsEntity

interface WalletsDao {
    fun createWallet(entity: WalletsEntity)
    fun getById(id: Long): WalletsEntity?
    fun increaseBalance(amount: Int, userId: Long): String
    fun decreaseBalance(amount: Int, userId: Long): String

}