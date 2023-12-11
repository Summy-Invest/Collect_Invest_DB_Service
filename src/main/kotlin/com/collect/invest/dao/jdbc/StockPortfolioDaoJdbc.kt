package com.collect.invest.dao.jdbc

import Utils.DateUtils
import com.collect.invest.dao.StockPortfolioDao
import com.collect.invest.dao.entity.StockPortfolioEntity
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class StockPortfolioDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : StockPortfolioDao {

    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = this@StockPortfolioDaoJdbc.url
            this.username = this@StockPortfolioDaoJdbc.username
            this.password = this@StockPortfolioDaoJdbc.password
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

    override fun addPurchase(entity: StockPortfolioEntity) {
        getConnection().use { connection ->
            val sql = "INSERT INTO stock_portfolio (order_date, shares_count, collectible_id, user_id, total_price, transaction_id) VALUES (?, ?, ?, ?, ?);"
            connection.prepareStatement(sql).use { statement ->
                statement.setDate(1, DateUtils.localDateTimeToSqlDate(entity.date))
                statement.setInt(2, entity.count)
                statement.setLong(3, entity.collectibleId)
                statement.setLong(4, entity.userId)
                statement.setDouble(5, entity.totalPrice)
                statement.setLong(6, entity.transactionId)

                statement.executeQuery()
            }
        }
    }

    override fun getUserCollectibles(userId: Long, collectibleId: Long): Int {

        getConnection().use { connection ->
            val sql = "SELECT SUM(shares_count) AS total_shares FROM stock_portfolio WHERE collectible_id = ? AND user_id = ?;"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, collectibleId)
                statement.setLong(2, userId)

                val result = statement.executeQuery()
                if (result.next()) {
                    return result.getInt("shares_count")
                } else {
                    throw NoSuchElementException("Not found")
                }
            }
        }

    }

    fun close() {
        dataSource.close()
    }


}
