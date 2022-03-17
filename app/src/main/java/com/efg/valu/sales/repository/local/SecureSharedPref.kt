package com.efg.valu.sales.repository.local

import android.content.Context
import android.content.SharedPreferences
import com.efg.valu.sales.ValuApplication
import com.efg.valu.sales.model.response.CacheEntry
import com.efg.valu.sales.util.extention.convertToModel
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.facebook.android.crypto.keychain.AndroidConceal
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain
import com.facebook.crypto.Crypto
import com.facebook.crypto.CryptoConfig
import com.facebook.crypto.Entity
import com.facebook.crypto.exception.CryptoInitializationException
import com.facebook.crypto.exception.KeyChainException
import com.facebook.crypto.keychain.KeyChain
import com.facebook.soloader.SoLoader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.text.ParseException
import java.util.*


object SecureSharedPref : SharedPreferences {

    private val TAG = SharedPreferences::class.java.simpleName
    private val keyChain: KeyChain =
        SharedPrefsBackedKeyChain(ValuApplication.getContext(),
            CryptoConfig.KEY_256)
    private const val sharedPrefFilename: String = "secureShared"
    private val entity: Entity? = Entity.create(sharedPrefFilename)
    private val sharedPreferences: SharedPreferences?
        get() =
            ValuApplication.getContext()
                ?.getSharedPreferences(sharedPrefFilename, Context.MODE_PRIVATE)


    private val crypto: Crypto
        get() = AndroidConceal.get().createCrypto256Bits(keyChain)

    init {
        SoLoader.init(ValuApplication.getContext(), false)
    }

    private fun log(type: Int, str: String) {
        when (type) {
            Log.WARN -> Log.w(TAG, str)
            Log.ERROR -> Log.e(TAG, str)
            Log.DEBUG -> Log.d(TAG, str)
        }
    }

    private fun encrypt(plainText: String?): String? {
        if (TextUtils.isEmpty(plainText)) {
            return plainText
        }

        var cipherText: ByteArray? = null

        if (!crypto.isAvailable) {
            log(Log.WARN, "encrypt: crypto not available")
            return null
        }

        try {
            cipherText = crypto.encrypt(plainText!!.toByteArray(), entity)
        } catch (e: KeyChainException) {
            log(Log.ERROR, "encrypt: $e")
        } catch (e: CryptoInitializationException) {
            log(Log.ERROR, "encrypt: $e")
        } catch (e: IOException) {
            log(Log.ERROR, "encrypt: $e")
        }

        return if (cipherText != null) Base64.encodeToString(cipherText, Base64.DEFAULT) else null
    }

    private fun decrypt(encryptedText: String): String {
        if (TextUtils.isEmpty(encryptedText)) {
            return encryptedText
        }

        var plainText: ByteArray? = null

        if (!crypto.isAvailable) {
            log(Log.WARN, "decrypt: crypto not available")
            return ""
        }

        try {
            plainText = crypto.decrypt(Base64.decode(encryptedText, Base64.DEFAULT), entity)
        } catch (e: KeyChainException) {
            log(Log.ERROR, "decrypt: $e")
        } catch (e: CryptoInitializationException) {
            log(Log.ERROR, "decrypt: $e")
        } catch (e: IOException) {
            log(Log.ERROR, "decrypt: $e")
        }

        return if (plainText != null)
            String(plainText)
        else
            ""
    }

    override fun getAll(): Map<String, *>? {
        return if (sharedPreferences != null) {
            val encryptedMap = sharedPreferences?.all
            val decryptedMap = HashMap<String, String?>(encryptedMap?.size ?: 0)
            if (encryptedMap != null) {
                for ((key, encryptedValue) in encryptedMap) {
                    if (encryptedValue != null) {
                        decryptedMap[key] = decrypt(encryptedValue.toString())
                    }
                }
            }
            decryptedMap
        } else {
            null
        }
    }

    override fun getString(key: String, defValue: String?): String? {
        val encryptedValue = sharedPreferences?.getString(key, defValue)
        return if (encryptedValue != null && encryptedValue != defValue) decrypt(encryptedValue) else defValue
    }

    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        val encryptedValues = sharedPreferences?.getStringSet(key, null) ?: return defValues
        val decryptedValues = HashSet<String>(encryptedValues.size)
        for (encryptedValue in encryptedValues) {
            decryptedValues.add(encryptedValue)
        }
        return decryptedValues
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val encryptedValue = sharedPreferences?.getString(key, null) ?: return defaultValue
        try {
            return Integer.parseInt(decrypt(encryptedValue))
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }

    }

    override fun getLong(key: String, defaultValue: Long): Long {
        val encryptedValue = sharedPreferences?.getString(key, null) ?: return defaultValue
        try {
            return java.lang.Long.parseLong(decrypt(encryptedValue))
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }

    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        val encryptedValue = sharedPreferences?.getString(key, null) ?: return defaultValue
        try {
            return java.lang.Float.parseFloat(decrypt(encryptedValue))
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }

    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val encryptedValue = sharedPreferences?.getString(key, null) ?: return defaultValue
        try {
            return java.lang.Boolean.parseBoolean(decrypt(encryptedValue))
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }

    }

    fun getObject(type: Type): CacheEntry<Any>? {

        val str = getString(type.toString(), "")
        str?.let {
            return str.convertToModel(object :
                TypeToken<CacheEntry<*>?>() {}.type)
        }
        return null
    }

    override fun contains(key: String): Boolean {
        return sharedPreferences?.contains(key) ?: false
    }

    override fun edit() = Editor

    object Editor : SharedPreferences.Editor {
        private var mEditor: SharedPreferences.Editor? = sharedPreferences?.edit()

        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            mEditor?.putString(key, encrypt(value))
            return this
        }

        override fun putStringSet(
            key: String,
            values: Set<String>?
        ): SharedPreferences.Editor {
            val encryptedValues = HashSet<String?>(
                values!!.size
            )
            for (value in values) {
                encryptedValues.add(encrypt(value))
            }
            mEditor?.putStringSet(key, encryptedValues)
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            mEditor?.putString(key, encrypt(value.toString()))
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            mEditor?.putString(key, encrypt(value.toString()))
            return this
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            mEditor?.putString(key, encrypt(value.toString()))
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            mEditor?.putString(key, encrypt(java.lang.Boolean.toString(value)))
            return this
        }

        fun <T> putObject(value: CacheEntry<T>, type: Type): SharedPreferences.Editor {
            try {
                val str = Gson().toJson(value, CacheEntry::class.java)
                putString(type.toString(), str)
            } catch (paresException: ParseException) {

            }
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            mEditor?.remove(key)
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            mEditor?.clear()
            return this
        }

        override fun commit(): Boolean {
            return mEditor?.commit() ?: false
        }


        override fun apply() {
            mEditor?.apply()
        }
    }


    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences?.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences?.unregisterOnSharedPreferenceChangeListener(listener)
    }


}
