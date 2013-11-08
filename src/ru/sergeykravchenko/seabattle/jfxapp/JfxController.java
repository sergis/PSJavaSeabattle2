package ru.sergeykravchenko.seabattle.jfxapp;

import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ru.sergeykravchenko.seabattle.gameseabattle.Ship;
import ru.sergeykravchenko.seabattle.player.Player;
 /*
 *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 курсовая работа. класс контроллера интерфейса JAVAFX для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с интерфейсом отображения JAVAFX в игре  Морской бой </p>
 * <p> MVC: класс интерфейса отображения для игрока</p>
 * <p> обработчики выполняются в потоке JAVAFX, формы отображения задаются файлом FXML.</p>
 *
 * <p>взаимодействует с потоком игрока - компьютера. </p>
 * <p>методы : </p> <dl>
 * <dt>конструктор: </dt>
 * <dd>инициализирует данные об игроке, задает имя, статус активности (наблюдатель или нет)</dd>
 * <dt> <code>tunePlayer()</code>:</dt>
 * <dd> проверяет и установливает настройки игры задаваемые Игроком, в т.ч. расстановку кораблей </dd>
 * <dt> <code>fireBoom()</code>:</dt>
 * <dd> рeализует выстрел противника по полю игрока</dd>
 * <dt> <code>placePlayerNavy()</code>:</dt>
 * <dd> размещает случайным образом корабли на поле данного игрока</dd>
 * <dt> <code>place2ndPlayerNavy()</code>:</dt>
 * <dd> размещает случайным образом корабли на поле противника у данного игрока игрока</dd>
 * <dt> <code>placeShipsAutomatically(SeaField playerTheater)<code></dt>
 * <dd>размещает корабли случайным образом на указанном поле</dd>
 * </dl>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  ComputerPlayer, GameSeaBattle, SeaField, SeaCell
  *
 */

public class JfxController {

    static int xpos,ypos;
    public static FireworkDraws fireworkDraws;  // Firework field to draw
    JfxAnimations jfxAnimatons ;

    @FXML
    public StackPane playMapStack;


    private boolean fireTogglePressed = false;
    private boolean fireScopeTrace = false;
    boolean navyPlaced = false;
    // FadeTransition fadeTransition;
    private double sC = 0;
    private double sX = 0;
    private double sY = 0;

    @FXML
    private AnchorPane wholeWin;
    public AnchorPane getWholeWin() { return wholeWin; }

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
    private ListView<String> console;

    //create a observableArrayList of logged events that will be listed in console
    final ObservableList<String> consoleObservableList = FXCollections.observableArrayList();
    private void showOnConsole(String text) {
        //if there is 3 items in list, delete first log message, shift other logs and  add a new one to end position
        if (console.getItems().isEmpty())
            console.setItems(consoleObservableList);
        if (consoleObservableList.size() == 3) {
            consoleObservableList.remove(0);
        }
        consoleObservableList.add(text);
    }
    @FXML
    private  StackPane consoleMessages;

    public void showMessage(String msg) {
        //message.setText(msg);
        showOnConsole(msg);
    }

    @FXML
    void fireToggleButton(ActionEvent event) {
        showMessage("FireToggleButton pressed:");
        if (fireToggleB.isSelected()) {

            fireToggleB.setText("Fire!");
            jfxAnimatons = new JfxAnimations(this);
            computerTurn = new LinkedBlockingQueue<Future<int[]>>() ;
            computerTurnLog= new ArrayList<int[]>();
            placeNavyAuto();
            menuPlaceShipsAuto.setDisable(true);

            fireTogglePressed=true;
            scopeVisor.setVisible(true);
            showMessage("Your turn. Point Target! ");
        }
        else {
            fireTogglePressed=false;
        }
    }

    @FXML
    void playerMapClicked(MouseEvent event) {
    // TODO: сделать обработку установки источника анимированного выстрела, перетаскивания кораблей и т.п.
        System.out.println("Event.x:"+event.getX()+" y:"+event.getY());
        System.out.println("Scene.x:"+event.getSceneX()+" y:"+event.getSceneY());
        System.out.println("Screen.x:"+event.getScreenX()+" y:"+event.getScreenY());
        System.out.println("playerMapClicked.xpos:"+xpos+" ypos:"+ypos);
    }

    @FXML
    private MenuItem menuLoad;
    @FXML
    void menuLoadItem(ActionEvent event) {
       menuLoad.setDisable(true);
       showMessage("SORRY.Action LOAD disabled");
    }

    @FXML
    private MenuItem menuPlay;
    @FXML
    void menuPlayItem(ActionEvent event) {
        showMessage("Action Play called. Please press PLAY button");
    }

    // Quit the Java FX Application
    @FXML
    void menuQuitItem(ActionEvent event) {
        showMessage("GAME IS ABOUT TO QUIT");
        Platform.exit();
    }

