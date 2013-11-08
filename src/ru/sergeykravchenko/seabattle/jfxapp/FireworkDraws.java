package ru.sergeykravchenko.seabattle.jfxapp;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Домашнее задание/курсовая работа. класс FireworkDraws для игры морской бой/h5>
 * <p> реализует создание и методы работы с объектом FireworkDraws игры  Морской бой </p>
 * <p>MVC: класс контроллера модели игры/p>
 * <p></p>
 * <p> Based on source of:
 * Java  Ensemble Samples(Fireworks in Canvas) Copyright (c) 2008, 2012 Oracle and/or its affiliates.</p>
 * <p>методы : </p> <ul>
 * @author Sergey Kravchenko   , based on Oracle
 * @version 0.0
 * @see  , 
 */
public class FireworkDraws extends Pane {
    final int EXPLOSIONS =24, FIRESIZE=9;

    private final AnimationTimer timer;
    private final Canvas canvas;

    private final List<Particle> particles = new ArrayList<Particle>();
    private final Paint[] colors;
  //  private int countDownTillNextFirework = 40;

    public FireworkDraws(Group parentStackPane){//StackPane parentStackPane) {
         // create a color palette of 180 colors
         colors = new Paint[181];
         colors[0] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                            new Stop(0, Color.WHITE),
                            new Stop(0.2, Color.hsb(59, 0.38, 1)),
                            new Stop(0.6, Color.hsb(59, 0.38, 1,0.1)),
                            new Stop(1, Color.hsb(59, 0.38, 1,0))
                            );
         //
         for (int h=0;h<360;h+=2) {
             colors[1+(h/2)] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                            new Stop(0, Color.WHITE),
                            new Stop(0.2, Color.hsb(h, 1, 1)),
                            new Stop(0.6, Color.hsb(h, 1, 1,0.1)),
                            new Stop(1, Color.hsb(h, 1, 1,0))
                            );
         }
         // create canvas

         canvas = new Canvas(700, 400);
         canvas.setBlendMode(BlendMode.ADD);
         canvas.setEffect(new Reflection(0,0.4,0.15,0));

         parentStackPane.getChildren().add(canvas);
         canvas.setMouseTransparent(true);
         // create animation timer that will be called every frame
         timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        SeaBattleJfx.mainJfx.everyFrameHandler();
                        drawframe();
                    }
         };
    }  // constructor done

    // drawing every frame
    public void drawframe() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // clear area with transparent black
        gc.setFill(Color.rgb(0, 0, 0, 0.2));
        gc.fillRect(0, 0, 700, 400);
        // draw fireworks
        drawFireworks(gc);
    }
    public void start() { timer.start(); }
    public void stop() { timer.stop(); }
    //  Fire Bullet Particle Explosion
    public void explodeBullet(double targetX, double targetY){

        particles.add(new Particle(
                targetX,  targetY,       // posX,  posY
                0f, 0f,                    // velX,  velY,
                targetX,  targetY, //0, 150 + Math.random() * 100,// double targetX, double targetY
                colors[0], FIRESIZE,                // Paint color,int size
                false, true, true));        // NOT use Physics, shouldExplodeChildren, hasTail
    }
        /**
          * Make resizable and keep background image proportions and center.
          */
    @Override
    protected void layoutChildren() {

                final double w = getWidth();
                final double h = getHeight();
                final double scale = Math.min(w/1024d, h/708d);
                final int width = (int)(1024*scale);
                final int height = (int)(708*scale);
                final int x = (int)((w-width)/2);
                final int y = (int)((h-height)/2);

                canvas.relocate(x, y);
                canvas.setWidth(width);
                canvas.setHeight(height *0.9);// 0.706);
    }

    private void drawFireworks(GraphicsContext gc) {

           Iterator<Particle> iter = particles.iterator();
           List<Particle> newParticles = new ArrayList<Particle>();
           while(iter.hasNext()) {
               Particle firework = iter.next();
               if(firework.update()) {// if the update returns true then particle has expired
                    iter.remove();     // remove particle from those drawn
                    // check if it should be exploded
                    if(firework.shouldExplodeChildren) {
                        if(firework.size == FIRESIZE) {
                           explodeCircle(firework, newParticles);
                        }
                    }
               }
               firework.draw(gc);
           }
           particles.addAll(newParticles);
    }

    private void fireParticle() {

        particles.add(new Particle(
                      canvas.getWidth()*0.5, canvas.getHeight()+10,
                      Math.random() * 5 - 2.5, 0,  //double velX, double velY,
                      0, 150 + Math.random() * 100,//
                      colors[0], FIRESIZE,                // Paint color,int size
                      false, true, true));        // DO NOT use Physics, shouldExplodeChildren, hasTail
    }
    // Explode big circle into smaller ones
    private void explodeCircle(Particle firework, List<Particle> newParticles) {

        final int count = EXPLOSIONS *2 + (int)(60*Math.random());
        final boolean shouldExplodeChildren = Math.random() > 0.5;
        final double angle = (Math.PI * 2) / count;
        final int color = (int)(Math.random()*colors.length);

        for(int i=count; i>0; i--) {
            double randomVelocity = 4 + Math.random() * 4;
            double particleAngle = i * angle;
            newParticles.add(new Particle(firework.posX, firework.posY,
                             Math.cos(particleAngle) * randomVelocity, Math.sin(particleAngle) * randomVelocity,
                             0, 0,
                             colors[color], 8,                    // Paint color,int size
                             true, shouldExplodeChildren, true)); // USE Physics, shouldExplodeChildren, hasTail
        }
    }
} // class  FireworkDraws ended
/**
  * A Simple Particle that draws its self as a circle.
* */
class Particle {

