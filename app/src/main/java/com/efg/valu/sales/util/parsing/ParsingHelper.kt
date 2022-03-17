package com.efg.valu.sales.util.parsing

import com.google.gson.*
import java.lang.reflect.Type

object ParsingHelper {
    var gson: Gson? = null
        get() {
            if (field == null) {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapter(
                    CharSequence::class.java,
                    CharSequenceDeserializer()
                )
                field = gsonBuilder.create()
            }
            return field
        }
        private set

//    fun <T> jsonParsing(string: String?, tClass: Class<T>?): T? {
//        return try {
//            gson!!.fromJson(string, tClass)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }

    internal class CharSequenceDeserializer : JsonDeserializer<CharSequence> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            element: JsonElement, type: Type,
            context: JsonDeserializationContext
        ): CharSequence {
            return element.asString
        }
    }
}