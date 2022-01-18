package com.khushalmajoka.whatsappanyone

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.khushalmajoka.whatsappanyone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var message: String
    private lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnSend.setOnClickListener {
            message = binding.etMessage.editText?.text.toString()
            phone = binding.etPhone.editText?.text.toString()

            if (phone.isNotEmpty()){

                binding.ccp.registerCarrierNumberEditText(binding.etPhone.editText)
                phone = binding.ccp.fullNumber
                if(isWhatsappInstalled()){

                    val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$message"))
                    startActivity(i)
                    binding.etMessage.editText?.text?.clear()
                    binding.etPhone.editText?.text?.clear()

                }else{
                    Toast.makeText(this, "Whatsapp not Installed", Toast.LENGTH_SHORT).show()
                }
            }else{
                binding.etPhone.error = "Phone Number is Required!"
            }
        }
    }

    private fun isWhatsappInstalled(): Boolean {

        val pm = packageManager

        val whatsappInstalled = try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        }catch (e: PackageManager.NameNotFoundException){
            false
        }

        return true
    }
}