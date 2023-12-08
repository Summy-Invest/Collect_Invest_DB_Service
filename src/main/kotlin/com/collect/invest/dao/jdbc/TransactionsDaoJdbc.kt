package com.collect.invest.dao.jdbc

import com.collect.invest.dao.TransactionsDao
import com.collect.invest.dao.entity.TransactionsEntity
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class TransactionsDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : TransactionsDao {
    init {
        // Загружаем драйвер JDBC (указываем драйвер для вашей конкретной базы данных)
        Class.forName("org.postgresql.Driver")
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection(url, username, password)
    }
    override fun saveTransaction(entity: TransactionsEntity): Long{
        getConnection().use { connection ->
            val sql = "INSERT INTO transactions (amount, status, wallet_id) VALUES (?, ?, ?) RETURNING transaction_id;"
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, entity.amount)
                statement.setString(2, entity.status)
                statement.setLong(3, entity.walletId)

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
        val amount = resultSet.getInt("amount")
        val status = resultSet.getString("status")
        val walletId = resultSet.getLong("wallet_id")
        return TransactionsEntity(id, amount, status, walletId)
    }

}
