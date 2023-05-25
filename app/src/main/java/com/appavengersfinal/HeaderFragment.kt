package com.appavengersfinal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appavengersfinal.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class HeaderFragment: Fragment(R.layout.header_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var qoute = ""
        val qoute_button = view.findViewById<Button>(R.id.quote_button)
        val meme_button = view.findViewById<ImageView>(R.id.meme_button)

        meme_button.setOnClickListener {
            var view = ImageView(context)
            view.setImageResource(R.drawable.meme)
            var toast = Toast(context)
            // val toast = Toast.makeText(context, JsonObject.getString("content"), Toast.LENGTH_LONG)
            toast.view = view
            toast.show()
        }

        qoute_button.setOnClickListener {
            val URL: String = "https://api.quotable.io/random"
            if (URL.isNotEmpty()) {
                val quoteFetch = OkHttpClient()
                val request = Request.Builder().url(URL).build()

                quoteFetch.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.i("QOUTE_GETTER", "Received Response!")
                        response.use {
                            if (!response.isSuccessful) {
                                Log.e("HTTP Error", "Error loading")
                            } else {
                                val body: String = response.body?.string() ?: "{}"
                                val JsonObject: JSONObject = JSONObject(body)
                                qoute = JsonObject.getString("content")
                                // Need to do this since its async
                                activity?.runOnUiThread(Runnable {
                                    val toast = Toast.makeText(context, JsonObject.getString("content"), Toast.LENGTH_LONG)
                                    toast.show()
                                })
                                Log.d("HTTP Success", qoute)
                            }
                        }
                    }
                })
                Log.d("QOUTE_GETTER", "Not Empty")
            } else {
                Log.d("QOUTE_GETTER", "EMPTY RESPONSE")
            }
        }
    }
}