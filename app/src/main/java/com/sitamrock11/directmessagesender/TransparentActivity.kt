package com.sitamrock11.directmessagesender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*

class TransparentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var num: String = "0"
        setContentView(R.layout.activity_main2)

        //getting the selected text as a String via ACTION_PROCESS_TEXT
        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            num = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        }

        //91 9865741256 format checking logic
            if (num.isDigitsOnly()) {
                if (num.length == 12 && num[0] == '9' && num[1] == '1') {
                    openWhatsapp(num)
                } else if (num.length == 10) {
                    num = "91$num"
                    openWhatsapp(num)
                } else if ((num.length == 12 && num[0] != '9' && num[1] != '1') || num.length < 10 || num.length > 12) {
                    Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show()
                    etNumber.text = null
                }
            }else if(num[0]=='+'){
                num=num.substring(1)
                openWhatsapp(num)
            }else {
                Toast.makeText(this, "Please select number digits only", Toast.LENGTH_SHORT).show()
            }
    }

    //opening whatsapp via intent
   private fun openWhatsapp(num:String){
        val intent= Intent(Intent.ACTION_VIEW)
        intent.`package`="com.whatsapp"
        intent.data= Uri.parse("https://wa.me/$num")
        if(packageManager.resolveActivity(intent,0) != null){
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please install whatsapp",Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}