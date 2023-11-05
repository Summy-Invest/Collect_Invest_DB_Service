package com.collect.invest.dao.jdbc

import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.WalletsEntity
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class WalletsDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : WalletsDao {
    init {
        // Загружаем драйвер JDBC (указываем драйвер для вашей конкретной базы данных)
        Class.forName("org.postgresql.Driver")
    }
    private fun getConnection(): Connection {
        return DriverManager.getConnection(url, username, password)
    }


    override fun createWallet(id: Long) {
        getConnection().use {connection ->
            val sql = "INSERT INTO wallets (user_id) VALUES(?)"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                statement.executeUpdate()
            }
        }
    }

    override fun getById(userId: Long): WalletsEntity? {
        getConnection().use { connection ->
            val sql = "SELECT * FROM wallets WHERE user_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, userId)
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) {
                    extractWalletFromResultSet(resultSet)
                } else {
                    null
                }
            }
        }
    }

    override fun updateStatus(userId: Long, status: String) {
        getConnection().use { connection ->
            val sql = "UPDATE wallets SET status = ? WHERE user_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, status)
                statement.setLong(2, userId)
                statement.executeQuery()
            }
        }
    }

    private fun extractWalletFromResultSet(resultSet: ResultSet): WalletsEntity {
        val id = resultSet.getLong("wallet_id")
        val userId = resultSet.getLong("user_id")
        val balance = resultSet.getInt("money")
        val status = resultSet.getString("status")
        return WalletsEntity(id, userId, balance, status)
    }

    override fun topupBalance(userId: Long, amount: Int): String {
        getConnection().use { connection ->
            val sql = "UPDATE wallets SET money = money + ? WHERE user_id = ?"
            connection.prepareStatement(sql).use { statement ->
                try {
                    statement.setInt(1, amount)
                    statement.setLong(2, userId)
                }catch (e: Throwable){
                    return "Ошибка"
                }
                return "Баланс пополнен на $amount"

            }
        }
    }

    override fun withdrawBalance(userId: Long, amount: Int): String {
        getConnection().use { connection ->
            val sql = "UPDATE wallets SET money = money - ? WHERE user_id = ?"
            connection.prepareStatement(sql).use { statement ->
                try {
                    statement.setInt(1, amount)
                    statement.setLong(2, userId)
                }catch (e: Throwable){
                    return "Ошибка"
                }
                return "Списание на сумму $amount"

            }
        }
    }


}