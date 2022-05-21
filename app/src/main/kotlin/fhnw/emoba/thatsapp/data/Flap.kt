package fhnw.emoba.thatsapp.data

import org.json.JSONObject
import fhnw.emoba.modules.module07.flutter_solution.data.Message

data class Flap(val sender: String,
                val receiver: String,
                val message: String,
                val imageUrl: String,
                val gps: Gps
) : Message {

    constructor(json : JSONObject): this(json.getString("sender"),
                                         json.getString("receiver"),
                                         json.getString("message"),
                                         json.getString("image"),
                                         Gps(json.getJSONObject("gps"))
    )

    override fun asJsonString(): String {
        return """
            {"sender":  "$sender",
             "receiver": "$receiver",
             "message": "$message" ,
             "image": "$imageUrl",
             "gps": {
                        "longitude": "${gps.longitude}",
                        "latitude": "${gps.latitude}"
                    }
            }
            """.trimIndent()
    }
}