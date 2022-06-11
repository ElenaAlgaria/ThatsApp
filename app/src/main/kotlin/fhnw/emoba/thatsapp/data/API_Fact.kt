package fhnw.emoba.thatsapp.data.Fact_2

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection


class APIFact(){

    fun getData(): String {

        val url = URL("https://api.api-ninjas.com/v1/facts?limit=1")

        val conn = url.openConnection() as HttpsURLConnection
        conn.addRequestProperty("X-Api-Key", "XuDF6PgesM4YBINOreLpqA==YPddnfJltswptHBZ")

        val inputStream = conn.inputStream
        conn.connect()

        val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        val jsonString = reader.readText()
        reader.close()

        val jsonArray = JSONArray(jsonString)
        val data: JSONObject = jsonArray[0] as JSONObject

        return data.getString("fact")
    }

}
