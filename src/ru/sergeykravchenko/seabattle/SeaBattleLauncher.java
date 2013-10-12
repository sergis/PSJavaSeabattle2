package ru.sergeykravchenko.seabattle;
/* 
 * *   Java 1 Course work (Eclipse)
 * <h3>Java 1 Lesson 3 Домашнее задание/курсовая работа</h3>
 * <p>Программа организует исполнение игры Морской бой (консольный вариант) </p>.
 * <p>Цель: 0 версия игры Морской бой</p>
 *
 * 
 * @author Sergey Kravchenko
 * @version 0.0 
 * @see 
 *
 */ 


public class SeaBattleLauncher {
	public static final short
		CMD_QUIT = 0, 		CMD_OVER = 1,	
		CMD_START= 2, 		CMD_STEP = 3;	
	public static void main(String[] args) {

		boolean modeTesting = true;
		StartInstance hInstance =null;   // Front OS/UI Controller
		Player hPlayer, hComputer;  // Player model Controllers
		GameSeaBattle hSeaBattle=null;   // Game model Controller
		
		
		hInstance = new StartInstance(modeTesting);
		hPlayer   = new Player(hInstance);
		hComputer = new Player(hInstance);
		short currentCommand = CMD_START;		
		do { 
				switch (currentCommand) {
				case CMD_START:
					if (hSeaBattle!=null)
					   hSeaBattle = new GameSeaBattle(hPlayer,hComputer); 
					break;
				case CMD_STEP:
					
					break;	
				case CMD_OVER:
					currentCommand = CMD_QUIT; 
				case CMD_QUIT: 
			
					break;
					
				default: 
					System.out.println("Unknown command, please repeat");
				};
			hInstance.renderView(currentCommand);	
			currentCommand = hInstance.getCommand();
		}		
		while (currentCommand!=CMD_QUIT);
		
		}
	
}
//:~

class Player {
	private string playerName;
	Player (StartInstance hInstance){
		
		 playerName = "Player 1 Human";
		 System.out.println ("Game Player Controller started");	
	}
	
};

class GameSeaBattle {
	GameSeaBattle (Player hPlayer1,Player hPlayer2){
	if ((hPlayer1!=null)&&(hPlayer2!=null)) 	{
	 System.out.println ("Game SeaBattle Controller started");
	}else 
		 System.out.println ("Game SeaBattle Controller aborted, invalid Players");
	}
};

/* Класс инициализации приложения и управления коммуникацией с клиентом */
class StartInstance 	{
	private short ourInstance,
				nextCmd;
	private boolean modeTesting;
	TestSeaBattle hTestSeaBattle;
	
	StartInstance (boolean modeTest){
		this.ourInstance = 0;
		this.modeTesting = modeTest;
		if (modeTesting) hTestSeaBattle = new TestSeaBattle();
		//boolean mode;
		System.out.println ("Instance started");
	 }
	 short getCommand(){
		if (modeTesting){
			nextCmd=hTestSeaBattle.getTestCmd();
		}
		else nextCmd=SeaBattleLauncher.CMD_QUIT;
		System.out.println ("Get command queue received:"+nextCmd);
		return (nextCmd);
	 }
	 void renderView(short viewPointer){
		 final String[] VIEW =
		         {"VIEW QUIT", "VIEW OVER",
				  "VIEW_START", "VIEW_STEP"};
		 
		 switch (viewPointer){
		    case SeaBattleLauncher.CMD_STEP: 
		    case SeaBattleLauncher.CMD_START:
			case SeaBattleLauncher.CMD_OVER:
			case SeaBattleLauncher.CMD_QUIT: 
				System.out.println(VIEW[viewPointer]);
				break;
				
			default: 
				System.out.println("Undefined view, please define VIEW switch");
		 }
		 
		 System.out.println ("Game View render");
	 }
	 
	
			/**/
}
/**/

class TestSeaBattle {
	private static final short[] testCmdQueue =
	    {SeaBattleLauncher.CMD_START,
		SeaBattleLauncher.CMD_STEP, SeaBattleLauncher.CMD_STEP, 
		SeaBattleLauncher.CMD_OVER, SeaBattleLauncher.CMD_QUIT};
	short testCmdNext;	
	
	TestSeaBattle () {
		testCmdNext = testCmdQueue[0]; 
		System.out.println ("Test Mode started#"+testCmdNext);
	}
	short getTestCmd(){
		short nextCmd;
		
		if (testCmdNext<testCmdQueue.length) 
		  {nextCmd = testCmdQueue[testCmdNext];
			testCmdNext++;
		}else nextCmd = SeaBattleLauncher.CMD_QUIT; //
		System.out.println ("Test Send Command:#"+nextCmd);
		return (nextCmd);
	}
};


