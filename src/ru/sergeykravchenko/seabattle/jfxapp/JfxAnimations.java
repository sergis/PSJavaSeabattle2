package ru.sergeykravchenko.seabattle.jfxapp;

import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class JfxAnimations {
    private final JfxController jfxController;// method to draw fire bullet transition
    Path path ;
    PathTransition pathTransition;
    private Queue<JfxAnimationQuery> queueFireAnimation;
    private int tGridWidth;
    public JfxAnimations(JfxController jfxController) {
        this.jfxController = jfxController;
        tGridWidth=0;
        queueFireAnimation = new LinkedBlockingQueue<JfxAnimationQuery>();//LinkedList<JfxAnimationQuery>();
        path = new Path();
        pathTransition = new PathTransition();
        jfxController.getWholeWin().getChildren().add(path);
    }
    //
    public Queue<JfxAnimationQuery> getQueueFireAnimation () { return queueFireAnimation;};
    //
    public synchronized void execAnimateFireShot() {
        double controlX = 250f;
        double controlY = 0f;
        JfxAnimationQuery aQuery = queueFireAnimation.peek();

        if ((aQuery != null) && (!aQuery.play)) {
            aQuery.play=true;
      //      System.out.println("execAnimation:"+Thread.currentThread().getName() + " ! " );
            final double startX = aQuery.ship2D.getX();
            final double startY = aQuery.ship2D.getY();
            final double targetX = aQuery.target2D.getX();
            final double targetY = aQuery.target2D.getY();

            if (jfxController.getNavyBank().getChildren().contains(jfxController.getBullet()))
                jfxController.getNavyBank().getChildren().remove(jfxController.getBullet());
            if (!jfxController.getWholeWin().getChildren().contains(jfxController.getBullet()))
                jfxController.getWholeWin().getChildren().add(jfxController.getBullet());
            jfxController.getBullet().setLayoutX(0f);  // initial pos X on the path;
            jfxController.getBullet().setLayoutY(0f);  // initial pos Y on the path;
            jfxController.getBullet().setVisible(true);

            path.getElements().clear();
            path.getElements().add(new MoveTo(startX, startY));
            path.getElements().add(new QuadCurveTo(controlX, controlY, targetX, targetY));
            //  path.setStroke(Color.LIGHTSKYBLUE);//ORANGERED);
            path.setStrokeWidth(0f);
            PathTransition pathTransition = PathTransitionBuilder.create()
                .node(jfxController.getBullet())
                .path(path)
                .duration(Duration.millis(2000))
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .cycleCount(1)
                .build();
            pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (jfxController.getWholeWin().getChildren().contains(jfxController.getBullet())) {
                        jfxController.getWholeWin().getChildren().remove(jfxController.getBullet());
                    }
                    JfxAnimationQuery pQuery = queueFireAnimation.peek();
                    if (pQuery!=null) {
                        jfxController.fireworkDraws.explodeBullet(pQuery.target2D.getX(),pQuery.target2D.getY());
                        GridPane targetMap = pQuery.getTargetMap();
                        if (targetMap!=null) {
                            Circle firedCell = new Circle(targetMap.getWidth()/20-2, Color.ORANGE);
                            targetMap.getChildren().add(firedCell);
                            targetMap.setRowIndex(firedCell,pQuery.getGridRow());
                            targetMap.setColumnIndex(firedCell,pQuery.getGridCol());
                            firedCell.setOpacity(0.9f);
                            firedCell.setFill(pQuery.color);
                        }
                        queueFireAnimation.remove();
                    }
                }
            });
            pathTransition.play();
        }
    }
}

