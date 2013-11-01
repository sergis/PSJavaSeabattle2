package ru.sergeykravchenko.seabattle.jfxapp;

import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ru.sergeykravchenko.seabattle.gameseabattle.Ship;
import ru.sergeykravchenko.seabattle.player.Player;


public class JfxController {

    static int xpos,ypos, xadd,yadd;
    public static FireworkDraws fireworkDraws;// = new FireworkDraws();      // Firework field to draw
    private boolean fireTogglePressed = false;
    private boolean fireScopeTrace = false;
    boolean navyPlaced = false;
    // FadeTransition fadeTransition;
    private double sC = 0;
    private double sX = 0;
    private double sY = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView bkgNavy;

    @FXML
    private ImageView shipWheel;


    @FXML
    private HBox scopeVisor;

    @FXML
    private HBox gameField;

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private HBox namesColsPlayerMap;

    @FXML
    private VBox namesRowsPlayerMap;

    @FXML
    private GridPane playMapGrids;

    @FXML
    private StackPane playMapStack;

    @FXML
    private GridPane playerMap;

    @FXML
    private HBox ship1d01;

    @FXML
    private HBox ship1d02;

    @FXML
    private HBox ship1d03;

    @FXML
    private HBox ship1d04;

    @FXML
    private HBox ship2dH01;

    @FXML
    private HBox ship2dH02;

    @FXML
    private HBox ship2dH03;

    @FXML
    private HBox ship2dV01;

    @FXML
    private HBox ship2dV02;

    @FXML
    private HBox ship2dV03;

    @FXML
    private VBox ships1dHorizontal;

    @FXML
    private VBox ships2dHorizontal;

    @FXML
    private HBox ships2dVertical;

    @FXML
    private HBox ship3dH01;

    @FXML
    private HBox ship3dH02;

    @FXML
    private VBox ships3dHorizontal;

    @FXML
    private HBox ship3dV01;

    @FXML
    private HBox ship3dV02;

    @FXML
    private HBox ships3dVertical;

    @FXML
    private HBox ship4dH01;

    @FXML
    private VBox ships4dHorizontal;

    @FXML
    private HBox ship4dV01;

    @FXML
    private HBox ships4dVertical;


    @FXML
    private GridPane targetMap;

    @FXML
    private HBox topHeader;

    @FXML
    private GridPane yMapNames;

    @FXML
    private ToggleButton fireToggleB;

    @FXML
    void fireToggleButton(ActionEvent event) { System.out.println("FireToggleButton pressed:");
        if (fireToggleB.isSelected()) {
            fireToggleB.setText("Fire!");
            placeNavyAuto();
            menuPlaceShipsAuto.setDisable(true);

            fireTogglePressed=true;
            setFireTargeting();   // TODO: move to firework initialization, don't nee too much event handlers!!!
            scopeVisor.setVisible(true);
            System.out.println("Point Target! ");
        }
        else {
            fireTogglePressed=false;
        }
    }

    @FXML
    void playerMapClicked(MouseEvent event) {

        System.out.println("Event.x:"+event.getX()+" y:"+event.getY());
        System.out.println("Scene.x:"+event.getSceneX()+" y:"+event.getSceneY());
        System.out.println("Screen.x:"+event.getScreenX()+" y:"+event.getScreenY());
        System.out.println("playerMapClicked.xpos:"+xpos+" ypos:"+ypos);
    }

