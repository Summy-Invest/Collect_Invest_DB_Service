package com.collect.invest.dao.jdbc

import com.collect.invest.dao.TransactionsDao
import com.collect.invest.dao.entity.TransactionsEntity
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.ResultSet

class TransactionsDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : TransactionsDao {

    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = this@TransactionsDaoJdbc.url
            this.username = this@TransactionsDaoJdbc.username
            this.password = this@TransactionsDaoJdbc.password
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
            maximumPoolSize = 10 // Установка максимального размера пула соединений
            minimumIdle = 2 // Установка минимального количества простаивающих соединений в пуле
            idleTimeout = 600000 // 10 минут - время простоя, после которого соединение будет удалено из пула
            maxLifetime = 1800000 // 30 минут - максимальное время жизни соединения
            connectionTimeout = 30000 // 30 секунд - максимальное время ожидания соединения из пула
        }
        dataSource = HikariDataSource(config)
    }

    private fun getConnection() = dataSource.connection



    override fun saveTransaction(entity: TransactionsEntity): Long{
        getConnection().use { connection ->
            val sql = "INSERT INTO transactions (amount, wallet_id) VALUES (?, ?) RETURNING transaction_id;"
            connection.prepareStatement(sql).use { statement ->
                statement.setDouble(1, entity.amount)
                statement.setLong(2, entity.walletId)

                val resultSet = statement.executeQuery()
                if (resultSet.next()) {
                    return resultSet.getLong("transaction_id")
                } else {
                    throw Exception("User was not inserted.")
                }
            }
        }
    }

    override fun getById(id: Long): TransactionsEntity? {
        getConnection().use { connection ->
            val sql = "SELECT * FROM transactions WHERE transaction_id = ?;"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) {
                    extractTransactionFromResultSet(resultSet)
                } else {
                    null
                }
            }
        }
    }

    override fun updateStatus(transaction: TransactionsEntity) {
        getConnection().use { connection ->
            val sql = "UPDATE transactions SET status = ? WHERE transaction_id = ?;"
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, transaction.status)
                statement.setLong(2, transaction.id)
                statement.executeUpdate()
            }
        }
    }

    private fun extractTransactionFromResultSet(resultSet: ResultSet): TransactionsEntity {
        val id = resultSet.getLong("transaction_id")
        val amount = resultSet.getDouble("amount")
        val status = resultSet.getString("status")
        val walletId = resultSet.getLong("wallet_id")
        return TransactionsEntity(id, amount, status, walletId)
    }


    fun close() {
        dataSource.close()
    }
}
