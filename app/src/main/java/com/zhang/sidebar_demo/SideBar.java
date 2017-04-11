package com.zhang.sidebar_demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * Created by zhang . DATA: 2017/4/5 . Description : 侧边栏
 */

public class SideBar extends View {

    /**
     * 导航内容
     */
    private char[] mLetters;

    private SectionIndexer mSectionIndexer = null;

    /**
     * 中间显示文字的textview
     */
    private TextView mDialogText;

    /**
     * 导航选中监听
     */
    private onSelecListener listener = null;

    /**
     * 导航条选中默认背景色
     */
    private int backgroundColor = 0x99CCCCCC;

    /**
     * 导航条未选中默认背景色
     */
    private int backgroundColorHint = 0x00000000;

    /* 导航条默认字体颜色 */
    private int textColor = 0xFF858C94;

    /**
     * 提示框默认字体颜色
     */
    private int dialogTextColor = Color.WHITE;

    /**
     * 提示框默认背景色
     */
    private int dialogBackground = 0x99CCCCCC;

    private int sidebarTextsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());

    private int dialogTextsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());

    private int mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

    private int mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

    private String digits = null;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取属性值
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SideBar, defStyleAttr, 0);
        digits = array.getString(R.styleable.SideBar_sidebar_gidits);
        backgroundColorHint = array.getColor(R.styleable.SideBar_sidebar_background_hint, backgroundColorHint);
        textColor = array.getColor(R.styleable.SideBar_sidebar_textcolor, textColor);
        sidebarTextsize = array.getDimensionPixelSize(R.styleable.SideBar_sidebar_textsize, sidebarTextsize);
        backgroundColor = array.getColor(R.styleable.SideBar_sidebar_background, backgroundColor);
        dialogTextsize = array.getDimensionPixelSize(R.styleable.SideBar_dialog_textsize, dialogTextsize);
        dialogTextColor = array.getColor(R.styleable.SideBar_dialog_textcolor, dialogTextColor);
        dialogBackground = array.getColor(R.styleable.SideBar_dialog_background, dialogBackground);
        mWidth = (int) array.getDimension(R.styleable.SideBar_dialog_width, mWidth);
        mHeight = (int) array.getDimension(R.styleable.SideBar_dialog_height, mHeight);
        array.recycle();
        init(context);
    }

    private void init(Context context) {
        mLetters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        if (!TextUtils.isEmpty(digits)) {
            digits = digits.toUpperCase();
            mLetters = new char[digits.length()];
            for (int i = 0; i < digits.length(); i++) {
                mLetters[i] = digits.charAt(i);
            }
        }
        initDialogText(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 绘制默认背景色
        canvas.drawColor(backgroundColorHint);

        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(sidebarTextsize);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        float widthCenter = getMeasuredWidth() / 2;

        if (mLetters.length > 0) {
            float height = getMeasuredHeight() / mLetters.length;
            for (int i = 0; i < mLetters.length; i++) {
                canvas.drawText(String.valueOf(mLetters[i]), widthCenter, (i + 1) * height, paint);
            }
        }
        this.invalidate();
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        int y = (int) event.getY();
        int idx = y / (getMeasuredHeight() / mLetters.length);
        if (idx >= mLetters.length) {
            idx = mLetters.length - 1;
        } else if (idx < 0) {
            idx = 0;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            setBackgroundColor(backgroundColor);
            mDialogText.setVisibility(View.VISIBLE);
            mDialogText.setText(String.valueOf(mLetters[idx]));
            if (mSectionIndexer == null) {
                throw new NullPointerException("mSectionIndexer can't be null");
            }
            int position = mSectionIndexer.getPositionForSection(mLetters[idx]);

            if (position == -1) {
                return true;
            }
            if (listener != null) {
                listener.setSelection(position);
            }

        } else {
            mDialogText.setVisibility(View.INVISIBLE);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            setBackgroundColor(backgroundColorHint);
        }
        return true;
    }

    /**
     * 初始化提示框
     * @param context
     */
    private void initDialogText(Context context) {
        mDialogText = new TextView(context);
        mDialogText.setVisibility(View.INVISIBLE);
        mDialogText.setTextSize(dialogTextsize);
        mDialogText.setTextColor(dialogTextColor);
        mDialogText.setBackgroundColor(dialogBackground);
        mDialogText.setMinHeight(mHeight);
        mDialogText.setMaxHeight(mHeight);
        mDialogText.setMinWidth(mWidth);
        mDialogText.setMaxWidth(mWidth);
        mDialogText.setGravity(Gravity.CENTER);
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(mDialogText, lp);
    }

    public void setSectionIndexer(SectionIndexer sectionIndexer) {
        mSectionIndexer = sectionIndexer;
    }

    /**
     * sidebar 选中监听
     */
    public interface onSelecListener {
        void setSelection(int position);
    }

    public void setOnSelecListener(onSelecListener listener) {
        this.listener = listener;
    }
}
