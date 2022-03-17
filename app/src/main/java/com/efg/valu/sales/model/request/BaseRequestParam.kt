package com.efg.valu.sales.model.request

import com.efg.valu.sales.model.response.signin.Account
import java.io.Serializable

open class BaseRequestParam(var account: Account? = null) : Serializable
