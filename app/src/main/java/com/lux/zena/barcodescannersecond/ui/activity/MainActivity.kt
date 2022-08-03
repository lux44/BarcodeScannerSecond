package com.lux.zena.barcodescannersecond.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.zxing.integration.android.IntentIntegrator
import com.lux.zena.barcodescannersecond.R
import com.lux.zena.barcodescannersecond.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.main=this

        binding.btn.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setPrompt("QR")
            integrator.setCameraId(0)
            integrator.setBeepEnabled(true)
            integrator.setBarcodeImageEnabled(false)
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data)
        Log.e("MAIN","QR 코드 체크")

        if (result!=null) {
            if (result.contents == null) Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(this, "scanned ${result.contents}", Toast.LENGTH_SHORT).show()

                binding.webView.settings.javaScriptEnabled=true
                binding.webView.webViewClient = WebViewClient()

                binding.webView.loadUrl(result.contents)
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}