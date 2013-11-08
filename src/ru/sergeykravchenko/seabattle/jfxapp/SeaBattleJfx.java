package ru.sergeykravchenko.seabattle.jfxapp;/**
 *
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Домашнее задание/курсовая работа. класс ${class} Игры морской бой/h5>
 * <p>Класс реализует запуск приложения игры для интерфейса JavaFX c использованием FXML файла определения UI</p>
 * <p>/p>
 * <p></p>
 * <p></p>
 * <p>методы : </p> <ul>
 * <li</li>
 * <li></li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see ru.sergeykravchenko.seabattle.jfxapp.JfxController,
 *
 * Created with IntelliJ IDEA. * Date: 27.10.13    * Time: 21:35
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.sergeykravchenko.seabattle.consoleapp.UIController;
import ru.sergeykravchenko.seabattle.gameseabattle.GameSeaBattle;
import ru.sergeykravchenko.seabattle.player.ComputerPlayer;
import ru.sergeykravchenko.seabattle.player.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SeaBattleJfx extends Application {

  public static Scene uiScene;
  public static UIController hInstance = null;   // Front OS/UI Controller
  public static Player hPlayer;            // main Player model Controller
  public static ComputerPlayer hComputer;  // 2nd Player model Controller
  public static Thread hComputerThread;
  public static GameSeaBattle hSeaBattle = null;   // Game model Controller
  public static JfxController mainJfx;
  // public static ExecutorService execThreads = Executors.newCachedThreadPool();
  private Group root = new Group();
  private FXMLLoader jfxLoader;

    public static void main(String[] args) {

        hInstance = new UIController(false);  // from console app for debug only
        hPlayer   = new Player(hInstance);
        hComputer = new ComputerPlayer(hInstance);
        hSeaBattle = new GameSeaBattle(hPlayer,hComputer);
        // hComputerThread = new Thread(hComputer);
        // hComputerThread.setDaemon(true);
        // hComputerThread.start();


        launch(args);
    }
    /*
    * Инициализация GUI  JavaFX  с использованием определений FXML (выполняется в потоке JavaFX)
    */

    @Override
    public void start(Stage stage) throws Exception {
     //

        jfxLoader = new FXMLLoader(getClass().getResource("seabattle.fxml"));
        Parent rootP = (Parent) jfxLoader.load();

        mainJfx = ((JfxController) jfxLoader.getController());
        init(stage);
        root.getChildren().add(rootP);

        mainJfx.fireworkDraws = new FireworkDraws(root);
        mainJfx.setFireTargeting();


        stage.show();
        System.out.println("start:"+Thread.currentThread().getName() + "  Thread! " );
        mainJfx.fireworkDraws.start();
        mainJfx.showMessage("Game is about to START! ");
        mainJfx.showMessage("Press PLAY button to play with automatic ship placement.");
        mainJfx.setDevMode(false);
    }
    @Override
    public void stop() {

            mainJfx.fireworkDraws.stop();
    }

    //public void play() { fireworkDraws.start(); }

    private void init(Stage stage)  {
        System.out.println("init:"+Thread.currentThread().getName() + "  Thread! " );
        stage.setTitle("FXML Welcome SeaBattle");
        uiScene = new Scene(root);
        stage.setScene(uiScene);

    }


}
