package ise308.aydemir.alp.cities

class City {
    var id :Long ?= null
    var name :String ?= null
    var population : Int ?= null
    var country : String ?= null
    var isVisited : Boolean ?= null

    constructor(id: Long?, name: String?, population: Int?, country: String?, isVisited: Boolean?){
        this.id = id
        this.name = name
        this.population = population
        this.country = country
        this.isVisited = isVisited
    }
    constructor()
}