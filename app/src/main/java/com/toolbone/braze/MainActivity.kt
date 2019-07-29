package com.toolbone.braze

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.appboy.Appboy

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            val userId = txtInput.text.toString()
            if (userId.isEmpty()) {

                Snackbar.make(view, "User Id should not be null or empty. Doing nothing.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            } else {
                Snackbar.make(view, String.format("Changed user to %s and requested flush to Appboy", userId), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                Appboy.getInstance(applicationContext).changeUser(userId)
            }

            Appboy.getInstance(applicationContext).requestImmediateDataFlush()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
