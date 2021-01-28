package ise308.aydemir.alp.cities

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQL(var context : Context) : SQLiteOpenHelper(context,"CITYDB",null,1){

    companion object{
        const val TAG = "SQL"
        val id = "id"
        val nameC= "name"
        val populationC= "population"
        val countryC = "country"
        val isVisitedC = "visited"
    }
    val tableName="city"
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableSQL = "CREATE TABLE $tableName(" +
                "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$nameC VARCHAR(100), $populationC Int, " +
                "$countryC VARCHAR(100),$isVisitedC Int )"
        p0!!.execSQL(createTableSQL)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertIntoCity(city:City){
        Log.i(TAG,"insertIntoCity: insert city")
        val db = this.writableDatabase
        val isVisitedInt = if(city.isVisited!!){1}else{0}
        val insertQuery = "INSERT INTO $tableName($nameC,$populationC,$countryC,$isVisitedC)" +
                " VALUES('${city.name}','${city.population}','${city.country}','$isVisitedInt')"

        db.execSQL(insertQuery)
    }

    fun deleteCity(idParam :Long){
        Log.i(TAG,"deleteCity: delete city")
        val db = this.writableDatabase
        val deleteSql = "DELETE FROM $tableName WHERE $id = $idParam"
        db.execSQL(deleteSql)
    }

    fun updateCity(city : City){
        Log.i(TAG,"updateCity: update city")
        val db = this.writableDatabase
        val isVisitedInt = if(city.isVisited!!){1}else{0}
        val updateSql = "UPDATE $tableName SET $nameC = '${city.name}'," +
                "$populationC = '${city.population}' , $countryC = '${city.country}', $isVisitedC = '$isVisitedInt' " +
                "WHERE $id = ${city.id}"
        db.execSQL(updateSql)
    }

    fun selectAllCity():ArrayList<City>{
        Log.i(TAG,"selectAllCity: read all city")
        val db = this.readableDatabase
        val cities = ArrayList<City>()
        val sql = "SELECT * FROM $tableName"
        val crs = db.rawQuery(sql,null)
        if(crs.moveToFirst()){
            while(!crs.isAfterLast){
                var city = City()
                city.id = crs.getLong(crs.getColumnIndex(id))
                city.name = crs.getString(crs.getColumnIndex(nameC))
                city.population = crs.getInt(crs.getColumnIndex(populationC))
                city.country = crs.getString(crs.getColumnIndex(countryC))
                city.isVisited = crs.getInt(crs.getColumnIndex(isVisitedC))==1
                cities.add(city)
                crs.moveToNext()
            }
        }
        return cities
    }

    fun closeDB(){
        this.close()
    }
}