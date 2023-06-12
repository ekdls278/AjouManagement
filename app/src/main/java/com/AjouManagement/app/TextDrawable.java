package com.AjouManagement.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class TextDrawable extends ShapeDrawable {

    private String text;
    private Typeface typeface;

    public TextDrawable(String text, Typeface typeface) {
        super(new OvalShape());
        this.text = text;
        this.typeface = typeface;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(typeface);
        textPaint.setTextSize(30);

        int x = (getBounds().left + getBounds().right) / 2;
        int y = (getBounds().top + getBounds().bottom) / 2;

        canvas.drawText(text, x, y, textPaint);
    }
}
