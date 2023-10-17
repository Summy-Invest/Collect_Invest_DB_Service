package com.collect.invest.service

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.entity.UsersEntity

class UserService(
    private val usersDao: UsersDao
) {
    suspend fun validateUser(user: UsersEntity): Boolean {
        // TODO
        return true
    }

    suspend fun processUserData(user: UsersEntity): UsersEntity {
        // TODO
        return user
    }

    suspend fun saveUser(user: UsersEntity) {
        // TODO
        usersDao.saveUser(user)
    }


    suspend fun getById(id: Long): UsersEntity? {
        // TODO
        return usersDao.getById(id)
    }


}