    @FXML
    void menuLoadItem(ActionEvent event) {
        System.out.println("Action LOAD called");
    /*    if (fireworkDraws!=null){
            fireworkDraws.stop();
            // TODO: ??  STOP EVENTS IF ANY
        }
        fireworkDraws = new FireworkDraws(playMapStack);

        if (!playMapStack.getChildren().contains(fireworkDraws)) {

            playMapStack.getChildren().add(fireworkDraws);

            fireworkDraws.start();
//            setFireTargeting();
        }
        System.out.println("Fireworks & Targeting added ");
    */
    }
    //
    private void setFireTargeting() {
        // Set Ons and binding for Fire Toggle Button Press

//        System.out.println("Scopevisor.x:"+scopeVisor.layoutXProperty()+" y:"+scopeVisor.layoutYProperty());

        sC = scopeVisor.getWidth()/2;

        if (!targetMap.getChildren().contains(scopeVisor))
            targetMap.getChildren().add(scopeVisor);
        scopeVisor.setLayoutX(targetMap.getWidth()/2);
        scopeVisor.setLayoutY(targetMap.getHeight()/2);
        //   Mouse Event Handlers
        targetMap.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                targetMap.setCursor(Cursor.HAND);
                //    targetMap.setConstraints(scopeVisor,4,4);//,2,2, HPos.LEFT, VPos.TOP);
                if (fireTogglePressed){
//                    System.out.println("SCOPEvisor.x:"+scopeVisor.layoutXProperty()+" y:"+scopeVisor.layoutYProperty());
                    fireScopeTrace = true;
                    sX=event.getX(); sY=event.getY();
                    scopeVisor.setLayoutX(sX-sC);
                    scopeVisor.setLayoutY(sY-sC);
                    scopeVisor.setVisible(true);
                 }
            }
        });
        //
        targetMap.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                targetMap.setCursor(Cursor.DEFAULT);
                fireScopeTrace = false;
                scopeVisor.setVisible(false);
            }
        });
        //
        targetMap.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //    sX = event.getX()-sC-coordXReal.getValue();
                //    sY = event.getY()-sC-coordYReal.getValue();
                //    coordXReal.setValue(event.getX() -sX);
                //    coordYReal.setValue(event.getY() -sY);
                if (fireScopeTrace){
                    sX=event.getX(); sY=event.getY();
                    if ((sX>=0)&&(sY>=0)&&(sX<targetMap.getWidth())&&(sY<targetMap.getHeight()))
                    {
                        scopeVisor.setLayoutX(sX-sC);
                        scopeVisor.setLayoutY(sY-sC);
                        scopeVisor.toFront();
                    }
                }
            }
        });
        //
        targetMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (fireTogglePressed){
                    sX=event.getX(); sY=event.getY();
//                    System.out.println("Event.sX:"+sX+" sY:"+sY);
                    scopeVisor.setLayoutX(sX-sC);
                    scopeVisor.setLayoutY(sY-sC);

                    if ((fireToMap(SeaBattleJfx.hPlayer,targetMap, sX, sY)) ) {
                      if (SeaBattleJfx.hComputer.isDefeated()) {
                         System.out.println("You are WIN. GAME OVER");
                      } else {
                          System.out.println("FIRE again");
                      }
                    } else { // Ответный ход TODO: переделать через Game Controller!
                            // TODO: использовать другой поток для ответных ходов компьютера
                        if (fireToMap(SeaBattleJfx.hComputer, playerMap,sX,sY)) {
                            // компьютер попал
                            if (SeaBattleJfx.hPlayer.isDefeated()) {
                                System.out.println("Computer are WIN. GAME OVER");
                            } else {
                                System.out.println("Computer Should FIRE again");
                            }
                        }
                    }
                }
