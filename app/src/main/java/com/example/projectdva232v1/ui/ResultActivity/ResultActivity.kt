package com.example.projectdva232v1.ui.ResultActivity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.MainActivity
import com.example.projectdva232v1.R
import kotlin.math.roundToInt

class ResultActivity : AppCompatActivity() {

  lateinit var backgroundColor: View
  lateinit var activityLogo: ImageView
  lateinit var testScore: TextView
  lateinit var testName: TextView
  lateinit var closeButton: Button
  var score: Int = 0
  var maxScore: Int = 0
  lateinit var activityName: String

  
  @RequiresApi(Build.VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.result_layout)

    score = intent.getIntExtra("correctAnswers", 0)
    maxScore = intent.getIntExtra("totalAnswers", 0)
    activityName = intent.getStringExtra("activity").toString()

    initView()
  }

  
  @RequiresApi(Build.VERSION_CODES.M)
  private fun initView(){
    backgroundColor = findViewById(R.id.resultBackground)
    activityLogo = findViewById(R.id.result_ActivityLogo)
    testScore = findViewById(R.id.result_TestScore)
    testName = findViewById(R.id.result_TestName)
    closeButton = findViewById(R.id.result_CloseButton)


    var colorId: Int = resources.getIdentifier(activityName, "color", packageName)
    var co = resources.getColor(colorId, null)
    backgroundColor.setBackgroundColor(co)

    var iconId: Int = resources.getIdentifier(activityName, "drawable", packageName)
    var da: Drawable = resources.getDrawable(iconId, null)
    activityLogo.setImageDrawable(da)

    var strActivityId: Int = resources.getIdentifier(activityName, "string", packageName)
    var str: String = resources.getString(strActivityId, null)
    testName.text = str

    testScore.text = calcPercentage().toString()+"%"
    closeButton.setOnClickListener {
      sendToHome()
    }
  }

  private fun calcPercentage(): Int {
    var result: Double = score.toDouble() / maxScore.toDouble()
    result *= 100
    return result.roundToInt()
  }

  private fun sendToHome() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
  }
}
