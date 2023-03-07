package com.example.musicplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var mp: MediaPlayer
    lateinit var play: ImageButton
    lateinit var elapsed: TextView
    lateinit var remaining: TextView
    lateinit var position: SeekBar
    lateinit var volume: SeekBar
    private var totalTime = 0

    private lateinit var drawerTogle: DrawerLayout
    private lateinit var navView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        play = findViewById(R.id.play)
        elapsed = findViewById(R.id.elapsed)
        remaining = findViewById(R.id.remaining)
        position = findViewById(R.id.position)
        volume = findViewById(R.id.volume)

        mp = MediaPlayer.create(this, R.raw.believer)

        drawerTogle = findViewById(R.id.drawerlayout)
        toggle = ActionBarDrawerToggle(this, drawerTogle, R.string.open, R.string.close)
        navView = findViewById(R.id.nav_view)



        play.setOnClickListener {
            if (mp.isPlaying) {
                mp.pause()
                play.setImageResource(R.drawable.play)
            } else {
                mp.start()
                play.setImageResource(R.drawable.pause)
            }
        }
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration
        position.max = totalTime


        Music.setSeekBar(volume, mp, true)
        Music.setSeekBar(position, mp, null, true)


        val handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                val currentPosition = msg.what
                position.progress = currentPosition
                val elapsedTime = Music.createTimeLabel(currentPosition)
                elapsed.text = elapsedTime
                val remainingTime = Music.createTimeLabel(totalTime - currentPosition)
                remaining.text = "- $remainingTime"
            }
        }
        Thread(Runnable {
            while (true) {
                try {
                    val msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Log.d("Thread", e.message.toString())
                }
            }
        }).start()


        drawerTogle.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> Toast.makeText(this, "Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.filemusic -> Toast.makeText(this, "Clicked File", Toast.LENGTH_SHORT).show()
                R.id.sync -> Toast.makeText(this, "Clicked Sync", Toast.LENGTH_SHORT).show()
                R.id.trash -> Toast.makeText(this, "Clicked Trash", Toast.LENGTH_SHORT).show()
                R.id.settings -> Toast.makeText(this, "Clicked Settings", Toast.LENGTH_SHORT).show()
                R.id.login -> Toast.makeText(this, "Clicked Login", Toast.LENGTH_SHORT).show()
                R.id.share -> Toast.makeText(this, "Clicked Share", Toast.LENGTH_SHORT).show()
                R.id.rateus -> Toast.makeText(this, "Clicked Rate Us", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}