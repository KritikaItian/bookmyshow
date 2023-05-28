package com.krutika.bookmyshow.utils.ext

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.Px
import androidx.core.animation.doOnEnd
import androidx.core.content.res.use
import androidx.core.graphics.applyCanvas
import androidx.core.view.ViewCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.transition.Slide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

/**
 * Retrieve a color from the current [android.content.res.Resources.Theme].
 */
@ColorInt
fun Context.themeColor(
    @AttrRes themeAttrId: Int,
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

/**
 * Search this view and any children for a [ColorDrawable] `background` and return it's `color`,
 * else return `colorSurface`.
 */
@ColorInt
fun View.descendantBackgroundColor(): Int {
    val bg = backgroundColor()
    if (bg != null) {
        return bg
    } else if (this is ViewGroup) {
        forEach {
            val childBg = descendantBackgroundColorOrNull()
            if (childBg != null) {
                return childBg
            }
        }
    }
    return context.themeColor(android.R.attr.colorBackground)
}

@ColorInt
private fun View.descendantBackgroundColorOrNull(): Int? {
    val bg = backgroundColor()
    if (bg != null) {
        return bg
    } else if (this is ViewGroup) {
        forEach {
            val childBg = backgroundColor()
            if (childBg != null) {
                return childBg
            }
        }
    }
    return null
}

/**
 * Check if this [View]'s `background` is a [ColorDrawable] and if so, return it's `color`,
 * otherwise `null`.
 */
@ColorInt
fun View.backgroundColor(): Int? {
    val bg = background
    if (bg is ColorDrawable) {
        return bg.color
    }
    return null
}

/**
 * Walk up from a [View] looking for an ancestor with a given `id`.
 */
fun View.findAncestorById(@IdRes ancestorId: Int): View {
    return when {
        id == ancestorId -> this
        parent is View -> (parent as View).findAncestorById(ancestorId)
        else -> throw IllegalArgumentException("$ancestorId not a valid ancestor")
    }
}

/**
 * Potentially animate showing a [BottomNavigationView].
 *
 * Abruptly changing the visibility leads to a re-layout of main content, animating
 * `translationY` leaves a gap where the view was that content does not fill.
 *
 * Instead, take a snapshot of the view, and animate this in, only changing the visibility (and
 * thus layout) when the animation completes.
 */
fun BottomNavigationView.show() {
    if (visibility == VISIBLE) return

    try {
        val parent = parent as ViewGroup
        // View needs to be laid out to create a snapshot & know position to animate. If view isn't
        // laid out yet, need to do this manually.
        if (!isLaidOut) {
            measure(
                MeasureSpec.makeMeasureSpec(parent.width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(parent.height, MeasureSpec.AT_MOST)
            )
            layout(parent.left, parent.height - measuredHeight, parent.right, parent.height)
        }

        val drawable = BitmapDrawable(context.resources, drawToBitmap())
        drawable.setBounds(left, parent.height, right, parent.height + height)
        parent.overlay.add(drawable)
        ValueAnimator.ofInt(parent.height, top).apply {
            startDelay = 100L
            duration = 300L
            interpolator = AnimationUtils.loadInterpolator(
                context,
                android.R.interpolator.linear_out_slow_in
            )
            addUpdateListener {
                val newTop = it.animatedValue as Int
                drawable.setBounds(left, newTop, right, newTop + height)
            }
            doOnEnd {
                parent.overlay.remove(drawable)
                visibility = VISIBLE
            }
            start()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        visibility = VISIBLE
    }
}

/**
 * Potentially animate hiding a [BottomNavigationView].
 *
 * Abruptly changing the visibility leads to a re-layout of main content, animating
 * `translationY` leaves a gap where the view was that content does not fill.
 *
 * Instead, take a snapshot, instantly hide the view (so content lays out to fill), then animate
 * out the snapshot.
 */
fun BottomNavigationView.hide() {
    if (visibility == GONE) return
    try {
        val drawable = BitmapDrawable(context.resources, drawToBitmap())
        val parent = parent as ViewGroup
        drawable.setBounds(left, top, right, bottom)
        parent.overlay.add(drawable)
        visibility = GONE
        ValueAnimator.ofInt(top, parent.height).apply {
            startDelay = 100L
            duration = 200L
            interpolator = AnimationUtils.loadInterpolator(
                context,
                android.R.interpolator.fast_out_linear_in
            )
            addUpdateListener {
                val newTop = it.animatedValue as Int
                drawable.setBounds(left, newTop, right, newTop + height)
            }
            doOnEnd {
                parent.overlay.remove(drawable)
            }
            start()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        visibility = GONE
    }
}

/**
 * A copy of the KTX method, adding the ability to add extra padding the bottom of the [Bitmap];
 * useful when it will be used in a [android.graphics.BitmapShader][BitmapShader] with
 * a [android.graphics.Shader.TileMode.CLAMP][CLAMP tile mode].
 */
fun View.drawToBitmap(@Px extraPaddingBottom: Int = 0): Bitmap {
    if (!ViewCompat.isLaidOut(this)) {
        throw IllegalStateException("View needs to be laid out before calling drawToBitmap()")
    }
    return Bitmap.createBitmap(width, height + extraPaddingBottom, ARGB_8888).applyCanvas {
        translate(-scrollX.toFloat(), -scrollY.toFloat())
        draw(this)
    }
}

@BindingAdapter("marginTop")
fun View.setLayoutMarginTop(margin: Float) {
    val lp = layoutParams as MarginLayoutParams
    lp.setMargins(lp.leftMargin, margin.toInt(), lp.rightMargin, lp.bottomMargin)
    layoutParams = lp
}

@BindingAdapter("marginBottom")
fun View.setLayoutMarginBottom(margin: Float) {
    val lp = layoutParams as MarginLayoutParams
    lp.setMargins(lp.leftMargin, lp.topMargin, lp.rightMargin, margin.toInt())
    layoutParams = lp
}

fun View.slideUp() {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(
        0.0F,
        0.0F,
        0.0F,
        -height.toFloat()
    )
    animate.duration = 500
    animate.fillAfter = true
    startAnimation(animate)
}

fun View.slideDown() {
    val animate = TranslateAnimation(
        0.0F,
        0.0F,
        -height.toFloat(),
        0.0F
    ) // toYDelta
    animate.duration = 300
    animate.fillAfter = true
    startAnimation(animate)
}

fun View.hideShowWithSlide(show: Boolean) {
    val transition = Slide(Gravity.TOP)
    transition.duration = 200
    transition.addTarget(this)
    androidx.transition.TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
    isVisible = show
}

fun TabLayout.setBoldTypeface() {
    addOnTabSelectedListener(
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabLayout =
                    (getChildAt(0) as ViewGroup).getChildAt(tab!!.position) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(tabTextView.typeface, Typeface.BOLD)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabLayout =
                    (getChildAt(0) as ViewGroup).getChildAt(tab!!.position) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(null, Typeface.NORMAL)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
// blank overridden method
            }
        }
    )
}

fun View.listenKeyboardChanges(
    onChange: (isShown: Boolean) -> Unit,
) {
    this.viewTreeObserver.addOnGlobalLayoutListener {
        val rec = Rect()
        this.getWindowVisibleDisplayFrame(rec)
        val screenHeight = this.rootView.height
        val keypadHeight = screenHeight - rec.bottom
        if (keypadHeight > screenHeight * 0.15) {
            onChange(true)
        } else {
            onChange(false)
        }
    }
}

