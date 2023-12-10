package com.collect.invest.dao
import com.collect.invest.dao.entity.TransactionsEntity

interface TransactionsDao {
    fun saveTransaction(entity: TransactionsEntity): Long
    fun getById(id: Long): TransactionsEntity?
    fun updateStatus(transaction: TransactionsEntity)
}
