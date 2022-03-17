package com.efg.valu.sales.model.response

import com.google.gson.annotations.SerializedName


class CacheEntry<T>(

    @SerializedName("obj")
    var obj: T?,
    @SerializedName("lastUpdatedDate")
    var lastUpdatedDate: Long
) {
    constructor(obj: T?) : this(obj, System.currentTimeMillis())
}
