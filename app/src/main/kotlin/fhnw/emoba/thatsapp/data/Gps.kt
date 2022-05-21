package fhnw.emoba.thatsapp.data

import org.json.JSONObject

data class Gps(var longitude: String = "", var latitude: String = "") {

    constructor(json: JSONObject): this(
        json.getString("longitude"),
        json.getString("latitude")
    )

    fun asJsonString(): String{
        return """
            {
               "longitude": $longitude,
               "latitude": $latitude
            }
            """.trimIndent()
    }
}