    private static final double GRAVITY = 0.06;
            // properties for animation
            // and colouring
            double alpha;
            final double easing;
            double fade;
            double posX;
            double posY;
            double velX;
            double velY;
            final double targetX;
            final double targetY;
            final Paint color;
            final int size;
            final boolean usePhysics;
            final boolean shouldExplodeChildren;
            final boolean hasTail;
            double lastPosX;
            double lastPosY;

    public Particle(double posX, double posY, double velX, double velY, double targetX, double targetY,
                    Paint color,int size, boolean usePhysics, boolean shouldExplodeChildren,
                    boolean hasTail) {
        //
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.color = color;
        this.size = size;
        this.usePhysics = usePhysics;
        this.shouldExplodeChildren = shouldExplodeChildren;
        this.hasTail = hasTail;
        this.alpha    = 1;
        this.easing   = Math.random() * 0.02;
        this.fade     = Math.random() * 0.1;
    }
   //  @returns  true if particle has expired
    public boolean update() {
        lastPosX = posX;
        lastPosY = posY;
        if(this.usePhysics) { // on way down
            velY += GRAVITY;
            posY += velY;
            this.alpha -= this.fade; // fade out particle
        } else { // on way up
                double distance = (targetY - posY);
                // ease the position
                posY += distance * (0.03 + easing);
                // cap to 1
                alpha = Math.min(distance * distance * 0.00005, 1);
                }
        posX += velX;
        return alpha < 0.005;
    }
    // draw particle in Graphics context
    public void draw(GraphicsContext context) {

            final double x = Math.round(posX);
            final double y = Math.round(posY);
            final double xVel = (x - lastPosX) * -5;
            final double yVel = (y - lastPosY) * -5;
                // set the opacity for all drawing of this particle
            context.setGlobalAlpha(Math.random() * this.alpha);
                // draw particle
            context.setFill(color);
            context.fillOval(x-size, y-size, size+size, size+size);
                // draw the arrow triangle from where we were to where we are now
            if (hasTail) {
               context.setFill(Color.rgb(255,255,255,0.3));
               context.fillPolygon(new double[]{posX + 1.5,posX + xVel,posX - 1.5},
                                   new double[]{posY,posY + yVel,posY}, 3);
            }
    }
}
//:~
