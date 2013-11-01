package ru.sergeykravchenko.seabattle.gameseabattle;

/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 3 Домашнее задание/курсовая работа. класс Корабль для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Корабль игры  Морской бой </p>
 * <p>создает один Корабль с заданным количеством палуб,   реализует его состояния (на рейде, в походе),
 * курс (ориентация на поле), координаты занятых ячеек игрового поля, состояния палуб (боевая или разрушена)  /p>
 * <p></p>
 * <p> MVC: класс контроллера модели игры</p>
 * <p>методы : </p> <ul>
 * <li>конструктор: инициализирует данные о корабле, в походе или на рейде, статус палуб </li>
 * <li> <code>putDeckOnField(int deck, int x, int y,SeaCell cell)</code>: устанавливает палубу корабля на поле игры
  * все проверки д.б. сделаны вне метода</li>
 * <li><code>getDecksOnShip() </code> возвращает кол-во палуб на корабле</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  Ship, SeaField
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */
public class Ship {
    private boolean shipInBattle;
    private int decksOnShip;
    private SeaCell[] deckPosition;
    private int xCoordinate,yCoordinate;
    private boolean vertical;
    public Ship(int decks){
        shipInBattle = false; // Корабль на рейде, команда в отпуске
        decksOnShip = decks;
        deckPosition= new SeaCell[decks] ;
        xCoordinate=-1; yCoordinate=-1; // coordinates out of field
      }
    public int getDecksOnShip() {
        return decksOnShip;
    }
    public void putDeckOnField(int deckN, SeaCell cell) {
        cell.setCellAsDeck(this);
        deckPosition[deckN] = cell;
    }

    public void setInBattle(boolean newSetInBattle){
        shipInBattle=newSetInBattle;
    }
    public int getxCoordinate() { return xCoordinate; }
    public int getyCoordinate() { return yCoordinate; }
    public boolean isVertical() { return vertical;}
    public void setShipPlace(int xCoordinate, int yCoordinate, boolean straight) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.vertical = straight;
    }
    // обработка выстрела противника по кораблю
    // @return boolean жив корабль или нет по кораблю или нет
    public boolean fireBoom() {

        for (SeaCell shipDeck:deckPosition)
            if (shipDeck.getCellState()== SeaCell.StateCell.DECK) return true;
        // нет живых палуб
        shipInBattle =false;
        return shipInBattle;
    }
    public boolean isInBattle(){ return shipInBattle; }
}
//:~ end of class
