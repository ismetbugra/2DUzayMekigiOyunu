package com.example.android2doyun

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android2doyun.databinding.ActivitySonucEkraniBinding

class SonucEkraniActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySonucEkraniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySonucEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val skor=intent.getIntExtra("skor",0)
        binding.sonucSkorTextView.text=skor.toString()

        val sp=getSharedPreferences("Sonuc", Context.MODE_PRIVATE)
        val enYuksekSkor=sp.getInt("enYuksekSkor",0)

        if (skor>enYuksekSkor){
            val editor=sp.edit()
            editor.putInt("enYuksekSkor",skor)
            editor.commit()

            binding.enyuksekSkorTextView.text=skor.toString()
        }else{
            binding.enyuksekSkorTextView.text=enYuksekSkor.toString()
        }

        binding.buttonTekrarDene.setOnClickListener {
            startActivity(Intent(this@SonucEkraniActivity,MainActivity::class.java))
        }
    }
}