package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
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

        mp = MediaPlayer.create(this, R.raw.Believer)

        drawerTogle = findViewById(R.id.drawerlayout)
        toggle = ActionBarDrawerToggle(this, drawerTogle, R.string.open, R.string.close)
        navView = findViewById(R.id.nav_view)



    }
}