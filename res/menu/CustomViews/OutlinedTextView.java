package com.jjkbashlord.menu.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by JJK on 4/25/17.
 */

public class OutlinedTextView extends android.support.v7.widget.AppCompatTextView {

    private Paint borderPainter, insetPainter;
    private Typeface tf;
    private int strokeWidth = 5;
    private String currStr = "";
    // Default colors, black boarder/white text inset
    private int borderColor = 0xFF000000;
    private int insetColor = 0xFFFFFFFF;
    float textSize = 150;

    public OutlinedTextView(Context context) {
        super(context);
        initDefaults();
        if(!getText().toString().isEmpty()){
            currStr = getText().toString();
        }
    }

    public OutlinedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaults();
        if(!getText().toString().isEmpty()){
            currStr = getText().toString();
        }
    }

    public OutlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaults();
        if(!getText().toString().isEmpty()){
            currStr = getText().toString();
        }
    }

    protected void initDefaults(){
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeue-Bold.ttf");
        borderPainter = new Paint();
        borderPainter.setDither(true);
        borderPainter.setColor(borderColor);
        borderPainter.setStyle(Paint.Style.STROKE);
        borderPainter.setStrokeJoin(Paint.Join.ROUND);
        borderPainter.setStrokeCap(Paint.Cap.ROUND);
        borderPainter.setStrokeWidth(strokeWidth);
        borderPainter.setTextAlign(Paint.Align.CENTER);
        borderPainter.setTypeface(tf);
        borderPainter.setFlags(Paint.ANTI_ALIAS_FLAG);
        insetPainter = new Paint();
        insetPainter.setDither(true);
        insetPainter.setColor(insetColor);
        insetPainter.setTextAlign(Paint.Align.CENTER);
        insetPainter.setTypeface(tf);
        insetPainter.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        currStr = getText().toString();
        if(!currStr.isEmpty()) {
            Rect bounds = new Rect();
            insetPainter.setTextSize(getTextSize());
            borderPainter.setTextSize(getTextSize());
            borderPainter.getTextBounds(currStr, 0, currStr.length(), bounds);

            canvas.drawText(currStr, this.getWidth() / 2, bounds.height() + ((this.getHeight() - bounds.height()) / 2), insetPainter);
            canvas.drawText(currStr, this.getWidth() / 2, bounds.height() + ((this.getHeight() - bounds.height()) / 2), borderPainter);
        }
    }

    public void setBorderedColor(int color){
        borderPainter.setColor(color);
        invalidate();
    }

    public void setInsetColor(int color){
        insetPainter.setColor(color);
        invalidate();
    }

    public void setBorderWidth(int width){
        borderPainter.setStrokeWidth(width);
    }

    public void setBorderTypeface(Typeface tf){
        borderPainter.setTypeface(tf);
        insetPainter.setTypeface(tf);

    }

    public String str(long i){
        return String.valueOf(i);
    }
}
