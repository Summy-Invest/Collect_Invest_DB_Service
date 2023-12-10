package com.collect.invest.dao.jdbc

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.entity.UsersEntity
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class UsersDaoJdbc(
        private val url: String,
        private val username: String,
        private val password: String
) : UsersDao {

    init {
        // Загружаем драйвер JDBC (указываем драйвер для вашей конкретной базы данных)
        Class.forName("org.postgresql.Driver")
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection(url, username, password)
    }

    override fun saveUser(entity: UsersEntity): Long {
        getConnection().use { connection ->
            val sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?) RETURNING user_id;"
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, entity.name)
                statement.setString(2, entity.email)
                statement.setString(3, entity.password)

                val resultSet = statement.executeQuery()
                if (resultSet.next()) {
                    return resultSet.getLong("user_id")
                } else {
                    throw Exception("User was not inserted.")
                }
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
}
