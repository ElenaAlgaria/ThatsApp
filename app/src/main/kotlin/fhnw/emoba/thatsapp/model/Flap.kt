package fhnw.emoba.modules.module07.flutter_solution.model

import org.json.JSONObject
import fhnw.emoba.modules.module07.flutter_solution.data.Message


data class Flap(val sender: String,
                val message: String) : Message {

    constructor(json : JSONObject): this(json.getString("sender"),
                                         json.getString("message"))

    override fun asJsonString(): String {
        return """
            {"sender":  "$sender", 
             "message": "$message" 
            }
            """
    }
}