    @FXML
    private MenuItem menuSave;
    @FXML
    void menuSaveItem(ActionEvent event) {
       showMessage("Action SAVE called");
        menuSave.setDisable(true);
        showMessage("SORRY.Action SAVE disabled");
    }

    @FXML
    private MenuItem menuStop;
    @FXML
    void menuStopItem(ActionEvent event) {
        //System.out.println
        showMessage("Action STOP called");
        menuStop.setDisable(true);
        showMessage("SORRY.Action STOP disabled");

    }
//  ***  Sub Menu Items of Menu.Tune definitions  ***
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
        showMessage("Action PLACE SHIP disabled");
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
            showMessage("Ships placed AUTOMATICALLY!");
        } else {
            return;
        }
    }

    @FXML
    void menuRemoveShip(ActionEvent event) {
        menuRemoveShip.setDisable(true);
        showMessage("*menuRemoveShip disabled!");
    }

    @FXML
    void menuRotateShip(ActionEvent event) {
        menuRotateShip.setDisable(true);
        showMessage("*menuRotateShip disabled!");
    }

    @FXML
    void menuAbout(ActionEvent event) {
        showAbout();
    }

    private void showAbout() {
        showMessage("(c)2013 Sergey Kravchenko.Java1 course work. www.prog-school.ru");
    }

    @FXML
    void initialize() {
        assert bkgNavy != null : "fx:id=\"bkgNavy\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert bullet != null : "fx:id=\"bullet\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert consoleMessages != null : "fx:id=\"consoleMessages\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert fireToggleB != null : "fx:id=\"fireToggleB\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert gameField != null : "fx:id=\"gameField\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert mainMenuBar != null : "fx:id=\"mainMenuBar\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuLoad != null : "fx:id=\"menuLoad\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuPlaceShip != null : "fx:id=\"menuPlaceShip\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuPlaceShipsAuto != null : "fx:id=\"menuPlaceShipsAuto\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuPlay != null : "fx:id=\"menuPlay\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuRemoveShip != null : "fx:id=\"menuRemoveShip\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuRotateShip != null : "fx:id=\"menuRotateShip\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuSave != null : "fx:id=\"menuSave\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert menuStop != null : "fx:id=\"menuStop\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert namesColsPlayerMap != null : "fx:id=\"namesColsPlayerMap\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert namesRowsPlayerMap != null : "fx:id=\"namesRowsPlayerMap\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert navyBank != null : "fx:id=\"navyBank\" was not injected: check your FXML file 'seabattle.fxml'.";
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
        assert wholeWin != null : "fx:id=\"wholeWin\" was not injected: check your FXML file 'seabattle.fxml'.";
        assert yMapNames != null : "fx:id=\"yMapNames\" was not injected: check your FXML file 'seabattle.fxml'.";

    }
    // инициализация отрисовок выстрелов и прицеливания на поле противника
    public void setFireTargeting() {         // Set Ons and binding for Fire Toggle Button Press

        JfxAnimations jfxAnimations= new JfxAnimations(this);


        if (!targetMap.getChildren().contains(scopeVisor))
            targetMap.getChildren().add(scopeVisor);
        scopeVisor.setLayoutX(targetMap.getWidth()/2);
        scopeVisor.setLayoutY(targetMap.getHeight()/2);
        //   Mouse Event Handlers
        targetMap.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                targetMap.setCursor(Cursor.HAND);
                if (fireTogglePressed){
                    fireScopeTrace = true;
                    sX=event.getX(); sY=event.getY();
                    sC = scopeVisor.getWidth()/2;
                    scopeVisor.setLayoutX(sX-sC*2);
                    scopeVisor.setLayoutY(sY-sC*2);
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
                if (fireScopeTrace) {
                    sX = event.getX();
                    sY = event.getY();
                    sC = scopeVisor.getWidth()/2;
                    if ((sX >= 0) && (sY >= 0) && (sX < targetMap.getWidth()) && (sY < targetMap.getHeight())) {
                        scopeVisor.setLayoutX(sX - sC);
                        scopeVisor.setLayoutY(sY - sC);
                        scopeVisor.toFront();
                    }
                }
            }
        });
        //
        targetMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (fireTogglePressed) {
                    sX = event.getX();
                    sY = event.getY();
                    sC = scopeVisor.getWidth()/2;
                    scopeVisor.setLayoutX(sX - sC);
                    scopeVisor.setLayoutY(sY - sC);

                    // find firing source
                    ArrayList<Ship> hShips = SeaBattleJfx.hPlayer.getPlayerNavyArray();
                    JfxAnimationQuery pQuery = new JfxAnimationQuery();
                    for (Ship ship : hShips)
                        if (ship.isInBattle()) {
                            pQuery.setShip2D(playerMap.localToScene(ship.getyCoordinate() * playerMap.getHeight() / 10 + 5,
                                                                    ship.getxCoordinate() * playerMap.getWidth() / 10 + 5
                                            ));
                            pQuery.setTarget2D(targetMap.localToScene(event.getX(), event.getY()));
                            jfxAnimatons.getQueueFireAnimation().add(pQuery);// animate player fire shot
                            break;
                        }
                    if ((fireOnMap(SeaBattleJfx.hPlayer, targetMap, sX, sY,pQuery ))) {
                        if (SeaBattleJfx.hComputer.isDefeated()) {
                            showMessage("You WON. GAME OVER");
                            showAbout();
                        } else {
                            showMessage("Your TURN. FIRE again");
                        }
                    } else { // Ответный ход компьютера
                        int[] fComputerTurn = new int[2];
                        computerTurn.add(execThreads.submit(SeaBattleJfx.hComputer));
                    }
                }
            }
        });
    // System.out.println("Fire  TARGETED ");
    }
    // выстрел по игровому полю
    // @return boolean true если попадание или нужно повторить выстрел (промах мимо поля)
    private boolean fireOnMap(Player firedPlayer, GridPane targetedMap, double sTargetX, double sTargetY, JfxAnimationQuery tQuery) {

        double tGridWidth  = targetedMap.getWidth()/10;
        double tGridHeight = targetedMap.getHeight()/10;
        int tGridRow = (int) (sTargetY /tGridHeight  );
        int tGridCol = (int) (sTargetX /tGridWidth );

        if ((tGridRow>=0)&&(tGridRow<10)&&(tGridCol>=0)&&(tGridCol<10)){
            if (tQuery!=null) {
                tQuery.setTargetMap(targetedMap,tGridRow,tGridCol);
                tQuery.setColor(Color.ORANGE);
            // System.out.println("target Row:"+tGridRow+" targetCol:"+tGridCol);
            if (SeaBattleJfx.hSeaBattle.fireTarget(firedPlayer,tGridCol,tGridRow))
                tQuery.setColor(Color.ORANGERED); // попал !
            else return false;//  промах. возможен ответный ход
            }
        }
        else showMessage("Target Row:" + tGridRow + " TargetCol:" + tGridCol + " is OUT OF MAP");

        return true; //  попал или надо повторить выстрел
    }
    //
    ExecutorService execThreads = Executors.newCachedThreadPool();
    Queue<Future<int[]>> computerTurn;
    ArrayList<int[]> computerTurnLog;
    int[] nextTurn;
    // every frame method callable to JFX controller from animation timer handler
    public void everyFrameHandler() {
        if (computerTurn!=null) {
            for (Future<?> iNextTurn : computerTurn)
                if (iNextTurn.isDone()) {

                    nextTurn = new int[2];
                    try {
                        nextTurn = (int[]) iNextTurn.get();
                      //  System.out.println("Computer TURN detected" + nextTurn);
                        computerTurn.remove();
                        computerTurnLog.add(nextTurn);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //ToDO: change body of catch statement
                    } catch (ExecutionException e) {
                        e.printStackTrace();  //ToDO: change body of catch statement
                    }
                    sX = nextTurn[0]*playerMap.getWidth()/10+5;
                    sY = nextTurn[1]*playerMap.getHeight()/10+5;
                    // find firing source
                    ArrayList<Ship> hCShips = SeaBattleJfx.hComputer.getPlayerNavyArray();
                    JfxAnimationQuery cQuery = new JfxAnimationQuery();

                    for (Ship ship : hCShips)
                        if (ship.isInBattle()) {
                            cQuery.setShip2D(targetMap.localToScene(ship.getxCoordinate() * targetMap.getWidth() / 10 + 5,
                                    ship.getyCoordinate() * targetMap.getHeight() / 10 + 5
                            ));
                            cQuery.setTarget2D(playerMap.localToScene(sX, sY));
                            cQuery.setPlay(false);
                            jfxAnimatons.getQueueFireAnimation().add(cQuery);// animate fire shot
                            break;
                        }
                    // ответный выстрел компьютера
                    if (fireOnMap(SeaBattleJfx.hComputer, playerMap, sX, sY, cQuery)) {
                        // компьютер попал
                        if (SeaBattleJfx.hPlayer.isDefeated()) {
                            showMessage("Computer WON. GAME OVER");
                            showAbout();
                        } else {
                            showMessage("Computer TURN. and FIRE again");
                            int[] fComputerTurn = new int[2];
                            computerTurn.add(execThreads.submit(SeaBattleJfx.hComputer));
                        }
                    }
                }
        }
        if (jfxAnimatons!=null)
            jfxAnimatons.execAnimateFireShot();
   }

    @FXML
    private HBox navyBank;
    public HBox getNavyBank() { return navyBank; }
    @FXML
    private HBox bullet;
    public HBox getBullet() {  return bullet; }

    public void setDevMode(boolean devMode) {
        if (!devMode) {
            menuRotateShip.setDisable(true);
            menuRemoveShip.setDisable(true);
            menuPlaceShip.setDisable(true);
            menuLoad.setDisable(true);
            menuSave.setDisable(true);
            menuPlay.setDisable(true);
            menuStop.setDisable(true);
            showAbout();
        }
    }
}
