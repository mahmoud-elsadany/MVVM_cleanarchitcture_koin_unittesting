package com.efg.valu.sales.repository.local

import java.lang.reflect.Type
import java.util.*

interface BaseLocalRepo {
    fun getCachedObject(type: Type): Any?
    fun <T> saveObject(instance: T, type: Type, lastModifiedDate: Long = Date().time)
}