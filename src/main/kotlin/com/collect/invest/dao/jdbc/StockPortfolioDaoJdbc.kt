package com.collect.invest.dao.jdbc

import Utils.DateUtils
import com.collect.invest.dao.StockPortfolioDao
import com.collect.invest.dao.entity.StockPortfolioEntity
import com.collect.invest.dao.entity.UsersEntity
import java.sql.Connection
import java.sql.DriverManager

class StockPortfolioDaoJdbc(
    private val url: String,
    private val username: String,
    private val password: String
) : StockPortfolioDao {

    init {
        // Загружаем драйвер JDBC (указываем драйвер для вашей конкретной базы данных)
        Class.forName("org.postgresql.Driver")
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection(url, username, password)
    }
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

    override fun addSale(entity: StockPortfolioEntity) {
        TODO("Not yet implemented")
    }


}
