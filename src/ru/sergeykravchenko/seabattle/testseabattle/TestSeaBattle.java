package ru.sergeykravchenko.seabattle.testseabattle;

import ru.sergeykravchenko.seabattle.player.Player;
import ru.sergeykravchenko.seabattle.uicontroller.UIController;
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
    private static final short[] testCmdQueue;
    private static final byte[] testPlayerCmdQueue;

    static {
        testCmdQueue = new short[]{UIController.CMD_TUNER, UIController.CMD_START,
                UIController.CMD_PLAY, UIController.CMD_PLAY,
                UIController.CMD_STOP, UIController.CMD_QUIT};
        testPlayerCmdQueue = new byte[] {
                Player.PLAYER_WAIT, Player.PLAYER_SETNAME,
                Player.PLAYER_SETSHIP, Player.PLAYER_SETSHIP,
                Player.PLAYER_WAIT, Player.PLAYER_START
        } ;
    }
    private short testCmdNext, testPlayerCmdNext;

    public TestSeaBattle() { testCmdNext=0; // Start from the testCmdQueue[0]
        testPlayerCmdNext = 0;
        System.out.println("Test SeaBattle started");
    }

    public short getTestCmd() {
        short nextCmd;
        System.out.println("Get Test Command:started");
        if (testCmdNext < testCmdQueue.length) {
            nextCmd = testCmdQueue[testCmdNext];
            testCmdNext++;
        } else nextCmd = UIController.CMD_QUIT; //
        System.out.println("Test Send Command:#" + nextCmd);
        return (nextCmd);
    }
    public byte getTestPlayerCmd() {
        byte nextCmd;
        System.out.println("Get Test Player Command started");
        if (testPlayerCmdNext < testPlayerCmdQueue.length) {
            nextCmd = testPlayerCmdQueue[testPlayerCmdNext];
            testPlayerCmdNext++;
        } else nextCmd = Player.PLAYER_QUIT; //
        System.out.println("Test Player Send Command:#" + nextCmd);
        return (nextCmd);
    }
}