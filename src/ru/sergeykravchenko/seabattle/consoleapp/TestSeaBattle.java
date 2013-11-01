package ru.sergeykravchenko.seabattle.consoleapp;

import ru.sergeykravchenko.seabattle.player.Player;
/*
 *  Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 4 Домашнее задание/курсовая работа. класс Тестирование для игры морской бой/h5>
 * <p>Класс реализует создание тестовых данных  и методы тестирования для игры  Морской бой </p>
 * <p>/p>
 * <p></p>
 * <p> </p>
 * <p>методы : </p> <ul>
 * <li>конструктор: инициализирует данные о режимах тестирования и тестовые наборы данных</li>
 * <li>  getTestCmd(): создает очередь режимов работы основного контроллера игры для пакетного тестирования</li>
 * <li> TODO: ___():</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  ,
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */

public class TestSeaBattle {





    private static final UIController.InstanceMode[] testCmdQueue;
    private static final Player.PlayerMode[] testPlayerCmdQueue;

    static {
        testCmdQueue = new UIController.InstanceMode[]{UIController.InstanceMode.TUNER,
                UIController.InstanceMode.START,
                UIController.InstanceMode.PLAY, UIController.InstanceMode.PLAY,
                UIController.InstanceMode.STOP, UIController.InstanceMode.QUIT};
        testPlayerCmdQueue = new Player.PlayerMode[] {
                Player.PlayerMode.WAIT, Player.PlayerMode.SETNAME,
                Player.PlayerMode.SETSHIP, Player.PlayerMode.SETSHIP,
                Player.PlayerMode.WAIT, Player.PlayerMode.START
        } ;
    }
    private short testCmdNext, testPlayerCmdNext;

    public TestSeaBattle() { testCmdNext=0; // Start from the testCmdQueue[0]
        testPlayerCmdNext = 0;
        System.out.println("Test SeaBattle started");
    }

    public UIController.InstanceMode getTestCmd() {
        UIController.InstanceMode nextCmd;
        System.out.println("Get Test Command:started");
        if (testCmdNext < testCmdQueue.length) {
            nextCmd = testCmdQueue[testCmdNext];
            testCmdNext++;
        } else nextCmd = UIController.InstanceMode.QUIT; //
        System.out.println("Test Send Command:#" + nextCmd);
        return (nextCmd);
    }
    public Player.PlayerMode getTestPlayerCmd() {
        Player.PlayerMode nextCmd;
    //    System.out.println("Get Test Player Command started");
        if (testPlayerCmdNext < testPlayerCmdQueue.length) {
            nextCmd = testPlayerCmdQueue[testPlayerCmdNext];
            testPlayerCmdNext++;
        } else nextCmd = Player.PlayerMode.QUIT; //
        System.out.println("Test Player Send Command:#" + nextCmd);
        return (nextCmd);
    }
}