package com.example.aplicacionmovil_software

class IPostHttp(
    var id: Int,
    var userID:Any,
    var tittle: String,
    var body: String

) {
    init {
        if(userID is String){
            userID=(userID as String).toInt()
        }
        if(userID is Int){
            userID=userID
        }
    }
}