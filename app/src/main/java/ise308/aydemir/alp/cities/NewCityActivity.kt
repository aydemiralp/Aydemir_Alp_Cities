package ise308.aydemir.alp.cities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class NewCityActivity : AppCompatActivity(){

    companion object{
        const val TAG = "NewCityActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_city)

        val cityName = findViewById<EditText>(R.id.cityNameEditTextID)
        val cityPopulation = findViewById<EditText>(R.id.populationEditTextID)
        val cityCountry = findViewById<EditText>(R.id.countryEditTextID)
        val isVisited = findViewById<CheckBox>(R.id.isVisitedCheckBoxID)

        findViewById<Button>(R.id.addNewCityButtonID2).setOnClickListener {
            if(cityName.text.toString().isNotEmpty()){
                if(cityPopulation.text.toString().isNotEmpty()){
                    if(cityCountry.text.toString().isNotEmpty()){
                        Log.i(TAG,"addNewCityButton.OnClick: all edit texts are filled.")
                        val city = City(1,cityName.text.toString(),
                            cityPopulation.text.toString().toInt(),cityCountry.text.toString(),isVisited.isChecked)
                        val db = SQL(applicationContext)
                        db.insertIntoCity(city)
                        val intent = Intent()
                        intent.putExtra("isAdded",true)
                        setResult(1,intent)
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Country is empty",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext,"Population is  empty",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext,"City name is empty",Toast.LENGTH_SHORT).show()
            }


        }
    }
}