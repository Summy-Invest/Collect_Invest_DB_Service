package com.collect.invest.service

import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.WalletsEntity
class WalletService(
    private val walletsDao: WalletsDao
) {
    suspend fun updateStatus(userId: Long, status: String) {
        walletsDao.updateStatus(userId, status)
    }

    suspend fun topupBalance(amount: Int, userId: Long) {
        walletsDao.topupBalance(amount, userId)
    }

    suspend fun withdrawBalance(amount: Int, userId: Long): String {
        return walletsDao.withdrawBalance(amount, userId)
    }

    suspend fun saveWallet(wallet: WalletsEntity) {
        // TODO
    }

    suspend fun getWallet(wallet: WalletsEntity): WalletsEntity{
        // TODO
        return wallet
    }
}