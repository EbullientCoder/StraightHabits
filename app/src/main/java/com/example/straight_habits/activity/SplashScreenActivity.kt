package com.example.straight_habits.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.straight_habits.R
import com.example.straight_habits.activity.routine.ShowRoutineActivity
import kotlinx.coroutines.*


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        //Remove the Action Bar
        supportActionBar?.hide()


        //Access to the DB and get all the Data
        /*lifecycleScope.launch(Dispatchers.IO){

            //Sleep for 3 seconds
            job = launch(Dispatchers.Default) {
                delay(3000)

                Log.e(String(), "CIAO")
            }
        }


        lifecycleScope.launch {
            //Wait till the End of the Job
            //job.join()

            //Go to the Main Activity
            val intent = Intent(applicationContext, ShowRoutineActivity::class.java)
            startActivity(intent)
        }*/

        Handler().postDelayed({
            val intent = Intent(applicationContext, ShowRoutineActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //intent.putExtra("session", Session())

            startActivity(intent)
            overridePendingTransition(0, 0);
        }, 1500)
    }

}