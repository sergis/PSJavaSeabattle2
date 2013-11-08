package ru.sergeykravchenko.seabattle.gameseabattle;
/*
 * Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 курсовая работа. класс Корабль для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Корабль игры  Морской бой </p>
 * <p>создает один Корабль с заданным количеством палуб,   реализует его состояния (на рейде, в походе),
 * курс (ориентация на поле), координаты занятых ячеек игрового поля, состояния палуб (боевая или разрушена)  /p>
 * <p></p>
 * <p> MVC: класс контроллера модели игры</p>
 * <p>методы : </p> <dl>
 * <dt>конструктор: </dt>
 * <dd>инициализирует данные о корабле, в походе или на рейде, статус палуб </dd>
 * <dt> <code>putDeckOnField(int deck, int x, int y,SeaCell cell)</code>: </dt>
 * <dd>устанавливает палубу корабля на поле игры !все проверки д.б. сделаны вне метода</dd>
 * <dt><code>getDecksOnShip()</code> </dt>
 * <dd> @return возвращает кол-во палуб на корабле</dd>
 * <dt><code>setShipPlace()</code> </dt>
 * <dd> устанавливает координаты и ориентацию корабля</dd>
 * <dt><code>fireBoom()</code> </dt>
 * <dd>обработка выстрела противника по кораблю, @return boolean жив корабль или нет по кораблю или нет</dd>
 * <dt><code>isInBattle()</code> </dt>
 * <dd>@return boolean участвует ли корабль в бою</dd>
 * <dt><code>isVertical()</code> </dt>
 * <dd>@return boolean расположен ли корабль на поле вертикально</dd>
 * <dt><code>isInBattle()</code> </dt>
 * <dd>@return boolean участвует ли корабль в бою</dd>
 * <dt><code>setInBattle()</code> </dt>
 * <dd> устанавливает свойство boolean = участвует ли корабль в бою</dd>
 * <dt><code>getxCoordinate()</code> </dt>
 * <dd> @return получение координаты x корабля</dd>
 * <dt><code>getyCoordinate()</code> </dt>
 * <dd> @return получение координаты y корабля</dd>
 * <dl>
 * @author Sergey Kravchenko
 * @version 1.0
 * @see  SeaCell, SeaField, Player, ComputerPlayer
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
    //
    public void putDeckOnField(int deckN, SeaCell cell) {
        cell.setCellAsDeck(this);
        deckPosition[deckN] = cell;
    }
    // устанавливает свойство boolean = участвует ли корабль в бою
    public void setInBattle(boolean newSetInBattle){
        shipInBattle=newSetInBattle;
    }
    // @return получение координаты x корабля
    public int getxCoordinate() { return xCoordinate; }
    // @return получение координаты y корабля
    public int getyCoordinate() { return yCoordinate; }
    //
    public boolean isVertical() { return vertical;}
    //
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
    // @return boolean участвует ли корабль в бою
    public boolean isInBattle(){ return shipInBattle; }
}
//:~ end of class
