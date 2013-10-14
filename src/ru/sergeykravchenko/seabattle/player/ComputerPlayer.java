package ru.sergeykravchenko.seabattle.player;

import ru.sergeykravchenko.seabattle.player.Player;
import ru.sergeykravchenko.seabattle.uicontroller.UIController;

/*
 *  Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 4 Домашнее задание/курсовая работа. класс ComputerPlayer для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Игрока-компьютера в игре  Морской бой </p>
 * <p>/p>
 * <p></p>
 * <p> MVC: класс контроллера интерфейса  игры</p>
 * <p>методы : </p> <ul>
 * <li>конструктор: инициализирует данные об игроке, задает имя компьютера, статус активности (наблюдатель или нет)
 * <li>  </li>
 * <li> TODO: ___():</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  ,
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */
public class ComputerPlayer extends Player {
    public ComputerPlayer(UIController hInstance) {
        super(hInstance);
        isObserver = true; /* No Input needed for Computer Players*/
        cmdPlayer = PLAYER_START; // No Negotiations with other Players
        playerName += "Computer";
        System.out.println ("Game Player Controller extended to:"+playerName);
    }
}
