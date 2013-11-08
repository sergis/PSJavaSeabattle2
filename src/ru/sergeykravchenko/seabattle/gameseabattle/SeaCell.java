package ru.sergeykravchenko.seabattle.gameseabattle;
/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 курсовая работа. класс ячейки поля игры морской бой/h5>
 * <p>Класс реализует создание и методы работы ячейки игрового полем игры  Морской бой </p>
 * <p>создает одну ячейку поля реализует ее состояния (свободна, занята палубой корабля, палуба разрушена, был выстрел)  и выдачу данных для отображения /p>
 * <p></p>
 * <p> MVC: класс контроллера модели игры</p>
 * <p>методы : </p> <dl>
 * <dt>конструктор:<dt><dd> создает ячейку со статусом <code>EMPTY</code>  (пустая) </dd>
 * <dt><code>getCellState()</code> :<dt><dd> возвращает статус ячейки </dd>
 * <dt><code>setCellState()</code> :<dt><dd> устанавливает указанный статус ячейки </dd>
 * <dt><code>setCellAsDeck(Ship hhShip)</code> <dt><dd> запоминает ячейку как палубу указанного корабля</dd>
 * <dt><code>setCellNeighbored()</code> <dt><dd> устанавливает ячейку как соседнюю с каким то кораблем </dd>
 * <dt><code>fireCell()</code> <dt><dd> обработка выстрела по ячейке @return true если попадание по палубе корабля  </dd>
 * <dt><code>isNeighbored()</code><dt><dd>  проверяет есть ли корабль по соседству с ячейкой  </dd>
 * <dt><code>isEmpty()</code> <dt><dd> проверяет пуста ли ячейка  </dd>
 * <dt><code>isDeck()</code> <dt><dd> проверяет является ли ячейка палубой корабля  </dd>
 * <dl>
 * @author Sergey Kravchenko
 * @version 1.0
 * @see  Ship, SeaField, Player, ComputerPlayer, GameSeaBattle
  *
 */
public class SeaCell {

    public enum StateCell {EMPTY, DECK, FIRED, DESTROYED, NEIGHBORED};
    private Ship hShip;
    private StateCell stateCell;

    public SeaCell () { stateCell = StateCell.EMPTY; }

    public void setCellState(StateCell cellState) {this.stateCell = cellState;}

    public void setFired() {stateCell=StateCell.FIRED;}

    public void setDestroyed() {stateCell=StateCell.DESTROYED;}

    public boolean isEmpty(){return (stateCell==StateCell.EMPTY);}

    public boolean isNeighbored(){return (stateCell==StateCell.NEIGHBORED);}

    public boolean isDeck(){return (stateCell==StateCell.DECK);}

    public boolean isDestroyed() {return stateCell==StateCell.DESTROYED;}

    public StateCell getCellState(){ return stateCell; }

    public void setCellNeighbored() {stateCell = StateCell.NEIGHBORED;}

    public void setCellAsDeck(Ship hhShip) {
        hShip = hhShip;
        stateCell =StateCell.DECK;
    }
    // выстрел по ячейке поля противником
    // @ return boolean попал или нет
    public boolean fireCell() {
        if (stateCell == StateCell.DECK) {
            stateCell = StateCell.DESTROYED;
            hShip.fireBoom();
            return true;
        }
        else stateCell = StateCell.FIRED;
        return false;  //To change body of created methods use File | Settings | File Templates.
    }
}
