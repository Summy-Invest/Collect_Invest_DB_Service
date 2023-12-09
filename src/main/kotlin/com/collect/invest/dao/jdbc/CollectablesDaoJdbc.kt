package com.collect.invest.dao.jdbc

import com.collect.invest.dao.CollectablesDao
import com.collect.invest.dao.entity.CollectablesEntity
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class CollectablesDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : CollectablesDao {

    init {
        // Загружаем драйвер JDBC (указываем драйвер для вашей конкретной базы данных)
        Class.forName("org.postgresql.Driver")
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection(url, username, password)
    }

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


    private fun extractCollectableFromResultSet(resultSet: ResultSet): CollectablesEntity {
        val id = resultSet.getLong("collectible_id")
        val name = resultSet.getString("name")
        val description = resultSet.getString("description")
        val category = resultSet.getString("category_id")
        val photoLink = resultSet.getString("photo_link")
        val currentPrice = resultSet.getDouble("current_price")
        val availableShares = resultSet.getInt("available_shares")
        return CollectablesEntity(id, name, description, category, photoLink, currentPrice, availableShares)
    }

}
