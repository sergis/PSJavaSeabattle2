package ru.sergeykravchenko.seabattle.gameseabattle;
import ru.sergeykravchenko.seabattle.player.Player;
/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h4>Java 1 Lesson 3 Домашнее задание/курсовая работа</h4>
 * <p>Класс реализующий правила игры  Морской бой. контроллер модели (MVC)</p>
 * <p>предназначен для реализации всех методов и свойств связанных со реализацией правил собственно игры /p>
 * <p>все настройки д.б. вынесены в класс управления настройками игры, здесь - только реализация уже заданных параметров    </p>
 * <p>   </p>
 * <p>методы : </p> <ul>
 * <li>конструктор: запоминает играющих, инициализирует игровое поле, рейд с кораблями, устанавливает статус "настройка" </li>
 * <li>TODO: setGameMode(): вызывается из контроллера настройки, устанавливает параметры игры по-умолчанию(см.описание метода)</li>
 * <li>TODO: resetSeaField(): создает игровое поле заданного размера</li>
 * <li>TODO: setSeaField(): реализует расстановкукораблей</li>
 * <li>TODO: playGame(): реализует исполнение одного хода и определяет кто ходит следующим</li>
 * <li>TODO: stopGame(): останавливает игру, (прерывает по команде игрока или по завершении ходов).полезен для переигровки и тп </li>
 * <li>TODO: quitGame(): прекращает игру</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see    Player, ComputerPlayer, TestSeaBattle,
 * TODO: классы Ship, Cell
 *
 */
public class GameSeaBattle {
	private Player[] hhPlayers;
    private short seaSize ;
    private SeaField hSeaField;
    //private Ship[] hShip;
    private Short[] flotShipDecks;
    private short nextStepPlayer; //
	public GameSeaBattle(Player hPlayer1, Player hPlayer2){
	    if ((hPlayer1!=null)&&(hPlayer2!=null)) 	{
		    hhPlayers = new Player[2];
            hhPlayers[0] = hPlayer1;
		    hhPlayers[1] = hPlayer2;
            nextStepPlayer = 0;
            seaSize = 10; // Default SeaSize
            flotShipDecks = new Short[10];
            flotShipDecks[0]=4;  // 4 decks ship = 1
            flotShipDecks[1]=3; flotShipDecks[2]=3;  // 3 decks ship = 2
            flotShipDecks[3]=2; flotShipDecks[4]=2;flotShipDecks[5]=2; // 2 decks ship = 3
            flotShipDecks[6]=1;flotShipDecks[7]=1; flotShipDecks[8]=1;flotShipDecks[9]=1;// 1 decks ship = 4
		    System.out.println ("Game SeaBattle Controller started with 2 players");
	    }else {
            System.out.println("Game SeaBattle Controller aborted, invalid Players");
        }
	}
    //* resetSeaField(size)  пересоздает игровое поле заданного размера. старое поле - в мусор
    public SeaField resetSeaField (short size) {
       short i;
       //  see SeaField.seaSize=size;
       for (i=0; i<hhPlayers.length; i++) {
           hhPlayers[i].setPlayerSea(new SeaField(size)); // if any Sea fields exist add job for GC, no saving yet
        System.out.println ("SeaField reset. size: "+size);
       }
       return (hSeaField);
    }

    public void playGame (Player hPlayer1, Player hPlayer2) {
	    System.out.println ("Play SeaBattle step done ");
	}
	public void stopGame(Player hPlayer1, Player hPlayer2) {
		System.out.println ("STOP SeaBattle step done ");
	}
	public void quitGame(Player hPlayer1, Player hPlayer2) {
		System.out.println ("QUIT SeaBattle Game done ");
	}

}
//:~