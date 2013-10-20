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
 * <li>TODO: setShip(): устанавливает корабль на поле игры </li>
 * <li>TODO: shotCell(): принимает выстрел создает игровое поле заданного размера</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  Ship, SeaField
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */
public class Ship {
    public enum DeckState {GOOD,DESTROYED};
    private boolean shipInBattle;
    private short decksOnShip;
    private DeckState[] deck;
    private SeaCell[] deckPosition;

    public Ship(short decks){
        short i;
        shipInBattle = false; // Корабль на рейде, команда в отпуске
        decksOnShip = decks;
        this.deck = new DeckState[decks];
        deckPosition= new SeaCell[decks];
        for (i=0; i<deck.length; i++){
            deck[i]=DeckState.GOOD;
            deckPosition[i] = null;
        }
    }
    public int getDecksOnShip() {
        return decksOnShip;
    }
    //public setShipPosition()
}
