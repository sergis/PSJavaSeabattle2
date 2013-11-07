package ru.sergeykravchenko.seabattle.jfxapp;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

public  class JfxAnimationQuery {
    volatile boolean play;
    Point2D ship2D;
    Point2D target2D;
    GridPane targetedMap;
    Paint color;
    int gridRow;
    int gridCol;
    public JfxAnimationQuery() {
        play=false;
    }
    public void setShip2D (Point2D ship2D) {this.ship2D=ship2D;}
    public void setTarget2D (Point2D target2D) {this.target2D=target2D;}
    public void setTargetMap (GridPane targetMap,int row, int col) {
        this.targetedMap=targetMap;
        this.gridRow =row;
        this.gridCol =col;
    }
    public void setPlay(boolean play) {this.play=play;}
    public boolean isPlay() {return play;}
    public Point2D getShip2D () {return ship2D;}
    public Point2D getTarget2D () {return target2D;}
    public GridPane getTargetMap() {return targetedMap;}
    public int getGridRow () {return gridRow;}
    public int getGridCol () {return gridCol;}
    public Paint getColor() {return color;}
    public void setColor(Paint color) {this.color=color;}
}