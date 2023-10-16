package com.collect.invest.dao

import com.collect.invest.dao.entity.UsersEntity

interface UsersDao {
    fun saveUser(entity: UsersEntity)
    fun getById(id: Long): UsersEntity?
}