package fhnw.emoba.thatsapp.data

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import org.json.JSONObject
import fhnw.emoba.modules.module07.flutter_solution.data.Message

data class Flap(val sender: String = "",
                val receiver: String = "",
                val message: String = "",
                var imageUrl: String = "",
                val gps: Gps
) : Message {
    var imageBitmap by mutableStateOf(Bitmap.createBitmap(120,120, Bitmap.Config.ALPHA_8))

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