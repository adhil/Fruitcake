package android.fruitcake.animation

import android.animation.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.fruitcake.R
import android.fruitcake.animation.BaseAnimationActivity.Companion.AnimationType
import android.fruitcake.animation.BaseAnimationActivity.Companion.AnimationType.*
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat

class RocketAnimationActivity : BaseAnimationActivity() {

    companion object {
        private const val KEY_ANIMATION_TYPE = "KEY_ANIMATION_TYPE"
        fun startActivity(context: Context, animationType: AnimationType) {
            context.startActivity(Intent(context, RocketAnimationActivity::class.java).apply {
                putExtra(KEY_ANIMATION_TYPE, animationType)
            })
        }
    }

    private var isAnimationRunning = false

    override fun onStartAnimation() {
        if (isAnimationRunning) return
        isAnimationRunning = true
        (intent?.getSerializableExtra(KEY_ANIMATION_TYPE) as AnimationType).let { animationType ->
            when (animationType) {
                VALUE_ANIMATOR -> launchRocketWithValueAnimator()
                OBJECT_ANIMATOR -> launchRocketWithObjectAnimator()
                COLOR_ANIMATION -> changeBgColorWithObjectAnimator()
                CHAINED_ROTATE_AND_LAUNCH_ANIMATION -> rotateAndLaunchRocketWithAnimatorSet()
                VIEW_PROPERTY_ANIMATION -> launchAndRotateRocketWithViewPropertyAnimator()
            }
        }
    }

    private fun launchRocketWithValueAnimator() {
        ValueAnimator.ofFloat(0f, -screenHeight).run {
            addUpdateListener {
                rocket.translationY = it.animatedValue as Float
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    isAnimationRunning = false
                    rocket.translationY = 0f
                }
            })
            interpolator = AccelerateInterpolator()
            duration = DEFAULT_ANIMATION_DURATION
            start()
        }
    }

    private fun launchRocketWithObjectAnimator() {
        ObjectAnimator.ofFloat(rocket, "translationY", 0f, -screenHeight).run {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    isAnimationRunning = false
                    rocket.translationY = 0f
                }
            })
            interpolator = AccelerateInterpolator()
            duration = DEFAULT_ANIMATION_DURATION
            start()
        }
    }

    private fun changeBgColorWithObjectAnimator() {
        ObjectAnimator.ofObject(
            frameLayout,
            "backgroundColor",
            ArgbEvaluator(),
            ContextCompat.getColor(this, R.color.background_from),
            ContextCompat.getColor(this, R.color.background_to)
        ).run {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    isAnimationRunning = false
                }
            })
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            duration = DEFAULT_ANIMATION_DURATION
            start()
        }
    }

    private fun rotateAndLaunchRocketWithAnimatorSet() {
        AnimatorSet().run {
            play(ObjectAnimator.ofFloat(rocket, "rotation", 0f, 360f).apply {
                duration = DEFAULT_ANIMATION_DURATION
            }).before(ObjectAnimator.ofFloat(rocket, "translationY", 0f, -screenHeight).apply {
                interpolator = AccelerateInterpolator()
                duration = DEFAULT_ANIMATION_DURATION
            })
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    isAnimationRunning = false
                    rocket.translationY = 0f
                }
            })
            start()
        }
    }

    private fun launchAndRotateRocketWithViewPropertyAnimator() {
        rocket.animate()
            .translationY(-screenHeight)
            .rotationBy(360f)
            .setDuration(DEFAULT_ANIMATION_DURATION)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    isAnimationRunning = false
                    rocket.translationY = 0f
                }
            })
            .start()
    }

    val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}