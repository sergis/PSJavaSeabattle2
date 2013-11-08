package ru.sergeykravchenko.seabattle.gameseabattle;
import ru.sergeykravchenko.seabattle.player.Player;
/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h4>Java 1 курсовая работа</h4>
 * <p>Класс реализующий правила игры  Морской бой. контроллер модели (MVC)</p>
 * <p>предназначен для реализации всех методов и свойств связанных с реализацией правил игры /p>
 * <p>все настройки д.б. вынесены в класс управления настройками игры, здесь - только реализация уже заданных параметров</p>
 * <p>   </p>
 * <p>методы : </p> <dl>
 * <dt>конструктор:</dt><dd> запоминает играющих, инициализирует игровое поле, рейд с кораблями, устанавливает статус "настройка" </dd>
 * <dt><code>fireTarget()</code> </dt><dd>выполняет выстрел Игрока по полю противника </dd>
 * <dt><code>getNavyShipDecks()</code></dt><dd>  // @return Short[] массив распределения палуб на кораблях флота</dd>
 * <dt>TODO: <code>playGame()</code>:</dt><dd> реализует исполнение одного хода и определяет кто ходит следующим</dd>
 * <dt>TODO: <code>stopGame()</code>:</dt><dd> останавливает игру, (прерывает по команде игрока или по завершении ходов).полезен для переигровки и тп </dd>
 * <dt>TODO: <code>quitGame()</code>:</dt><dd> прекращает игру</dd>
 * </dl>
 * @author Sergey Kravchenko
 * @version 1.0
 * @see    Player, ComputerPlayer
 */
public class GameSeaBattle {

	private Player[] hhPlayers;
    private short seaSize ;
    private SeaField hSeaField;
    //private Ship[] hNavy;
    private Short[] navyShipDecks;
    private short nextStepPlayer ;
 public GameSeaBattle(Player hPlayer1, Player hPlayer2){
	if ((hPlayer1!=null)&&(hPlayer2!=null)) 	{
		    hhPlayers = new Player[2];
            hhPlayers[0] = hPlayer1;
		    hhPlayers[1] = hPlayer2;
            hPlayer1.sethGame(this);
            hPlayer2.sethGame(this);
            hPlayer1.setMyTurn(true);
            nextStepPlayer = 0;  // индекс игрока для следующего хода
            seaSize = 10;        // Default SeaSize
            navyShipDecks = new Short[10];
            navyShipDecks[0]=4;  // 4 decks ship = 1
            navyShipDecks[1]=3; navyShipDecks[2]=3;  // 3 decks ship = 2
            navyShipDecks[3]=2; navyShipDecks[4]=2;
            navyShipDecks[5]=2; // 2 decks ship = 3
            navyShipDecks[6]=1;
            navyShipDecks[7]=1; navyShipDecks[8]=1;
            navyShipDecks[9]=1;// 1 decks ship = 4
	    }else {
            System.out.println("Game SeaBattle Controller aborted, invalid Players");
    }
 }

 // Player firing to target
 // @params fired Player, target coordinates x and y
 // @return boolean true if fire was shot or false if missing
 public boolean fireTarget(Player hPlayer, int xTarget, int yTarget) {
    boolean fireShot = false;
    Player targetPlayer;
    if ((hhPlayers!=null)&&(hPlayer!=null)) {
         if (hPlayer==hhPlayers[0]) targetPlayer =hhPlayers[1];
            else targetPlayer = hhPlayers[0];

       fireShot = targetPlayer.fireBoom(xTarget, yTarget);
       if (fireShot)   // mark player's own target map
            hPlayer.getTargetSea().getCell(xTarget,yTarget).setDestroyed();
       else hPlayer.getTargetSea().getCell(xTarget,yTarget).setFired();
     }
    return fireShot;
 }
 //
 public void playGame (Player hPlayer1, Player hPlayer2) {
	    System.out.println ("Play SeaBattle step done ");
 }
 public void stopGame(Player hPlayer1, Player hPlayer2) {
		System.out.println ("STOP SeaBattle step done ");
 }
 public void quitGame(Player hPlayer1, Player hPlayer2) {
		System.out.println ("QUIT SeaBattle Game done ");
 }

 // @return Short[] array of navy ship decks arrangement
 public Short[] getNavyShipDecks(){
     return navyShipDecks;
 }

}
//:~