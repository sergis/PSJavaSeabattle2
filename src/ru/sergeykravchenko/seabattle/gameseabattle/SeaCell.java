package ru.sergeykravchenko.seabattle.gameseabattle;

/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 3 Домашнее задание/курсовая работа. класс ячейки поля игры морской бой/h5>
 * <p>Класс реализует создание и методы работы ячейки игрового полем игры  Морской бой </p>
 * <p>создает одну ячейку поля реализует ее состояния (свободна, занята палубой корабля, палуба разрушена, был выстрел)  и выдачу данных для отображения /p>
 * <p></p>
 * <p> MVC: класс контроллера модели игры</p>
 * <p>методы : </p> <ul>
 * <li>конструктор: создает ячейку со статусом <code>EMPTY</code>  (пустая) </li>
 * <li> <code>getCellState()</code> : возвращает статус ячейки </li>
 * <li>  <code>setCellAsDeck(Ship hhShip)</code>  запоминает ячейку как палубу указанного корабля
 * <li>  <code>setCellNeighbored()</code>  устанавливает ячейку как соседнюю с каким то кораблем
 *
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  Ship, SeaField
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */
public class SeaCell {
    public enum StateCell {EMPTY, DECK, FIRED, DESTROYED, NEIGHBORED};
    private Ship hShip;
    private StateCell stateCell;
    public SeaCell () {
        stateCell = StateCell.EMPTY;
    }
    public boolean isEmpty(){return (stateCell==StateCell.EMPTY)?true:false;}

    public boolean isNeighbored(){return (stateCell==StateCell.NEIGHBORED)?true:false;}

    public boolean isDeck(){return (stateCell==StateCell.DECK)?true:false;}

    public StateCell getCellState(){
        return stateCell;
    }

    public void setCellNeighbored() {
        stateCell = StateCell.NEIGHBORED;
    }
    public void setCellAsDeck(Ship hhShip) {
        hShip = hhShip;
        stateCell =StateCell.DECK;
    }
}
