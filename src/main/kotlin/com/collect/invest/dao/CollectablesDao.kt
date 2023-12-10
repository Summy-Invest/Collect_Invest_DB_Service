package com.collect.invest.dao

import com.collect.invest.dao.entity.CollectablesEntity

interface CollectablesDao {

    fun getById(id: Long): CollectablesEntity?

    fun getAllCollectables(): List<CollectablesEntity>

}
