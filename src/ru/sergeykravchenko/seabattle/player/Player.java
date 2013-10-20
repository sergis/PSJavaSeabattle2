package ru.sergeykravchenko.seabattle.player;

import ru.sergeykravchenko.seabattle.gameseabattle.GameSeaBattle;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaField;
import ru.sergeykravchenko.seabattle.uicontroller.UIController;

/*
 *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 4 Домашнее задание/курсовая работа. класс Player для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Игрока в игре  Морской бой </p>
 * <p>/p>
 * <p></p>
 * <p> MVC: класс контроллера интерфейса  игры</p>
 * <p>методы : </p> <ul>
 * <li>конструктор: инициализирует данные об игроке, задает имя, статус активности (наблюдатель или нет)
 * <li> tunePlayer(): позволяет проверить и установить настройки игры задаваемые игроком, в т.ч. расстановку кораблей </li>
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
                              SETNAME               //в сопутствующем массиве параметров
    }
    protected PlayerMode cmdPlayer = PlayerMode.WAIT;
    protected UIController hPlayerUI;
    protected String playerName;
    protected short playerSeaSize = 10;
    protected SeaField playerSea;
    protected String [] coordinateNameSea; // массив обозначений координат игрового поля 

    protected GameSeaBattle hGame;
    protected boolean isObserver;
    //
    public Player(UIController hInstance) {
        hGame = null;
        playerName = "Player-";
        hPlayerUI = hInstance;
        isObserver = false; //  =true для игрока-компьютера и зарезервировано для игроков-зрителей
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
                playerSea= new SeaField(playerSeaSize);
                coordinateNameSea = new String[2];
                coordinateNameSea[1] ="ABCDEFGHIJKLMNOPQRSTVWXYZ0123456789";
                coordinateNameSea[2] ="0123456789ABCDEFGHIJKLMNOPQRSTVWXYZ";

                break;
            case SETSHIP:


                break;

            case SETFIELD:
                if ( playerSeaSize<=5) {
                    System.out.println("Game Player Sea Size=" + playerSeaSize + " invalid, please set again :");
                playerSeaSize=10;
                }
                break;
            case SETNAME:
                if (hPlayerUI!=null) {
                    playerName += "Human";
                }

                break;
            default:
        }

        System.out.println (playerName+":Tuner DONE. next cmd "+ cmdPlayer);
        }

    public void setPlayerSea (SeaField playerSea) {
        this.playerSea = playerSea;
        }
    public String[] getCoordNameSea () {
        return this.coordinateNameSea;
    }
    public SeaField getPlayerSea () {
        return this.playerSea;
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


    public short getPlayerSeaSize() {
        return playerSeaSize;
    }
}
