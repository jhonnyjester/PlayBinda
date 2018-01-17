package com.jhony.jester.play.Activitys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhony.jester.play.R;

public class AboutActivity extends AppCompatActivity {

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));

    }

    static class GraphicsView extends View {
        Paint tPaint;
        public GraphicsView(Context context) {
            super(context);

            tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            tPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            tPaint.setColor(Color.BLACK);
            tPaint.setTextSize(50);

        /*
        * About the app
        * civ of the logo
        * app version
        * app description
        *
        * About the developer
        * civ of vector
        * name
        * github link
        *
        * Open source licences new page
        * list all the licenses
        * open doc of the license on tap
        *
        * feedback
        * report*/
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Path path = new Path();
            path.addCircle(500, 100, 100, Path.Direction.CW);
            canvas.drawTextOnPath("Hey Binda", path, 10, 10, tPaint);
        }
    }
}

