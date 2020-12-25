package com.sitamrock11.directmessagesender

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var num: String = "0"
        //checking network
        val isConnected=checkConnectivity(this)

        //creating dialog box if network connection not Enable
        val dialog=AlertDialog.Builder(this)
        if(!isConnected){
            dialog.setTitle("Can't Open App!!")
            dialog.setMessage("Please Enable Your Internet Connection..")
            dialog.setPositiveButton("ENABLE"){ _, _->
                val intent = Intent(Settings.ACTION_DATA_USAGE_SETTINGS)
                startActivity(intent)
            }
            dialog.setNegativeButton("EXIT"){ _, _->
                finish()
            }
            dialog.create()
            dialog.show()
        }

        etNumber.isVisible = false
        etText.isVisible=false
        btnOpener.isVisible = false
        //using Glide to adding gif
        Glide.with(this)
                .load(R.drawable.start)
                .into(imgLogo)

        //setting color to action bar
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d40")))

        //setting color to status bar
        if (Build.VERSION.SDK_INT >= 21) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.parseColor("#00251a")
        }
        //for Whatsapp
        switchWhatsapp.setOnCheckedChangeListener { buttonView, isChecked ->
            //switch on logic
            if (isChecked) {
                switchTelegram.isEnabled=false
                etText.isVisible=true
                etText.setBackgroundColor(Color.parseColor("#d7ffd9"))
                tvStart.isVisible=false
                imgLogo.setImageResource(R.drawable.whatsapp_logo)
                etNumber.apply{
                    isVisible = true
                    hint = "Enter a number"
                    inputType = InputType.TYPE_CLASS_NUMBER
                    setBackgroundColor(Color.parseColor("#d7ffd9"))
                }
                btnOpener.apply{
                    isVisible = true
                    text = "OPEN WHATSAPP"
                    setBackgroundColor(Color.parseColor("#25D366"))
                    setTextColor(Color.parseColor("#000000"))
                }
                title = "Open WhatsApp"
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#a5d6a7")))
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = Color.parseColor("#75a478")
                }

                //btnOpener logic for whatsapp
                btnOpener.setOnClickListener {
                    num = etNumber.text.toString()
                    var text=etText.text.toString()

                    //91 9865741256 format checking logic
                    if (!num.isEmpty()) {
                        if (num.isDigitsOnly()) {
                            if (num.length == 12 && num[0] == '9' && num[1] == '1') {
                                openWhatsapp(num, text)
                            } else if (num.length == 10) {
                                num = "91$num"
                                openWhatsapp(num, text)
                            } else if ((num.length == 12 && num[0] != '9' && num[1] != '1') || num.length < 10 || num.length > 12) {
                                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                                etNumber.text = null
                            }
                        } else if (num[0] == '+') {
                            num = num.substring(1)
                            openWhatsapp(num, text)
                        } else {
                            Toast.makeText(this, "Please enter number digits only", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "OOPs!!! You have not entered any Number", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {         //switch off
                etText.isVisible=false
                tvStart.isVisible=true
                etNumber.isVisible = false
                btnOpener.isVisible = false
                switchTelegram.isEnabled=true
                title="Direct Message Sender"
                Glide.with(this)
                        .load(R.drawable.start)
                        .into(imgLogo)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d40")))
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = Color.parseColor("#00251a")
                }
            }
        }
        //for telegram
        switchTelegram.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {     //switch on logic
                var userName: String
                etText.isVisible=false
               // etText.setBackgroundColor(Color.parseColor("#b6ffff"))
                imgLogo.setImageResource(R.drawable.telegram_logo)
                tvStart.isVisible=false
                switchWhatsapp.isEnabled=false
                etNumber.apply{
                    isVisible = true
                    hint = "Enter a UserName"
                    inputType = InputType.TYPE_CLASS_TEXT
                    setBackgroundColor(Color.parseColor("#b6ffff"))
                }
                btnOpener.apply{
                    isVisible = true
                    setBackgroundColor(Color.parseColor("#0088cc"))
                    setTextColor(Color.parseColor("#ffffff"))
                    text = "OPEN TELEGRAM"
                }
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#63ccff")))
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = Color.parseColor("#039be5")
                }
                title = "Open Telegram"

                //btnOpener logic for telegram
                btnOpener.setOnClickListener {
                    userName = etNumber.text.toString()
                    if (!userName.isEmpty()) {
                       openTelegram(userName)
                    } else {
                        Toast.makeText(this, "OOPs!!! You have not entered any UserName", Toast.LENGTH_SHORT).show()
                    }

                }
            }else{           //switch off logic
                Glide.with(this)
                        .load(R.drawable.start)
                        .into(imgLogo)
                tvStart.isVisible=true
                title="Direct Message Sender"
                etText.isVisible=false
                etNumber.isVisible = false
                btnOpener.isVisible = false
                switchWhatsapp.isEnabled=true
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d40")))
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = Color.parseColor("#00251a")
                }
            }
        }
    }

    //opening whatsapp via intent
    private fun openWhatsapp(num: String, text: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.`package` = "com.whatsapp"
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$num&text=$text")
        if (packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please install whatsapp", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    //opening telegram via intent
    private fun openTelegram(userName: String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.`package` = "org.telegram.messenger"
        intent.data = Uri.parse("https://t.me/$userName")
        if (packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please install Telegram", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    //checking Connectivity
    fun checkConnectivity(context: Context):Boolean{
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?=connectivityManager.activeNetworkInfo
        if(activeNetwork?.isConnected!=null){
            return activeNetwork.isConnected
        }else{
            return false
        }
    }
}