//                System.out.println("FIRED Target! ");
            }
        });
        System.out.println("Fire  TARGETED ");
    }
    // выстрел по игровому полю
    // @return boolean true если попадание или нужно повторить выстрел (промах мимо поля)
    private boolean fireToMap(Player firedPlayer, GridPane targetedMap, double sTargetX, double sTargetY) {
        //
        if (playMapStack.getChildren().contains(fireworkDraws)) {
            fireworkDraws.fireBullet(5,5,500,100);
            System.out.println("FireBullet FIRED ");
        }
        //  mark cell as fired
        double tGridWidth  = targetedMap.getWidth()/10;
        double tGridHeight = targetedMap.getHeight()/10;
        int tGridRow = (int) (sTargetY /tGridHeight  );
        int tGridCol = (int) (sTargetX /tGridWidth );
        if ((tGridRow>=0)&&(tGridRow<10)&&(tGridCol>=0)&&(tGridCol<10)){
            Circle firedCell;
            firedCell = new Circle(tGridWidth/2-2, Color.ORANGE);
           // System.out.println("target Row:"+tGridRow+" targetCol:"+tGridCol);
            targetedMap.getChildren().add(firedCell);
            targetedMap.setRowIndex(firedCell, tGridRow);
            targetedMap.setColumnIndex(firedCell, tGridCol);

            if (SeaBattleJfx.hSeaBattle.fireTarget(firedPlayer,tGridCol,tGridRow)) {
                // попал . System.out.println("Your FIRE hit the target! ");
                firedCell.setFill(Color.ORANGERED);
            }
            else return false;//  промах. возможен ответный ход

        }
        else System.out.println("target Row:"+tGridRow+" targetCol:"+tGridCol+ " OUT OF MAP");
        return true; //  попал или надо повторить выстрел
    } //

    @FXML
    void menuPlayItem(ActionEvent event) {
        System.out.println("Action Play called");
        // Temporary fire one bullet
        if (playMapStack.getChildren().contains(fireworkDraws)) {
            fireworkDraws.fireBullet(5,5,500,100);
            System.out.println("Fireworks started ");
        }

    }
    // Quit the Java FX Application
    @FXML
    void menuQuitItem(ActionEvent event) {
        System.out.println("GAME IS ABOUT TO QUIT");
        Platform.exit();
    }

    @FXML
    void menuSaveItem(ActionEvent event) {
        System.out.println("Action SAVE called");
      // Temporary starting fireworks
        if (playMapStack.getChildren().contains(fireworkDraws)) {
            fireworkDraws.start();
            System.out.println("Fireworks started ");
        }
    }

    @FXML
    void menuStopItem(ActionEvent event) {
        System.out.println("Action STOP called");
        // Temporary stopping fireworks
        if (playMapStack.getChildren().contains(fireworkDraws)) {
            fireworkDraws.stop();
            System.out.println("Fireworks STOPed ");
        }
    }
