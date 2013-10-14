package ru.sergeykravchenko.seabattle;
/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h3>Java 1 Lesson 3 Домашнее задание/курсовая работа</h3>
 * <p>Программа организует исполнение игры Морской бой (консольный вариант) </p>.
 * <p>Цель: 0 версия игры Морской бой</p>
 * <p> метод SeaBattleLauncher.main() реализует  управление игрой и MVC патерн,<br>
 *  выделены классы для/p>
 *  <ul>
 *   <li>тестов игры: TestSeaBattle</li>
 *   <li>UI и Настройки Игры: UIController,</li>
 *   <li>класс Игрока и Комппьютера(наследует Игрока) : Player, ComputerPlayer
 *   (в следующих версиях можно будет реализовать игру по сети) </li>
 *   <li>Класс реализующий игру:  GameSeaBattle</li>
 *   <li>Класс базы данных Игр: GameDB</li>
 *   </ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see    UIController, Player, ComputerPlayer, GameSeaBattle
 *
 */

import ru.sergeykravchenko.seabattle.player.ComputerPlayer;
import ru.sergeykravchenko.seabattle.gameseabattle.GameSeaBattle;
import ru.sergeykravchenko.seabattle.player.Player;
import ru.sergeykravchenko.seabattle.uicontroller.UIController;

public class SeaBattleLauncher {

	public static void main(String[] args) {

		boolean modeTesting = true;
		UIController hInstance = null;   // Front OS/UI Controller
		Player hPlayer, hComputer;  // Player model Controllers
		GameSeaBattle hSeaBattle = null;   // Game model Controller
	
		
		hInstance = new UIController(modeTesting);
		hPlayer   = new Player(hInstance);
		hComputer = new ComputerPlayer(hInstance);
		short currentCommand = UIController.CMD_TUNER;		
		do { 
				switch (currentCommand) {
				case UIController.CMD_TUNER:
					   hPlayer.tunePlayer();
					break;
				case UIController.CMD_START:
					if (hSeaBattle == null)
					   hSeaBattle = new GameSeaBattle(hPlayer,hComputer); 
					break;
				case UIController.CMD_PLAY:
					if (hSeaBattle != null) {
						hSeaBattle.playGame(hPlayer,hComputer);	
					};
		
					break;	
				case UIController.CMD_STOP:
					if (hSeaBattle != null) {
						hSeaBattle.stopGame(hPlayer,hComputer);
					}
                    break; 
				case UIController.CMD_QUIT:
					if (hSeaBattle != null) {
						hSeaBattle.quitGame(hPlayer,hComputer);
					}
                	break;
					
				default: 
					System.out.println("Unknown command, please repeat");
				};
			hInstance.renderView(hPlayer);   // show UI for Player
			hInstance.renderView(hComputer); // show Computer's display to observer if any
			currentCommand = hInstance.getInstanceMode();
		}		
		while (currentCommand!=UIController.CMD_QUIT);
		
		}
	
}
;

/**/


