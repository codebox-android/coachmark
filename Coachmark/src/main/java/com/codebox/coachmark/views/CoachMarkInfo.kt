package com.codebox.coachmark.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.codebox.coachmark.configuration.CoachMarkConfig
import com.codebox.coachmark.constants.Gravity
import com.codebox.coachmark.constants.TypeFace
import com.codebox.coachmark.constants.Utils
import kotlin.math.roundToInt

/**
 * This class is used to design and build info text
 */
class CoachMarkInfo : TextView {
    constructor(context: Context, builder: Builder) : super(context) {
        init(builder)
    }

    constructor(context: Context, attrs: AttributeSet?, builder: Builder) : super(context, attrs) {
        init(builder)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, builder: Builder) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        init(builder)
    }


    private lateinit var mBuilder: Builder
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private fun init(builder: Builder) {
        mBuilder = builder
        this.setWillNotDraw(false)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, mBuilder.getTextSize())
        setTextColor(mBuilder.getTextColor())
        text = mBuilder.getInfoText()
        val fontTYpeFace = mBuilder.getFontTypeface()
        val fontStyle = mBuilder.getFontStyle()
        when (fontTYpeFace) {
            TypeFace.BOLD -> {
                if (fontStyle != null) {
                    setTypeface(fontStyle, Typeface.BOLD)
                } else {
                    setTypeface(null, Typeface.BOLD)
                }
            }
            TypeFace.ITALIC -> {
                if (fontStyle != null) {
                    setTypeface(fontStyle, Typeface.ITALIC)
                } else {
                    setTypeface(null, Typeface.ITALIC)
                }
            }
            TypeFace.BOLD_ITALIC -> {
                if (fontStyle != null) {
                    setTypeface(fontStyle, Typeface.BOLD_ITALIC)
                } else {
                    setTypeface(null, Typeface.BOLD_ITALIC)
                }
            }
            TypeFace.NORMAL -> {
                if (fontStyle != null) {
                    setTypeface(fontStyle, Typeface.NORMAL)
                } else {
                    setTypeface(null, Typeface.NORMAL)
                }
            }
        }
        mPaint.color = mBuilder.getBackgroundColor()
        gravity = mBuilder.getInfoTextGravity()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mBuilder.getDrawable()?.let {
                when (mBuilder.getDrawablePosition()) {
                    Gravity.TOP -> setCompoundDrawablesRelativeWithIntrinsicBounds(null, it, null, null)
                    Gravity.START -> setCompoundDrawablesRelativeWithIntrinsicBounds(it, null, null, null)
                    Gravity.BOTTOM -> setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, it)
                    Gravity.END -> setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, it, null)
                    else -> {}
                }
            }
        } else {
            mBuilder.getDrawable()?.let {
                when (mBuilder.getDrawablePosition()) {
                    Gravity.TOP -> setCompoundDrawablesWithIntrinsicBounds(null, it, null, null)
                    Gravity.START -> setCompoundDrawablesWithIntrinsicBounds(it, null, null, null)
                    Gravity.BOTTOM -> setCompoundDrawablesWithIntrinsicBounds(null, null, null, it)
                    Gravity.END -> setCompoundDrawablesWithIntrinsicBounds(null, null, it, null)
                    else -> {}
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                mBuilder.getCornerRadius(), mBuilder.getCornerRadius(),
                mPaint
        )
        super.onDraw(canvas)
    }

    class Builder(private val mContext: Context) {

        private var mBackgroundColor: Int = Color.GRAY
        private var mCornerRadius: Float = 8f
        private var mInfoText: CharSequence = "CODEBOX"
        private var mTextColor: Int = Color.WHITE
        private var mMargin: Rect = Rect()
        private var mInfoTextGravity: Int = android.view.Gravity.CENTER
        private var mPadding: Rect = Rect(
                Utils.dpToPx(mContext, 4).roundToInt(),
                Utils.dpToPx(mContext, 4).roundToInt(),
                Utils.dpToPx(mContext, 4).roundToInt(),
                Utils.dpToPx(mContext, 4).roundToInt()
        )
        private var mTextSize: Float = 16f
        private var mInfoViewWidth: Int = FrameLayout.LayoutParams.MATCH_PARENT
        private var mInfoViewHeight: Int = Utils.dpToPx(mContext, 30).roundToInt()
        private var mInfoViewGravity: Gravity = Gravity.BOTTOM
        private var mInfoViewCenterAlignment: Boolean = false
        private var mAttachedToTarget: Boolean = false
        private var mDrawable: Drawable? = null
        private var mDrawablePosition: Gravity = Gravity.END
        private var mFontTypeface: TypeFace = TypeFace.NORMAL
        private var mFontStyle: Typeface? = null

        fun getInfoTextGravity(): Int = mInfoTextGravity
        fun getBackgroundColor(): Int = mBackgroundColor
        fun getTextColor(): Int = mTextColor
        fun getCornerRadius(): Float = mCornerRadius
        fun getInfoText(): CharSequence = mInfoText
        fun getMargin(): Rect = mMargin
        fun getPadding(): Rect = mPadding
        fun getTextSize(): Float = mTextSize
        fun getInfoViewWidth(): Int = mInfoViewWidth
        fun getInfoViewHeight(): Int = mInfoViewHeight
        fun getInfoViewGravity(): Gravity = mInfoViewGravity
        fun isNotAttachedToTarget(): Boolean = !mAttachedToTarget
        fun isAttachedToTarget(): Boolean = mAttachedToTarget
        fun isInfoViewCenterAlignment(): Boolean = mInfoViewCenterAlignment
        fun getDrawable(): Drawable? = mDrawable
        fun getDrawablePosition(): Gravity = mDrawablePosition
        fun getFontTypeface(): TypeFace = mFontTypeface
        fun getFontStyle(): Typeface? = mFontStyle

        fun setConfig(config: CoachMarkConfig) {
            val infoBuilder = config.getInfoTextBuilder()
            setBackgroundColor(infoBuilder.getBackgroundColor())
            setTextColor(infoBuilder.getTextColor())
            setTextSize(infoBuilder.getTextSize())
            setCornerRadius(infoBuilder.getCornerRadius())
            setInfoViewGravity(infoBuilder.getInfoViewGravity())
            setDrawable(infoBuilder.getDrawable())
            setDrawablePosition(infoBuilder.getDrawablePosition())
            setAttachToTarget(infoBuilder.isAttachedToTarget())
            setMargin(infoBuilder.getMargin())
            setPadding(infoBuilder.getPadding())
            setFontTypeFace(infoBuilder.getFontTypeface())
            setFontStyle(infoBuilder.getFontStyle())
        }

        fun setBackgroundColor(color: Int): Builder {
            mBackgroundColor = color
            return this
        }

        fun setTextColor(color: Int): Builder {
            mTextColor = color
            return this
        }

        fun setCornerRadius(radius: Float): Builder {
            mCornerRadius = radius
            return this
        }

        fun setInfoText(text: CharSequence): Builder {
            mInfoText = text
            return this
        }

        fun setFontStyle(style: Typeface?) {

        }

        fun setMargin(left: Int, top: Int, right: Int, bottom: Int): Builder {
            mMargin.set(
                    Utils.dpToPx(mContext, left).roundToInt(),
                    Utils.dpToPx(mContext, top).roundToInt(),
                    Utils.dpToPx(mContext, right).roundToInt(),
                    Utils.dpToPx(mContext, bottom).roundToInt()
            )
            return this
        }

        fun setMargin(margin: Rect): Builder {
            mMargin.set(margin)
            return this
        }

        fun setPadding(padding: Rect): Builder {
            mPadding.set(padding)
            return this
        }

        fun setInfoTextGravity(gravity: Int): Builder {
            mInfoTextGravity = gravity
            return this
        }

        fun setPadding(left: Int, top: Int, right: Int, bottom: Int): Builder {
            mPadding.set(
                    Utils.dpToPx(mContext, left).roundToInt(),
                    Utils.dpToPx(mContext, top).roundToInt(),
                    Utils.dpToPx(mContext, right).roundToInt(),
                    Utils.dpToPx(mContext, bottom).roundToInt()
            )
            return this
        }

        fun setTextSize(size: Float): Builder {
            mTextSize = size
            return this
        }

        fun setInfoViewWidth(width: Int): Builder {
            mInfoViewWidth = Utils.dpToPx(mContext, width).roundToInt()
            return this
        }

        fun setInfoViewHeight(height: Int): Builder {
            mInfoViewHeight = Utils.dpToPx(mContext, height).roundToInt()
            return this
        }

        fun setInfoViewGravity(gravity: Gravity): Builder {
            mInfoViewGravity = gravity
            return this
        }

        fun setDrawable(drawable: Drawable?): Builder {
            mDrawable = drawable
            return this
        }

        fun setFontTypeFace(typeface: TypeFace) {
            mFontTypeface = typeface
        }

        fun setDrawableResource(id: Int): Builder {
            mDrawable = ContextCompat.getDrawable(mContext, id)
            return this
        }

        fun setDrawablePosition(position: Gravity): Builder {
            mDrawablePosition = position
            return this
        }

        fun setAttachToTarget(boolean: Boolean): Builder {
            mAttachedToTarget = boolean
            return this
        }

        fun build(): CoachMarkInfo {
            return CoachMarkInfo(mContext, this)
        }
    }
}