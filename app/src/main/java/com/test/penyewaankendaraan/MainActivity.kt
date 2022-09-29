package com.test.penyewaankendaraan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.textfield.TextInputLayout
import com.test.penyewaankendaraan.databinding.ActivityHomeBinding
import com.test.penyewaankendaraan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var binding: ActivityMainBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2  = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("User Login")

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnLogin: Button = findViewById(R.id.btnLogin)


        btnRegister.setOnClickListener {
            val moveRegister = Intent( this@MainActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()


            if(username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkLogin=false

            }
            if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin=false
            }

            if(username == "admin"&& password== "0944") checkLogin=true
            if(!checkLogin)return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        createNotificationChannel()

        binding!!.btn1.setOnClickListener {
            sendNotification1()
        }

        binding!!.btn2.setOnClickListener {
            sendNotification2()
    }

        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Notification Title"
                val descriptionText = "Notification Description"

                val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = descriptionText
                }

                val channel2 = NotificationChannel(CHANNEL_ID_2, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = descriptionText
                }

                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel1)
                notificationManager.createNotificationChannel(channel2)
            }
        }

        private fun sendNotification1() {
            val intent : Intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
            broadcastIntent.putExtra("toastMessage", binding?.etMessage?.text.toString())
            val actionIntent = PendingIntent.getBroadcast(this,0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
                .setContentTitle(binding?.etTitle?.text.toString())
                .setContentText(binding?.etMessage?.text.toString())
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher,"Toast", actionIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId1, builder.build())
            }
        }

        private fun sendNotification2() {

            val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24)
                .setContentTitle(binding?.etTitle?.text.toString())
                .setContentText(binding?.etMessage?.text.toString())
                .setPriority(NotificationCompat.PRIORITY_LOW)

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId2, builder.build())
            }
        }

    }