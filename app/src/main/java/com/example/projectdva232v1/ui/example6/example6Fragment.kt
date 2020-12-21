package com.example.projectdva232v1.ui.example6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.R
import kotlin.math.roundToInt

// TODO: Needs renaming - alternatively copy class content and refactor to different type (AppCompatActivity?)
class example6Fragment : Fragment() {

  private lateinit var example6Model: Example6Model
  lateinit var backgroundColor: View
  lateinit var activityLogo: ImageView
  lateinit var testScore: TextView
  lateinit var testName: TextView
  lateinit var closeButton: Button
  var score: Int = 0
  var maxScore: Int = 0
  lateinit var activityName: String


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    example6Model = ViewModelProvider(this).get(Example6Model::class.java)
    val root = inflater.inflate(R.layout.fragment_example6, container, false)

    // TODO: Use these values or somehow get values from previous test page in another way?
    /*
    score = intent.getIntExtra(correctAnswers, Int)
    maxScore = intent.getIntExtra(totalAnswers, Int)
    activityName = intent.getStringExtra(activity)
    */

    initView(root)

    return root
  }

  private fun initView(view: View){
    backgroundColor = view.findViewById(R.id.resultBackground)
    activityLogo = view.findViewById(R.id.result_ActivityLogo)
    testScore = view.findViewById(R.id.result_TestScore)
    testName = view.findViewById(R.id.result_TestName)
    closeButton = view.findViewById(R.id.result_CloseButton)

    // TODO: Connect these setters to values from previous test page and relevant resources
    /*
      TODO: Create resources for category colors and icons (strings/drawables?)
      backgroundColor.setBackgroundColor(/* Insert color resource here - depends on test-category */)
      activityLogo.setImageResource(/* Insert image resource here - depends on test-category */)
    testScore.text = calcPercentage().toString()+"%"
    testName.text = activityName
    // Direct closeButton to TestSelector/other page?
     */
  }

  private fun calcPercentage(): Int {
    var result: Double = score.toDouble() / maxScore.toDouble()
    result *= 100
    return result.roundToInt()
  }
}