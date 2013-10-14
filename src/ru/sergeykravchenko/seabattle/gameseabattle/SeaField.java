package ru.sergeykravchenko.seabattle.gameseabattle;

/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 3 Домашнее задание/курсовая работа. класс Игрового поля Игры морской бой/h5>
 * <p>Класс реализует создание и работу с игровым полем игры  Морской бой. MVC: субконтроллер модели игры</p>
 * <p>создает игровое поле заданного размера из классов ячеек поля, реализует методы управления полем и выдачи данных для отображения /p>
 * <p></p>
 * <p> </p>
 * <p>методы : </p> <ul>
 * <li>конструктор: запоминает играющих, инициализирует игровое поле, рейд с кораблями, устанавливает статус "настройка" </li>
 * <li>TODO: setShip(): устанавливает корабль на поле игры </li>
 * <li>TODO: shotCell(): принимает выстрел создает игровое поле заданного размера</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see
 *
 * ? Player, ComputerPlayer, TestSeaBattle,
 * TODO: классы SeaCell
 *
 */
public class SeaField {
    private SeaCell[][] hSeaCell ;
    public short seaSize;
    public SeaField(short size){
        seaSize = size;
        hSeaCell = new SeaCell[size][size];
    }
}
