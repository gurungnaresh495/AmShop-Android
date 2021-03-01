package com.example.amshop.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.amshop.model.Product
import com.google.gson.internal.bind.DateTypeAdapter

class CartDBHelper(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object
    {
        const val DATABASE_NAME = "amshop"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cart"
        const val COLUMN_CAT_ID = "cat_id"
        const val COLUMN_ID = "id"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_PRODUCT_NAME = "NAME"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_SUB_ID = "sub_id"
        const val COLUMN_DESCRIPTION = "description"

    }

    var columns = arrayOf(COLUMN_ID, COLUMN_PRODUCT_NAME, COLUMN_IMAGE, COLUMN_CAT_ID,
        COLUMN_MRP, COLUMN_PRICE, COLUMN_QUANTITY, COLUMN_SUB_ID, COLUMN_DESCRIPTION )

    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = "create table $TABLE_NAME ($COLUMN_ID char(50), $COLUMN_PRODUCT_NAME varchar, $COLUMN_IMAGE varchar, " +
                "$COLUMN_CAT_ID int, $COLUMN_MRP double, $COLUMN_PRICE double, $COLUMN_QUANTITY int, " +
                "$COLUMN_SUB_ID int, $COLUMN_DESCRIPTION varchar)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addProduct(product: Product)
    {
        var db = writableDatabase
        var cursor = db.query(TABLE_NAME, columns, "$COLUMN_ID = ?",
            arrayOf(product._id), null, null, null)
        if (!cursor.moveToFirst()) {
            var contentValues = ContentValues()
            contentValues.put(COLUMN_ID, product._id)
            contentValues.put(COLUMN_CAT_ID, product.catId)
            contentValues.put(COLUMN_PRODUCT_NAME, product.productName)
            contentValues.put(COLUMN_IMAGE, product.image)
            contentValues.put(COLUMN_MRP, product.mrp)
            contentValues.put(COLUMN_PRICE, product.price)
            contentValues.put(COLUMN_QUANTITY, 1)
            contentValues.put(COLUMN_SUB_ID, product.subId)
            contentValues.put(COLUMN_DESCRIPTION, product.description)
            db.insert(TABLE_NAME, null, contentValues)
        }
        else
            increaseProduct(product)

    }

    fun getAllProducts(): ArrayList<Product>
    {
        var db = readableDatabase
        var productList = ArrayList<Product>()

        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null &&cursor.moveToFirst())
        {
            do {
                productList.add(Product(cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_CAT_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                            cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP)),
                            cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_SUB_ID))
                            ))
            }while(cursor.moveToNext())
        }
        return productList
    }

    fun deleteProduct(product: Product)
    {
        var db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(product._id))
    }

    fun increaseProduct(product: Product)
    {
        var db = writableDatabase
        var contentValues = ContentValues()
        var cursor = db.query(TABLE_NAME, columns, "$COLUMN_ID = ?",
            arrayOf(product._id), null, null, null)
        if (cursor != null &&cursor.moveToFirst())
            contentValues.put(COLUMN_QUANTITY, cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)) + 1)
        db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(product._id))
    }

    fun decreaseProduct(product: Product)
    {
        var db = writableDatabase
        var contentValues = ContentValues()
        var cursor = db.query(TABLE_NAME, columns, "$COLUMN_ID = ?",
            arrayOf(product._id), null, null, null)
        if (cursor != null &&cursor.moveToFirst())
            contentValues.put(COLUMN_QUANTITY, cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)) -1)
        db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(product._id))

        if (getQuantityOfProduct(product) ==0)
            deleteProduct(product)
    }

    fun getQuantityOfProduct(product: Product): Int
    {
        var db = readableDatabase
        var cursor = db.query(TABLE_NAME, columns, "$COLUMN_ID = ?",
            arrayOf(product._id), null, null, null)
        if (cursor != null &&cursor.moveToFirst())
           return cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
        return 0

    }
    fun getNumberOfProducts(): Int
    {
        var db = readableDatabase
        val temp_column= arrayOf("sum($COLUMN_QUANTITY)")
        var cursor = db.query(TABLE_NAME, temp_column, null, null, null, null, null)
        if (cursor != null &&cursor.moveToFirst())
            return cursor.getInt(cursor.getColumnIndex("sum($COLUMN_QUANTITY)"))
        return 0
    }

    fun getPriceTotal(): Double
    {
        var db = readableDatabase
        val temp_column= arrayOf("sum($COLUMN_QUANTITY * $COLUMN_PRICE)")
        var cursor = db.query(TABLE_NAME, temp_column, null, null, null, null, null)
        if (cursor != null &&cursor.moveToFirst())
            return cursor.getDouble(cursor.getColumnIndex("sum($COLUMN_QUANTITY * $COLUMN_PRICE)"))
        return 0.00
    }

    fun getDiscountTotal(): Double
    {
        var db = readableDatabase
        val temp_column= arrayOf("sum($COLUMN_QUANTITY * ($COLUMN_MRP - $COLUMN_PRICE))")
        var cursor = db.query(TABLE_NAME, temp_column, null, null, null, null, null)
        if (cursor != null &&cursor.moveToFirst())
            return cursor.getDouble(cursor.getColumnIndex("sum($COLUMN_QUANTITY * ($COLUMN_MRP - $COLUMN_PRICE))"))
        return 0.00
    }

    fun emptyCart()
    {
        var db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }
}