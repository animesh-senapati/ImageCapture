package com.example.imagecapture;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

public class TickMarkView extends View {
    private Paint paint;
    private Path path;
    private float pathProgress = 0f;
    private ValueAnimator animator;

    public TickMarkView(Context context) {
        super(context);
        init();
    }

    public TickMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TickMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Set up the paint for the tick mark
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);

        path = new Path();

        // Create an animator to control the drawing progress of the tick
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(500); // 500ms duration
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pathProgress = (float) animation.getAnimatedValue();
                invalidate(); // Redraw the view
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();
        // Define the tick mark path
        path.moveTo(getWidth() * 0.3f, getHeight() * 0.5f);
        path.lineTo(getWidth() * 0.45f, getHeight() * 0.7f);
        path.lineTo(getWidth() * 0.75f, getHeight() * 0.3f);

        // Measure the path to animate
        PathMeasure measure = new PathMeasure(path, false);
        Path dst = new Path();
        measure.getSegment(0f, measure.getLength() * pathProgress, dst, true);

        // Draw the path on the canvas
        canvas.drawPath(dst, paint);
    }
}

