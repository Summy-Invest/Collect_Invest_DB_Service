package com.collect.invest.dao

import com.collect.invest.dao.entity.StockPortfolioEntity

interface StockPortfolioDao {

    fun addPurchase(entity: StockPortfolioEntity)
    fun addSale(entity: StockPortfolioEntity)


}

