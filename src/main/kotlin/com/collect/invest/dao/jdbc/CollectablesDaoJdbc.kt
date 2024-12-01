package com.collect.invest.dao.jdbc

import com.collect.invest.dao.CollectablesDao
import com.collect.invest.dao.entity.CollectablesEntity
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.ResultSet


class CollectablesDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : CollectablesDao {

    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = this@CollectablesDaoJdbc.url
            this.username = this@CollectablesDaoJdbc.username
            this.password = this@CollectablesDaoJdbc.password
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

    override fun getById(id: Long): CollectablesEntity? {
        getConnection().use { connection ->
            val sql = "SELECT * FROM collectibles WHERE collectible_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) {
                    extractCollectableFromResultSet(resultSet)
                } else {
                    null
                }
            }
        }
    }

    override fun getAllCollectables(): List<CollectablesEntity> {
        val collectibles = mutableListOf<CollectablesEntity>()
        getConnection().use { connection ->
            val sql = "SELECT * FROM collectibles"
            connection.prepareStatement(sql).use { statement ->
                val resultSet = statement.executeQuery()
                while (resultSet.next()) {
                    collectibles.add(extractCollectableFromResultSet(resultSet))
                }
            }
        }
        return collectibles
    }

    override fun getPrice(id: Long): Double {
        getConnection().use { connection ->
            val sql = "SELECT current_price FROM collectibles WHERE collectible_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                val resultSet = statement.executeQuery()
                if (resultSet.next()) {
                    return resultSet.getDouble("current_price")
                } else {
                    throw NoSuchElementException("Collectible with ID $id not found")
                }
            }
        }
    }

    override fun updatePrice(id: Long, newPrice: Double) {
        getConnection().use { connection ->
            val sql = "UPDATE collectibles SET current_price = ? WHERE collectible_id = ?;"
            connection.prepareStatement(sql).use { statement ->
                statement.setDouble(1, newPrice)
                statement.setLong(2, id)
                statement.executeUpdate()
            }
        }
    }

    override fun updateCollectible(id: Long, shares: Int) {
        getConnection().use { connection ->
            val sql = "UPDATE collectibles SET available_shares = ? WHERE collectible_id = ?;"
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, shares)
                statement.setLong(2, id)
                statement.executeUpdate()
            }
        }
    }

    private fun extractCollectableFromResultSet(resultSet: ResultSet): CollectablesEntity {
        val id = resultSet.getLong("collectible_id")
        val name = resultSet.getString("name")
        val description = resultSet.getString("description")
        val category = resultSet.getString("category")
        val photo = "http://10.0.2.2:8080/image/" + resultSet.getString("photo_url")
        val currentPrice = resultSet.getDouble("current_price")
        val availableShares = resultSet.getInt("available_shares")
        return CollectablesEntity(id, name, description, category, photo, currentPrice, availableShares)
    }

    fun close() {
        dataSource.close()
    }

}
