package com.example.projectdva232v1.ui.ResultActivity

import android.content.Intent
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.MainActivity
import com.example.projectdva232v1.R
import kotlin.math.roundToInt

// TODO: Needs renaming - alternatively copy class content and refactor to different type (AppCompatActivity?)
/*class ResultFragment : Fragment() {

  private lateinit var resultModel: ResultModel
  lateinit var backgroundColor: View
  lateinit var activityLogo: ImageView
  lateinit var testScore: TextView
  lateinit var testName: TextView
  lateinit var closeButton: Button
  var score: Int = 0
  var maxScore: Int = 0
  lateinit var activityName: String


  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    resultModel = ViewModelProvider(this).get(ResultModel::class.java)
    val root = inflater.inflate(R.layout.result_layout, container, false)

    score = intent.getIntExtra(correctAnswers, Int)
    maxScore = intent.getIntExtra(totalAnswers, Int)
    activityName = intent.getStringExtra(activity)

    initView(root)

    return root
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  private fun initView(view: View){
    backgroundColor = view.findViewById(R.id.resultBackground)
    activityLogo = view.findViewById(R.id.result_ActivityLogo)
    testScore = view.findViewById(R.id.result_TestScore)
    testName = view.findViewById(R.id.result_TestName)
    closeButton = view.findViewById(R.id.result_CloseButton)


    var colorId: Int = resources.getIdentifier(activityName, "colors", context?.packageName)
    backgroundColor.setBackgroundColor(colorId)

    var iconId: Int = resources.getIdentifier(activityName, "drawable", context?.packageName)
    var da: Drawable = resources.getDrawable(iconId, null)
    activityLogo.setImageDrawable(da)

    testScore.text = calcPercentage().toString()+"%"
    testName.text = activityName
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
    val intent = Intent(this.activity, MainActivity::class.java)
    startActivity(intent)
  }
}
 */