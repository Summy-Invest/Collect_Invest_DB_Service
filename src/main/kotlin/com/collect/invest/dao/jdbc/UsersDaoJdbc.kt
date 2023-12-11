package com.collect.invest.dao.jdbc

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.entity.UsersEntity
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.ResultSet

class UsersDaoJdbc(
        private val url: String,
        private val username: String,
        private val password: String
) : UsersDao {

    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = this@UsersDaoJdbc.url
            this.username = this@UsersDaoJdbc.username
            this.password = this@UsersDaoJdbc.password
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

    override fun saveUser(entity: UsersEntity){
        getConnection().use { connection ->
            val sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?);"
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, entity.name)
                statement.setString(2, entity.email)
                statement.setString(3, entity.password)

                statement.executeUpdate()
            }
        }
    }



    override fun getById(id: Long): UsersEntity? {
        getConnection().use { connection ->
            val sql = "SELECT * FROM users WHERE user_id = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) {
                    extractUserFromResultSet(resultSet)
                } else {
                    null
                }
            }
        }
    }

    private fun extractUserFromResultSet(resultSet: ResultSet): UsersEntity {
        val id = resultSet.getLong("user_id")
        val name = resultSet.getString("name")
        val email = resultSet.getString("email")
        val password = resultSet.getString("password")
        return UsersEntity(id, name, email, password)
    }


    override fun getByEmail(email: String): UsersEntity? {
        getConnection().use { connection ->
            val sql = "SELECT * FROM users WHERE email = ?"
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, email)
                val resultSet = statement.executeQuery()
                return if (resultSet.next()) {
                    extractUserFromResultSet(resultSet)
                } else {
                    null
                }
            }
        }
    }

    override fun checkPassword(password: String, storedPassword: String): Boolean {
        return password == storedPassword
    }

    fun close() {
        dataSource.close()
    }
}
