package com.collect.invest.dao

import com.collect.invest.dao.entity.UsersEntity

interface UsersDao {
    fun saveUser(entity: UsersEntity)
    fun getById(id: Long): UsersEntity?
    fun getByEmail(email: String): UsersEntity?
    fun checkPassword(password: String, storedPassword: String): Boolean
}
