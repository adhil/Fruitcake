package android.fruitcake.animation

import android.fruitcake.R
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseAnimationActivity : AppCompatActivity() {
  protected lateinit var rocket: View
  protected lateinit var doge: View
  protected lateinit var frameLayout: View
  protected var screenHeight = 0f
  protected var defaultRocketPosition = 0f

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_base_animation)

    rocket = findViewById(R.id.ivRocket)
    doge = findViewById(R.id.ivDoge)
    frameLayout = findViewById(R.id.flContainer)

    defaultRocketPosition = rocket.y
    frameLayout.setOnClickListener { onStartAnimation() }
  }

  override fun onResume() {
    super.onResume()
    DisplayMetrics().apply {
      windowManager.defaultDisplay.getMetrics(this)
      screenHeight = heightPixels.toFloat()
    }
  }

  protected abstract fun onStartAnimation()

  companion object {
    val DEFAULT_ANIMATION_DURATION = 2500L
    enum class AnimationType {
      VALUE_ANIMATOR, OBJECT_ANIMATOR, COLOR_ANIMATION, CHAINED_ROTATE_AND_LAUNCH_ANIMATION, VIEW_PROPERTY_ANIMATION
    }
  }
}
