package com.collect.invest.dao.jdbc

import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.UsersEntity
import com.collect.invest.dao.entity.WalletsEntity
import io.ktor.network.sockets.*
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

    override fun createWallet(entity: WalletsEntity) {
        getConnection().use {connection ->
            val sql = "INSERT INTO wallets (user_id, status) VALUES(?, ?)"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, entity.userId)
                statement.setString(2, entity.status)
                statement.executeUpdate()
            }
        }
    }

    override fun getById(id: Long): WalletsEntity? {
        getConnection().use { connection ->
            val sql = "SELECT * FROM wallets WHERE wallet_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) {
                    extractWalletFromResultSet(resultSet)
                } else {
                    null
                }
            }
        }
    }

    private fun extractWalletFromResultSet(resultSet: ResultSet): WalletsEntity {
        val id = resultSet.getLong("wallet_id")
        val user_id = resultSet.getLong("user_id")
        val balance = resultSet.getInt("money")
        val status = resultSet.getString("status")
        return WalletsEntity(id, user_id, balance, status)
    }

    override fun increaseBalance(amount: Int, userId: Long): String {
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

    override fun decreaseBalance(amount: Int, userId: Long): String {
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