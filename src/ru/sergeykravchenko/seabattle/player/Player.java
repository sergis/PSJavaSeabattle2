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
 * <li> tunePlayer(): позволяет проверить и установить настройки игры задаваемые игроком </li>
 * <li> TODO: ___():</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  ,
 *? Player, ComputerPlayer, TestSeaBattle,
  *
 */
public class Player {
    public static final byte PLAYER_QUIT = 0, PLAYER_STOP = 1,PLAYER_WAIT = 2, PLAYER_START = 3,
             PLAYER_SETSHIP = 4, PLAYER_SETFIELD = 5,
             PLAYER_SETNAME = 6;
    protected byte cmdPlayer = PLAYER_WAIT;
    protected UIController hPlayerUI;
    public String playerName;
    SeaField playerSea;
    protected GameSeaBattle hGame;
    protected boolean isObserver;

    public Player(UIController hInstance) {
        hGame = null;
        playerName = "Player-";
        hPlayerUI = hInstance;
        isObserver = false;
        System.out.println ("Game Player Controller started:"+playerName);
    }

    public	void tunePlayer(){
        System.out.println (playerName+":Tuner cmd "+ cmdPlayer);
        switch (cmdPlayer){
            case PLAYER_WAIT:

                break;
            case PLAYER_START:
                break;
            case PLAYER_SETSHIP:


                break;
            case PLAYER_SETFIELD:

                break;
            case PLAYER_SETNAME :
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
    boolean isObserver() {
        return isObserver;
        }
    /*
    * Player#getPlayerName()
    * @return playerName
    */
    public String getPlayerName() {
        return playerName;}


}
