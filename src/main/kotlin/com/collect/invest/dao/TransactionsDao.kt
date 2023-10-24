package com.collect.invest.dao
import com.collect.invest.dao.entity.TransactionsEntity

interface TransactionsDao {
    fun saveTransaction(entity: TransactionsEntity)
    fun getById(id: Long): TransactionsEntity?
    fun updateStatus(id: Long, status: String)
}