//  ***  Menu Tune definitions  ***
    @FXML
    private MenuItem menuPlaceShip;

    @FXML
    private MenuItem menuPlaceShipsAuto;

    @FXML
    private MenuItem menuRemoveShip;

    @FXML
    private MenuItem menuRotateShip;

    @FXML
    void menuPlaceShip(ActionEvent event) {
        menuPlaceShip.setDisable(true);
        System.out.println("Action PLACE SHIP called");

    }

    @FXML
    void menuPlaceShipsAuto(ActionEvent event) {
        placeNavyAuto();
        menuPlaceShipsAuto.setDisable(true);
    }
    // автоматическое размещение кораблей игрока и компьютера на поле для начала игры
    private void placeNavyAuto() {
        if (!navyPlaced) {
            SeaBattleJfx.hPlayer.placePlayerNavy();
            SeaBattleJfx.hComputer.placePlayerNavy();
            SeaBattleJfx.hInstance.renderSeaFieldConsole(SeaBattleJfx.hPlayer);
            SeaBattleJfx.hInstance.renderSeaFieldConsole(SeaBattleJfx.hComputer);
            // render ships on the field

            for (Ship hShip : SeaBattleJfx.hPlayer.getPlayerNavyArray()) {
                List<Node> nodeList;
                switch (hShip.getDecksOnShip()) {
                    case 4:
                        if (hShip.isVertical())
                            nodeList = ships4dVertical.getChildren();
                        else nodeList = ships4dHorizontal.getChildren();
                        break;
                    case 3:
                        if (hShip.isVertical())
                            nodeList = ships3dVertical.getChildren();
                        else nodeList = ships3dHorizontal.getChildren();
                        break;
                    case 2:
                        if (hShip.isVertical())
                            nodeList = ships2dVertical.getChildren();
                        else nodeList = ships2dHorizontal.getChildren();
                        break;
                    default:
                        nodeList = ships1dHorizontal.getChildren();
                        break;
                }
                if (!nodeList.isEmpty()) {
                    Iterator<Node> nodeIterator = nodeList.iterator();
                    if (nodeIterator.hasNext()) {
                        Node shipBox = nodeIterator.next();

                        playerMap.getChildren().add(shipBox);
                        if (hShip.isVertical())
                            playerMap.setConstraints(shipBox, hShip.getxCoordinate(), hShip.getyCoordinate(),
                                    1, hShip.getDecksOnShip(),
                                    HPos.CENTER, VPos.TOP);
                        else playerMap.setConstraints(shipBox, hShip.getxCoordinate(), hShip.getyCoordinate());

                    }
                }
            }
            navyPlaced = true;

            System.out.println("Ships placed!");
        } else {
            return;
        }
    }

    @FXML
    void menuRemoveShip(ActionEvent event) {
        menuRemoveShip.setDisable(true);
    }

    @FXML
    void menuRotateShip(ActionEvent event) {
        menuRotateShip.setDisable(true);
    }

    @FXML
    void menuAbout(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert bkgNavy != null : "fx:id=\"bkgNavy\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert fireToggleB != null : "fx:id=\"fireToggleB\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert gameField != null : "fx:id=\"gameField\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert mainMenuBar != null : "fx:id=\"mainMenuBar\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert namesColsPlayerMap != null : "fx:id=\"namesColsPlayerMap\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert namesRowsPlayerMap != null : "fx:id=\"namesRowsPlayerMap\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert playMapGrids != null : "fx:id=\"playMapGrids\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert playMapStack != null : "fx:id=\"playMapStack\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert playerMap != null : "fx:id=\"playerMap\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert scopeVisor != null : "fx:id=\"scopeVisor\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship1d01 != null : "fx:id=\"ship1d01\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship1d02 != null : "fx:id=\"ship1d02\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship1d03 != null : "fx:id=\"ship1d03\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship1d04 != null : "fx:id=\"ship1d04\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship2dH01 != null : "fx:id=\"ship2dH01\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship2dH02 != null : "fx:id=\"ship2dH02\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship2dH03 != null : "fx:id=\"ship2dH03\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship2dV01 != null : "fx:id=\"ship2dV01\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship2dV02 != null : "fx:id=\"ship2dV02\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship2dV03 != null : "fx:id=\"ship2dV03\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship3dH01 != null : "fx:id=\"ship3dH01\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship3dH02 != null : "fx:id=\"ship3dH02\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship3dV01 != null : "fx:id=\"ship3dV01\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship3dV02 != null : "fx:id=\"ship3dV02\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship4dH01 != null : "fx:id=\"ship4dH01\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ship4dV01 != null : "fx:id=\"ship4dV01\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert shipWheel != null : "fx:id=\"shipWheel\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ships1dHorizontal != null : "fx:id=\"ships1dHorizontal\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ships2dHorizontal != null : "fx:id=\"ships2dHorizontal\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ships2dVertical != null : "fx:id=\"ships2dVertical\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ships3dHorizontal != null : "fx:id=\"ships3dHorizontal\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ships3dVertical != null : "fx:id=\"ships3dVertical\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ships4dHorizontal != null : "fx:id=\"ships4dHorizontal\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert ships4dVertical != null : "fx:id=\"ships4dVertical\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert targetMap != null : "fx:id=\"targetMap\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert topHeader != null : "fx:id=\"topHeader\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert yMapNames != null : "fx:id=\"yMapNames\" was not injected: check your FXML file 'seabattle.fxml'.";



    }

}
