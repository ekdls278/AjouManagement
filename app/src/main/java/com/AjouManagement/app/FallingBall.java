package com.AjouManagement.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class FallingBall extends View {
    Context context;
    private ShapeDrawable mDrawable;

    private ArrayList<ShapeDrawable> balls; // 공들을 저장하는 ArrayList

    // 그래픽 객체의 중심 좌표
    private ArrayList<Point> positions; // 공들의 좌표를 저장하는 ArrayList

    // 그래픽 객체가 움직이는 방향
    private ArrayList<Integer> directionsX; // x방향 이동 방향을 저장하는 ArrayList
    private ArrayList<Integer> directionsY; // y방향 이동 방향을 저장하는 ArrayList

    //공의 초기값 (x, y, 너비, 높이)
//    int x = 1000;
//    int y = 0;
    int width = 100;
    int height = 100;
//
//    //그래픽 객체의 중심 좌표
//    int cx, cy;
//
//    //그래픽 객체가 움직이는 방향
//    int dir_x =1;
//    int dir_y = 1;

    //x, y값의 변화량
    int dx = 5;
    int dy = 5;

    int screen_width; //= Resources.getSystem().getDisplayMetrics().widthPixels;
    int screen_height; //= Resources.getSystem().getDisplayMetrics().heightPixels;



    public FallingBall(Context context) {
        super(context);
        balls = new ArrayList<>();
        positions = new ArrayList<>();
        directionsX = new ArrayList<>();
        directionsY = new ArrayList<>();
        this.context = context;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screen_width = w;
        screen_height = h;
    }

    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < balls.size(); i++) {
            ShapeDrawable ball = balls.get(i);
            Point position = positions.get(i);
            int directionX = directionsX.get(i);
            int directionY = directionsY.get(i);

            // 그래픽 객체의 중심 좌표
            int cx = position.x + ball.getBounds().width() / 2;
            int cy = position.y + ball.getBounds().height() / 2;

            // x방향의 전환
            if (cx <= ball.getBounds().width() / 2 || cx >= screen_width - ball.getBounds().width() / 2) {
                directionX = -directionX;
                directionsX.set(i, directionX);
            }
            // y방향의 전환
            if (cy <= ball.getBounds().height() / 2 || cy >= screen_height - ball.getBounds().height() / 2) {
                // 바닥에 닿았을 때 점점 멈추도록 처리
                if (cy >= screen_height - ball.getBounds().height() / 2) {
                    // x방향으로 이동 거리를 서서히 감소
                    if (directionX > 0) {
                        directionX--;
                    } else if (directionX < 0) {
                        directionX++;
                    }

                    // y방향으로 이동 거리를 서서히 감소
                    if (directionY > 0) {
                        directionY--;
                    } else if (directionY < 0) {
                        directionY++;
                    }
                }
            }
            // x, y 방향의 이동
            position.x += directionX * dx;
            position.y += directionY * dy;

            ball.setBounds(position.x, position.y, position.x + ball.getBounds().width(), position.y + ball.getBounds().height());
            ball.draw(canvas);

            // 충돌 검사
            for (int j = i + 1; j < balls.size(); j++) {
                ShapeDrawable otherBall = balls.get(j);
                Point otherPosition = positions.get(j);
                int otherDirectionX = directionsX.get(j);
                int otherDirectionY = directionsY.get(j);

                // 두 공의 중심 좌표
                int otherCx = otherPosition.x + otherBall.getBounds().width() / 2;
                int otherCy = otherPosition.y + otherBall.getBounds().height() / 2;

                // 공 간의 거리 계산
                double distance = Math.sqrt(Math.pow(cx - otherCx, 2) + Math.pow(cy - otherCy, 2));

                // 충돌 검사
                if (distance <= ball.getBounds().width() / 2 + otherBall.getBounds().width() / 2) {
                    // 충돌 시 방향을 반대로 변경
                    directionX = 0;
                    directionY = 0;
                    otherDirectionX = 0;
                    otherDirectionY = 0;
                    directionsX.set(i, directionX);
                    directionsY.set(i, directionY);
                    directionsX.set(j, otherDirectionX);
                    directionsY.set(j, otherDirectionY);

                    // 충돌한 공들을 떨어뜨리기 위해 위치 조정
                    int moveDistance = ball.getBounds().width() / 2 + otherBall.getBounds().width() / 2 - (int) distance + 1;
                    int moveX = (int) (moveDistance * Math.cos(Math.atan2(cy - otherCy, cx - otherCx)));
                    int moveY = (int) (moveDistance * Math.sin(Math.atan2(cy - otherCy, cx - otherCx)));

                    position.x += moveX;
                    position.y += moveY;
                    otherPosition.x -= moveX;
                    otherPosition.y -= moveY;
                }
            }
        }

        invalidate();
    }

    public void createBall(int x, int y, int directionX, int directionY, int color, String title) {
        TextDrawable ball = new TextDrawable(title, Typeface.DEFAULT);
        ball.getPaint().setColor(color);
        ball.setBounds(x, y, x + width, y + height); // 공의 위치와 크기 설정

        balls.add(ball);

        Point position = new Point(x, y);
        positions.add(position);

        directionsX.add(directionX);
        directionsY.add(directionY);

        invalidate();
    }

    public FallingBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        balls = new ArrayList<>();
        positions = new ArrayList<>();
        directionsX = new ArrayList<>();
        directionsY = new ArrayList<>();
        mDrawable = new ShapeDrawable(new OvalShape());
        //mDrawable.getPaint().setColor(Color.RED);
    }

    public FallingBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        balls = new ArrayList<>();
        positions = new ArrayList<>();
        directionsX = new ArrayList<>();
        directionsY = new ArrayList<>();
        mDrawable = new ShapeDrawable(new OvalShape());
        //mDrawable.getPaint().setColor(Color.RED);
    }

    public FallingBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        balls = new ArrayList<>();
        positions = new ArrayList<>();
        directionsX = new ArrayList<>();
        directionsY = new ArrayList<>();
        mDrawable = new ShapeDrawable(new OvalShape());
        //mDrawable.getPaint().setColor(Color.RED);
    }
}


