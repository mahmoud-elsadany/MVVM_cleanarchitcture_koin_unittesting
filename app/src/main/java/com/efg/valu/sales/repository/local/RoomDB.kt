package com.efg.valu.sales.repository.local

object DatabaseBuilder {}

//    private var INSTANCE: AppDatabase? = null
//    private const val DATABASE_NAME = "VALU-SALES-DB"
//
//    fun getInstance(): AppDatabase {
//        if (INSTANCE == null) {
//            synchronized(AppDatabase::class) {
//                INSTANCE = buildRoomDB(ValuApplication.getContext()!!)
//            }
//        }
//        return INSTANCE!!
//    }
//
//    private fun buildRoomDB(context: Context) =
//        Room.databaseBuilder(
//            context.applicationContext,
//            AppDatabase::class.java,
//            DATABASE_NAME
//        ).build()
//
//}
//
//@Dao
//interface CacheEntryDao {
//    @Query("SELECT * FROM cache WHERE type = :cacheType")
//    suspend fun getCacheEntry(cacheType: String): CacheEntry?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCacheEntry(cacheEntry: CacheEntry?)
//
//    @Delete
//    suspend fun delete(user: CacheEntry?)
//}

//@Database(entities = [CacheEntry::class], version = 1, exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun cacheEntryDao(): CacheEntryDao
//}
//
//
//interface DatabaseHelper {
//    suspend fun getCacheEntry(type: Type?): Any?
//    suspend fun insertCacheEntry(instance: Any?, type: Type)
//}
//
//class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
//    override suspend fun getCacheEntry(type: Type?): Any? {
//        val entry: CacheEntry? =
//            appDatabase.cacheEntryDao().getCacheEntry(type.toString())
//        val cachedObject: String? = entry?.obj
//        return cachedObject?.convertToModel(type)
//    }
//
//
//    override suspend fun insertCacheEntry(
//        instance: Any?, type: Type
//    ) {
//        val cacheEntry = CacheEntry(Gson().toJson(instance), type.toString())
//        appDatabase.cacheEntryDao().insertCacheEntry(cacheEntry)
//    }


