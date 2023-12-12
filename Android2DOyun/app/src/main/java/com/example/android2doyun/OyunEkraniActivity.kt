package com.example.android2doyun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.android2doyun.databinding.ActivityOyunEkraniBinding
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunEkraniActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOyunEkraniBinding

    //Pozsiyonlar
    private var anakarakterX=0.0f
    private var anakarakterY=0.0f
    private var aslanX=0.0f
    private var aslanY=0.0f
    private var kusX=0.0f
    private var kusY=0.0f
    private var maymunX=0.0f
    private var maymunY=0.0f


    //Kontroller
    private var dokunmaKontrol=false
    private var baslangicKontrol=false

    //Boyutlar
    private var anakarakterGenisligi=0
    private var anakarakterYuksekligi=0
    private var ekraninGenisligi=0
    private var ekraninYuksekligi=0


    private val timer= Timer()

    private var skor=0

    private var oyunHizi=0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOyunEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lionImage.x=-800.0f
        binding.lionImage.y=-800.0f
        binding.kusImage.x=-800.0f
        binding.kusImage.y=-800.0f
        binding.monkeyImage.x=-800.0f
        binding.monkeyImage.y=-800.0f

        /*binding.anaKarakter.setOnClickListener {
            startActivity(Intent(this@OyunEkraniActivity,SonucEkraniActivity::class.java))
            finish()
        }*/

        binding.cl.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                //event--> ekrana dokunulma bilgisi

                if (baslangicKontrol){
                    if (event?.action==MotionEvent.ACTION_DOWN){
                        //ekrana dokunuldu bilgisi
                        Log.e("Motion Event","Action Down : Dkunuldu")
                        dokunmaKontrol=true
                    }

                    if (event?.action==MotionEvent.ACTION_UP){
                        //ekrandan el çekildi bilgisi
                        Log.e("Motion Event","Action Up : Çekildi")
                        dokunmaKontrol=false
                    }
                }else{
                    baslangicKontrol=true

                    binding.baslamaTextView.visibility=View.INVISIBLE

                    //ekrana dokunuldugunda ana karakterin konumu alınıyor
                    anakarakterX=binding.anaKarakter.x
                    anakarakterY=binding.anaKarakter.y

                    anakarakterGenisligi=binding.anaKarakter.width
                    anakarakterYuksekligi=binding.anaKarakter.height
                    ekraninGenisligi=binding.cl.width
                    ekraninYuksekligi=binding.cl.height


                    //anakarakterin serbest hareketi
                    // timer bize belirili aralıklarla çalıstırmak istedigimiz hareketi verir
                    timer.schedule(0,20){
                        Handler(Looper.getMainLooper()).post{
                            anaKarakterHareketEttirme()
                            cisimleriHareketEttirme()
                            carpismaKontrol()
                        }
                    }

                }

                return true
            }


        })
    }

    fun anaKarakterHareketEttirme(){
        var anaKarakterHiz=ekraninYuksekligi/50.0f
        if (dokunmaKontrol){
            //- ise yukarı + ise aşagı hareket ettirir y eksenınde
            //dokunuldugunda yukarı gidecek
            anakarakterY-=anaKarakterHiz
        }else{
            anakarakterY+=anaKarakterHiz
        }

        //ana karakterin ekran dısına cıkmaması saglşanır
        if (anakarakterY<=0.0f){
            anakarakterY=0.0f
        }
        if (anakarakterY>=ekraninYuksekligi-anakarakterYuksekligi){
            anakarakterY=(ekraninYuksekligi-anakarakterYuksekligi).toFloat()
        }


        binding.anaKarakter.y=anakarakterY
    }

    fun cisimleriHareketEttirme(){
        aslanX-=ekraninGenisligi/70.0f+oyunHizi
        kusX-=ekraninGenisligi/94.0f+oyunHizi
        maymunX-=ekraninGenisligi/96.0f+oyunHizi

        if (aslanX<=0.0f){
            aslanX=ekraninGenisligi+20.0f
            aslanY= floor(Math.random()*ekraninYuksekligi).toFloat()
        }

        binding.lionImage.x=aslanX
        binding.lionImage.y=aslanY

        if (kusX<=0.0f){
            kusX=ekraninGenisligi+20.0f
            kusY= floor(Math.random()*ekraninYuksekligi).toFloat()
        }

        binding.kusImage.x=kusX
        binding.kusImage.y=kusY

        if (maymunX<=0.0f){
            maymunX=ekraninGenisligi+20.0f
            maymunY= floor(Math.random()*ekraninYuksekligi).toFloat()
        }

        binding.monkeyImage.x=maymunX
        binding.monkeyImage.y=maymunY
    }

    fun carpismaKontrol(){
        val kusMerkezX=kusX+binding.kusImage.width/2.0f
        val kusMerkezY=kusY+binding.kusImage.height/2.0f

        if (0.0f<=kusMerkezX && kusMerkezX<=anakarakterGenisligi
            && anakarakterY<=kusMerkezY && kusMerkezY<=anakarakterY+anakarakterYuksekligi){
            skor+=20
            kusX=-10.0f
            oyunHizi+=0.1f
        }
        val maymunMerkezX=maymunX+binding.monkeyImage.width/2.0f
        val maymunMerkezY=maymunY+binding.monkeyImage.height/2.0f

        if (0.0f<=maymunMerkezX && maymunMerkezX<=anakarakterGenisligi
            && anakarakterY<=maymunMerkezY && maymunMerkezY<=anakarakterY+anakarakterYuksekligi){
            skor+=50
            maymunX=-10.0f
            oyunHizi+=0.3f
        }
        val aslanMerkezX=aslanX+binding.lionImage.width/2.0f
        val aslanMerkezY=aslanY+binding.lionImage.height/2.0f

        if (0.0f<=aslanMerkezX && aslanMerkezX<=anakarakterGenisligi
            && anakarakterY<=aslanMerkezY && aslanMerkezY<=anakarakterY+anakarakterYuksekligi){
            aslanX=-10.0f
            timer.cancel()
            val intent=Intent(this@OyunEkraniActivity,SonucEkraniActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }

        binding.skorTextView.text=skor.toString()
    }
}