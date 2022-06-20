package com.example.final_project

class ModelNumber {
    var id:String = ""
    var number:String = ""
    var timestamp:Long = 0
    var uid:String = ""

    constructor()
    constructor(id: String, number: String, timestamp: Long, uid: String) {
        this.id = id
        this.number = number
        this.timestamp = timestamp
        this.uid = uid
    }


}