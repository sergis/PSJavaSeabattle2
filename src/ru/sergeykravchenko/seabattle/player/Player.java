package ru.sergeykravchenko.seabattle.player;

import ru.sergeykravchenko.seabattle.gameseabattle.GameSeaBattle;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaField;
import ru.sergeykravchenko.seabattle.gameseabattle.Ship;
import ru.sergeykravchenko.seabattle.uicontroller.UIController;

import java.util.ArrayList;
import java.util.Random;

/*
 *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 4 Домашнее задание/курсовая работа. класс Player для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Игрока в игре  Морской бой </p>
 * <p>/p>
 * <p></p>
 * <p> MVC: класс контроллера интерфейса  игры</p>
 * <p>методы : </p> <ul>
 * <li>конструктор: инициализирует данные об игроке, задает имя, статус активности (наблюдатель или нет)
 * <li> tunePlayer(): проверяет и установливает настройки игры задаваемые Игроком, в т.ч. расстановку кораблей </li>
 * <li> TODO: ___():</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  ,
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */
public class Player {

    public enum PlayerMode {WAIT, START, QUIT, STOP,// Команды/состояния пользовательского интерфейса игрока
                            SETSHIP, SETFIELD,      //  параметры команд будут передаваться
                            SETNAME                 //в сопутствующем массиве параметров
    }
    protected PlayerMode cmdPlayer = PlayerMode.WAIT;
    protected UIController hPlayerUI;
    protected String playerName;
    protected short playerSeaSize = 10;
    protected SeaField[] playerSea = new SeaField[2];
    protected String [] coordinateNameSea; // массив обозначений координат игрового поля 

    protected GameSeaBattle hGame;
    protected ArrayList<Ship> hNavy;  // флот к бою
    protected boolean isObserver;
    //
    public Player(UIController hInstance) {
        hGame = null;                 // контроллер игры для Игрока
        playerName = "Player-";       // Имя Игрока
        hPlayerUI = hInstance;        // контроллер интерфейса Игрока
        isObserver = false; // запрет на ввод команд игры =true  для игрока-компьютера и зарезервировано для игроков-зрителей
        coordinateNameSea = new String[2]; // формируем поле координат по умолчанию
        coordinateNameSea[0] ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        coordinateNameSea[1] ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        //     0123456789012345678901234567890123456789

        playerSea[0]= new SeaField(playerSeaSize); // Player Sea Field always exists
        playerSea[1]= new SeaField(playerSeaSize); // Target Sea Field always exists

        System.out.println ("Game Player Controller started:"+playerName);
    }
    //
    public	void tunePlayer(){
        System.out.println (playerName+":Tuner cmd "+ cmdPlayer);
        switch (cmdPlayer){
            case WAIT: // no command detected
                 break;
            case START: // all tunes done, start the game
                if ( playerSeaSize<=5) {
                    System.out.println ("Game Player Sea Size invalid, reset to default =10 :"+playerName);
                    playerSeaSize=10;
                }
                if (hNavy==null)
                    for (short decks:hGame.getNavyShipDecks()){
                        hNavy.add(new Ship(decks));
                    }
                /*   set ships automatically  */
                placeShipsAutomatically(playerSea[0]);
                break;
            case SETSHIP:
                  if (hGame!=null){
                    if (hNavy==null)
                       for (short decks:hGame.getNavyShipDecks()){
                           hNavy.add(new Ship(decks));
                       }
                    /* Set Ship as of Player command  */
                        
                  }
                 break;
            case SETFIELD:
                if (( playerSeaSize<=5)&&(playerSeaSize>35)) {
                    System.out.println("Game Player Sea Size=" + playerSeaSize + " invalid, please set again :");
                playerSeaSize=10;
                } else {
                   playerSea[0]= new SeaField(playerSeaSize);  // new Player Sea Field
                   playerSea[1]= new SeaField(playerSeaSize);  // new Target Sea Field
                }
                break;
            case SETNAME:
                if (hPlayerUI!=null) {
                    playerName += "Human";
                }

                break;
            default: System.out.println (playerName+":Unknown Tuner command "+ cmdPlayer);
        }

        System.out.println (playerName+":Tuner DONE. next cmd "+ cmdPlayer);
    }

  /*  public void setPlayerSea (SeaField playerSea) {
        this.playerSea[0] = playerSea;
        }   */
    protected void placeShipsAutomatically(SeaField playerTheater){
        Random rndGen = new Random ();
        // variant 1 random placement ship by ship
        for (Ship hShip:hNavy) {
            if ((hShip!=null)&&(playerTheater!=null)) {
                for (int i = playerSeaSize * playerSeaSize; i > 0; i--) {
                    int rnd = rndGen.nextInt(playerSeaSize * playerSeaSize - 1);
                    int x = (rnd % playerSeaSize);
                    int y = (short) (rnd / playerSeaSize);
                    if (playerTheater.placeShip(hShip,x,y,rndGen.nextBoolean()) )
                    {
                        break;
                    }
                    ;

                }
            }
        }
    }

    public String[] getCoordNameSea () {
        return this.coordinateNameSea;
    }
    public SeaField getPlayerSea () {
        return this.playerSea[0];
    }
    public SeaField getTargetSea () {
        return this.playerSea[1];
    }
    boolean isObserver() {
        return isObserver;
        }
    /*
    * Player#getPlayerName()
    * @return playerName
    */
    public String getPlayerName() {
        return playerName;}
//
    public short getPlayerSeaSize() {
        return playerSeaSize;
    }
}
