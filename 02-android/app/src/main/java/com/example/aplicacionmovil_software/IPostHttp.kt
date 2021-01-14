package com.example.aplicacionmovil_software

class IPostHttp(
    var id: Int,
    var userId:Any,
    var title: String,
    var body: String?

) {
    init {
        if(userId is String){
            userId = (userId as String).toInt()
        }
        if(userId is Int){
            userId = userId
        }
    }
}