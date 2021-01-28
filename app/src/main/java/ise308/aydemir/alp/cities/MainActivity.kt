package ise308.aydemir.alp.cities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){
    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var cityList : ArrayList<City>
    lateinit var cityAdapter : CityAdapter
    private lateinit var sqlite : SQL

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityList = ArrayList()
        recyclerView= findViewById(R.id.recyclerViewid)
        cityAdapter = CityAdapter(applicationContext,this)
        recyclerView.adapter = cityAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        sqlite = SQL(applicationContext)
        val cities = sqlite.selectAllCity()
        for(city in cities){
            cityAdapter.addNewCityToAdapter(city)
        }
    }

    fun onUpdateDeleteReadAgain(){
        Log.i(TAG,"onUpdateDeleteReadAgain: city list updated.")
        cityAdapter.deleteAllCitiesFromAdapter()
        sqlite = SQL(applicationContext)
        val cities = sqlite.selectAllCity()
        for(city in cities){
            cityAdapter.addNewCityToAdapter(city)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.add_menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        if(item.itemId==R.id.addNewCityButtonID){
            Log.i(TAG,"onOptionsItemSelected: add activity button clicked.")
            startActivityForResult(Intent(applicationContext,NewCityActivity::class.java),1)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            if(data != null){
                cityAdapter.deleteAllCitiesFromAdapter()
                sqlite = SQL(applicationContext)
                val cities = sqlite.selectAllCity()
                for(city in cities){
                    cityAdapter.addNewCityToAdapter(city)
                }
                Log.i(TAG,"onActivityResult: new city added and list reloaded.")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy: sqlite database closed.")
        this.sqlite.closeDB()
    }
}