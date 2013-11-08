package ru.sergeykravchenko.seabattle.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.rules.Timeout;
import ru.sergeykravchenko.seabattle.gameseabattle.GameSeaBattle;
import ru.sergeykravchenko.seabattle.player.Player;

/**
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Домашнее задание/курсовая работа. класс тестирования класса Игрока (<code>Player</code>) Игры морской бой/h5>
 * <p> реализует проверку методов автоматического размещения кораблей
 * через проверку методов  <code>placePlayerNavy()</code> и <code>place2ndPlayerNavy()</code>
 * а также TODO: метода настройки игрока</p>
 * <p>/p>
 * <p></p>
 * <p></p>
 * <p>методы : </p> <ul>
 * <li</li>
 * <li></li>
 * <ul>
 *
 * @author Sergey Kravchenko
 * @version 0.0
 * @see Created with IntelliJ IDEA. * Date: 22.10.13    * Time: 20:56
 */

public class PlayerTest {
    private Player testPlayer, testPlayer2;
    GameSeaBattle game;

    @Rule
    public Timeout globalTimeout = new Timeout(20000); // 20 seconds max per method tested

    @Test  //(Timeout=20000)
    public void testPlacePlayerNavy() throws Exception {
       for (int i=0;i<10000;i++){
           testPlayer = new Player(null);
           testPlayer2 = new Player(null);
           game = new GameSeaBattle(testPlayer,testPlayer2);
           assertTrue(testPlayer.placePlayerNavy());
           assertTrue(testPlayer2.placePlayerNavy());
        }
    }

    @Test
    public void testPlace2ndPlayerNavy() throws Exception {
        for (int i=0;i<10000;i++){
        testPlayer = new Player(null);
        testPlayer2 = new Player(null);
        game = new GameSeaBattle(testPlayer,testPlayer2);

        assertTrue(testPlayer.place2ndPlayerNavy());
        assertTrue(testPlayer2.place2ndPlayerNavy());
        }
    }
}
