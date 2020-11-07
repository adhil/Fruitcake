package android.fruitcake.animation

import android.content.Context
import android.content.Intent
import android.fruitcake.R
import android.fruitcake.animation.BaseAnimationActivity.Companion.AnimationType.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_animation_list.*

class AnimationListActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, AnimationListActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_list)
        rvAnimationList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = AnimationListAdapter(
                context, arrayListOf(
                    AnimationListItem("Launch Rocket With Value Animator", VALUE_ANIMATOR),
                    AnimationListItem("Launch Rocket With Object Animator", OBJECT_ANIMATOR),
                    AnimationListItem("Launch Rocket Color Animation", COLOR_ANIMATION),
                    AnimationListItem("Rotate & Launch Rocket Chained Animation", CHAINED_ROTATE_AND_LAUNCH_ANIMATION),
                    AnimationListItem("Launch & Rotate Rocket With View Property Animation", VIEW_PROPERTY_ANIMATION)
                )
            )
        }
    }
}