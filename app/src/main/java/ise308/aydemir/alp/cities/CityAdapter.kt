package ise308.aydemir.alp.cities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CityAdapter(var context: Context, var activity: Activity) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    companion object {
        const val TAG = "CityAdapter"
    }

    private val cities: ArrayList<City>
    private val mInflater: LayoutInflater

    init{
        mInflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        cities = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.set(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun addNewCityToAdapter(city: City){
        cities.add(city)
        this.notifyDataSetChanged()
    }

    fun deleteAllCitiesFromAdapter(){
        this.cities.clear()
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(var view1: View) : RecyclerView.ViewHolder(view1){

        private lateinit var city : City
        private var nameText : TextView
        private var populationText : TextView
        private var countryText : TextView
        private var editButton : ImageButton
        private var deleteButton : ImageButton

        //list item edit button on click
        val editClick = View.OnClickListener {
            val dialogEdit = DialogEdit(city)
            Log.i(TAG,"Edit Dialog opened for City id = ${city.id}")
            dialogEdit.show((activity as MainActivity).supportFragmentManager, null)
        }
        //list item delete button on click
        val deleteClick = View.OnClickListener{
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Are you sure to delete?").setPositiveButton("Yes"){dialog,i->
                val sqlite = SQL(context)
                //delete from sqlite and read from sqlite
                sqlite.deleteCity(city.id!!)
                (activity as MainActivity).onUpdateDeleteReadAgain()
                Log.i(TAG,"City id = ${city.id} is deleted.")
            }.setNegativeButton("No"){dialog,i->
                dialog.dismiss()
            }.show()
        }

        init{
            nameText = view1.findViewById(R.id.nameText)
            populationText = view1.findViewById(R.id.populationText)
            countryText = view1.findViewById(R.id.countryText)
            editButton = view1.findViewById(R.id.edit)
            editButton.setOnClickListener(editClick)
            deleteButton = view1.findViewById(R.id.delete)
            deleteButton.setOnClickListener(deleteClick)
        }

        fun set(city1: City){
            Log.i(TAG,"ID: ${city1.id} is rendered")
            city = city1
            nameText.text = city.name
            populationText.text = city.population.toString()
            countryText.text = city.country
            if(city1.isVisited!!){
                view1.setBackgroundColor(Color.GREEN)
            }else{
                view1.setBackgroundColor(Color.RED)
            }
        }

    }


}