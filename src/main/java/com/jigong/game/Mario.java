package com.jigong.game;

import java.awt.image.BufferedImage;

public class Mario implements Runnable {
    //表示横纵坐标
    private int x;
    private int y;
    //用于表示当前的状态
    private String status;

    //用于显示当前状态对应的图像
    private BufferedImage show = null;
    //定义一个Background对象，用来获取障碍物的信息
    private BackGround backGround = new BackGround();
    //用来实现马里奥的动作
    private Thread thread = null;
    //马里奥的移动速度
    private int xSpeed;
    //马里奥的跳跃速度
    private int ySpeed;
    //定义一个索引
    private int index;
    //表示马里奥的上升时间
    private int upTime = 0;
    public Mario() {
    }

    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand-right";
        thread = new Thread(this);
        thread.start();
    }

    //马里奥向左移动
    public void leftMove() {
        //改变速度
        xSpeed = -5;
        //判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "move--left";
        }
    }
    //马里奥向右移动
    public void rightMove() {
        xSpeed = 5;
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "move--right";
        }
    }

    //马里奥向左停止
    public void leftStop() {
        xSpeed = 0;
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "stop--left";
        }
    }

    //马里奥向右停止
    public void rightStop() {
        xSpeed = 0;
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "stop--right";
        }
    }

    //马里奥跳跃
    public void jump(){
        if (status.indexOf("jump") == -1){
            if (status.indexOf("left") == -1){
                status = "jump--left";
            }else {
                status = "jump--right";
            }
            ySpeed = -10;
            upTime = 7;
        }
    }

    public void fail(){
        if (status.indexOf("left") == -1){
            status = "jump--left";
        }else {
            status = "jump--right";
        }
        ySpeed = 10;
    }
    @Override
    public void run() {
        while (true) {
            //判断是否处于障碍物上
            boolean onObstacle = false;
            //遍历当前场景内的所有障碍物
            for (int i = 0;i < backGround.getObstacleList().size();i++){
                Obstacle ob = backGround.getObstacleList().get(i);
                //判断马里奥是否处于障碍物上
                if (ob.getY() == this.y+25 && (ob.getX() > this.x-30 && ob.getX() < this.x+25)){
                    onObstacle = true;
                }
            }
            //进行马里奥的跳跃操作


            if (xSpeed < 0 || xSpeed > 0) {
                x += xSpeed;
                //判断马里奥是否到达了最左边
                if (x < 0) {
                    x = 0;
                }
            }
            //判断当前是否是移动状态
            if (status.contains("move")) {
                index = index == 0 ? 1 : 0;
            }
            //判断是否在向左移动
            if ("move--left".equals(status)) {
                show = StaticValue.Run_L.get(index);
            }
            //判断是否在向左移动
            if ("move--right".equals(status)) {
                show = StaticValue.Run_R.get(index);
            }
            //判断是否在向左停止
            if ("stop--left".equals(status)) {
                show = StaticValue.stand_L;
            }
            //判断是否在向左移动
            if ("stop--right".equals(status)) {
                show = StaticValue.stand_R;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否是向左跳跃
            if ("jump--left".equals(status)){
                show = StaticValue.jump_L;
            }
            //判断是否是向右跳跃
            if ("jump--right".equals(status)){
                show = StaticValue.jump_R;
            }
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }
    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
}
