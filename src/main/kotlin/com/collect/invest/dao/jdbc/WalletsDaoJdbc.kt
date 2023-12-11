package com.collect.invest.dao.jdbc

import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.WalletsEntity
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.ResultSet

class WalletsDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : WalletsDao {

    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = this@WalletsDaoJdbc.url
            this.username = this@WalletsDaoJdbc.username
            this.password = this@WalletsDaoJdbc.password
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
                statement.executeUpdate()
            }
        }
    }

    private fun extractWalletFromResultSet(resultSet: ResultSet): WalletsEntity {
        val id = resultSet.getLong("wallet_id")
        val userId = resultSet.getLong("user_id")
        val balance = resultSet.getDouble("money")
        val status = resultSet.getString("status")
        return WalletsEntity(id, userId, balance, status)
    }

    override fun topupBalance(userId: Long, amount: Double){
        getConnection().use { connection ->
            val sql = "UPDATE wallets SET money = money + ? WHERE user_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setDouble(1, amount)
                statement.setLong(2, userId)
                statement.executeUpdate()
            }
        }
    }

    override fun withdrawBalance(userId: Long, amount: Double){
        getConnection().use { connection ->
            val sql = "UPDATE wallets SET money = money - ? WHERE user_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setDouble(1, amount)
                statement.setLong(2, userId)

                statement.executeUpdate()
            }
        }
    }

    fun close() {
        dataSource.close()
    }
}
