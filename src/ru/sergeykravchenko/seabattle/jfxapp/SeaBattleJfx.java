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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.sergeykravchenko.seabattle.consoleapp.UIController;
import ru.sergeykravchenko.seabattle.gameseabattle.GameSeaBattle;
import ru.sergeykravchenko.seabattle.player.ComputerPlayer;
import ru.sergeykravchenko.seabattle.player.Player;

public class SeaBattleJfx extends Application {

  public static Scene uiScene;
  public static UIController hInstance = null;   // Front OS/UI Controller
  public static Player hPlayer, hComputer;  // Player model Controllers
  public static GameSeaBattle hSeaBattle = null;   // Game model Controller

  private Parent root;

  public static void main(String[] args) {

        hInstance = new UIController(false);  // from console app for debug only
        hPlayer   = new Player(hInstance);
        hComputer = new ComputerPlayer(hInstance);
        hSeaBattle = new GameSeaBattle(hPlayer,hComputer);

        launch(args);
    }
    /*
    * Инициализация GUI  JavaFX  с использованием определений FXML (выполняется в потоке JavaFX)
    */

    @Override
    public void start(Stage stage) throws Exception {
     //
        root = FXMLLoader.load(getClass().getResource("seabattle.fxml"));

        init(stage);

        stage.show();

     //   play();

    }
    @Override
    public void stop() {
        if (JfxController.fireworkDraws!=null)
            JfxController.fireworkDraws.stop();
    }

    //public void play() { fireworkDraws.start(); }

    private void init(Stage stage)  {

        stage.setTitle("FXML Welcome SeaBattle");
        uiScene = new Scene(root);
        stage.setScene(uiScene);

    }


}
