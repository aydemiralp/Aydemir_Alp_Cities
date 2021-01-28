package ise308.aydemir.alp.cities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class DialogEdit(var city:City): DialogFragment() {

    companion object {
        const val TAG = "DialogEdit"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_edit_fragment,null)

        val name = view.findViewById<EditText>(R.id.cityNameEdit)
        val population = view. findViewById<EditText>(R.id.populationEdit)
        val country = view.findViewById<EditText>(R.id.countryEdit)
        val isVisitedEdit = view.findViewById<CheckBox>(R.id.isVisitedEdit)
        //fill edit text fields with city arguments
        name.setText(city.name)
        population.setText(city.population.toString())
        country.setText(city.country)
        isVisitedEdit.isChecked = city.isVisited!!

        view.findViewById<Button>(R.id.editButtonId).setOnClickListener {
            //check all edit texts are not empty
            if(name.text.toString().isNotEmpty()){
                if(population.text.toString().isNotEmpty()){
                    if(country.text.toString().isNotEmpty()){
                        if(name.text.toString() != city.name || population.text.toString().toInt() != city.population
                                || country.text.toString() != city.country || isVisitedEdit.isChecked != city.isVisited){
                            //there is changed value
                            val sql = SQL(context!!)
                            sql.updateCity(City(city.id,name.text.toString(),
                                    population.text.toString().toInt(),country.text.toString(),isVisitedEdit.isChecked))
                            (activity as MainActivity).onUpdateDeleteReadAgain()
                            Log.i(TAG,"City id = ${city.id} is updated.")
                            dismiss()
                        }else{
                            //there is no changed value
                            Toast.makeText(context,"There is no changed value",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(context,"Country is empty", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Population is  empty", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"City name is empty", Toast.LENGTH_SHORT).show()
            }

        }
        builder.setView(view).setMessage("Edit Fragment")
        return builder.create()
    }
}