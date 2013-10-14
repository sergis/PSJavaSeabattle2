package ru.sergeykravchenko.seabattle.gameseabattle;

/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 3 Домашнее задание/курсовая работа. класс ячейки поля игры морской бой/h5>
 * <p>Класс реализует создание и методы работы ячейки игрового полем игры  Морской бой </p>
 * <p>создает одну ячейку поля реализует ее состояния (свободна, занята палубой корабля, палуба разрушена, был выстрел)  и выдачу данных для отображения /p>
 * <p></p>
 * <p> MVC: класс контроллера модели игры</p>
 * <p>методы : </p> <ul>
 * <li>конструктор: запоминает играющих, инициализирует игровое поле, рейд с кораблями, устанавливает статус "настройка" </li>
 * <li>TODO: setShip(): устанавливает корабль на поле игры </li>
 * <li>TODO: shotCell(): принимает выстрел создает игровое поле заданного размера</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  Ship, SeaField
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */
public class SeaCell {
    public final static byte CELL_EMPTY =0, CELL_DECK =1, CELL_FIRED=2, CELL_DESTROYED =3;
    private Ship hShip;
    private byte stateCell;
    public SeaCell () {
        stateCell = CELL_EMPTY;
    }
}
