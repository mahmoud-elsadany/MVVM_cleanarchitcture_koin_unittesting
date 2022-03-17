package com.efg.valu.sales.model.response

import java.io.Serializable

open class BaseModel : Serializable {
    var message: String? = null
    var error: Int = 0
    open class Payload : Serializable
}