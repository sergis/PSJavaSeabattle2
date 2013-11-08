package ru.sergeykravchenko.seabattle.player;

import ru.sergeykravchenko.seabattle.consoleapp.UIController;
import ru.sergeykravchenko.seabattle.gameseabattle.GameSeaBattle;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaField;
import ru.sergeykravchenko.seabattle.gameseabattle.Ship;

import java.util.ArrayList;
import java.util.Random;

/*
 *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 курсовая работа. класс Player для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Игрока в игре  Морской бой </p>
 * <p> MVC: класс модели интерфейса  игрока</p>
 * <p>методы : </p> <dl>
 * <dt>конструктор: </dt>
 * <dd>инициализирует данные об игроке, задает имя, статус активности (наблюдатель или нет)</dd>
 * <dt> <code>tunePlayer()</code>:</dt>
 * <dd> проверяет и установливает настройки игры задаваемые Игроком, в т.ч. расстановку кораблей </dd>
 * <dt> <code>fireBoom()</code>:</dt>
 * <dd> рeализует выстрел противника по полю игрока</dd>
 * <dt> <code>placePlayerNavy()</code>:</dt>
 * <dd> размещает случайным образом корабли на поле данного игрока</dd>
 * <dt> <code>place2ndPlayerNavy()</code>:</dt>
 * <dd> размещает случайным образом корабли на поле противника у данного игрока игрока</dd>
 * <dt> <code>placeShipsAutomatically(SeaField playerTheater)<code></dt>
 * <dd>размещает корабли случайным образом на указанном поле</dd>
 * </dl>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  ComputerPlayer, GameSeaBattle, SeaField, SeaCell
  *
 */
public class Player {

    private boolean defeated;  // = true if GAME OVER and the player LOST


    // Команды/состояния пользовательского интерфейса игрока
    public enum PlayerMode {WAIT, START, QUIT, STOP,//  параметры команд будут передаваться
                            SETSHIP, SETFIELD,      //в сопутствующем массиве параметров
                            SETNAME
    }
    protected boolean myTurn;
    //protected ArrayList<SeaField.Coords> turnsLog ;
    protected PlayerMode cmdPlayer = PlayerMode.WAIT;
    protected UIController hPlayerUI;
    protected String playerName;
    protected short playerSeaSize = 10;
    protected SeaField[] playerSea = new SeaField[2];
    protected String [] coordinateNameSea; // массив обозначений координат игрового поля 

    protected GameSeaBattle hGame;
    protected ArrayList<Ship> hNavy = new ArrayList<Ship>();  // флот к бою
    protected boolean isObserver;
    //
    public Player(UIController hInstance) {
        hGame = null;                 // контроллер игры для Игрока (вне игры);
        playerName = "Player-";       // Имя Игрока
        hPlayerUI = hInstance;        // контроллер интерфейса Игрока
        isObserver = false; // запрет на ввод команд игры =true  для игрока-компьютера и зарезервировано для игроков-зрителей
        coordinateNameSea = new String[2]; // формируем поле координат по умолчанию
        coordinateNameSea[0] ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        coordinateNameSea[1] ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        //     0123456789012345678901234567890123456789

        playerSea[0]= new SeaField(playerSeaSize); // Player Sea Field always exists
        playerSea[1]= new SeaField(playerSeaSize); // Target Sea Field always exists
        myTurn = false;
       // turnsLog = new ArrayList<SeaField.Coords> (playerSeaSize*playerSeaSize); //журнал "Ходы противника"
        defeated = false;
    }
    //  метод для реализации настроек, задаваемых игроком
    // TODO: переработать, м.б. разбить на отдельные методы настроек, вызываемые из интерфейс-контроллера
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
                if (hNavy.isEmpty())
                    for (short decks:hGame.getNavyShipDecks()){
                        hNavy.add(new Ship(decks));
                    }
                /*   set ships automatically  */
                placeShipsAutomatically(playerSea[0]);
                break;
            case SETSHIP:
                  if (hGame!=null){
                    if (hNavy.isEmpty())
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
// размещает случайным образом корабли на поле игрока
// @return boolean если все корабли размещены на поле
  public boolean placePlayerNavy(){
      if (hGame!=null){
          if (hNavy.isEmpty())
              for (short decks:hGame.getNavyShipDecks()){
                  hNavy.add(new Ship(decks));
              }
          return placeShipsAutomatically(playerSea[0]);
      } else return false;
  }
  //  размещает случайным образом корабли на поле противника игрока
  // @return boolean если все корабли размещены на поле
  // TODO:  УБРАТЬ, сделана  только для тестирования вывода на поле
  public boolean place2ndPlayerNavy(){
      if (hGame!=null){
          if (hNavy.isEmpty())
                for (short decks:hGame.getNavyShipDecks()){
                    hNavy.add(new Ship(decks));
          }
        return placeShipsAutomatically(playerSea[1]);
      } else return false;
  }
// размещает корабли случайным образом на указанном поле
// @return boolean если все корабли размещены на поле
  protected boolean placeShipsAutomatically(SeaField playerTheater){
        boolean placed = true;
        Random rndGen = new Random ();
        // variant 1 random placement ship by ship
        for (Ship hShip:hNavy) {
            if ((hShip!=null)&&(playerTheater!=null)) {
                for (int i = playerSeaSize * playerSeaSize; i > 0; i--) {
                    int rnd = rndGen.nextInt(playerSeaSize * playerSeaSize - 1);
                    int x = (rnd % playerSeaSize);
                    int y = (rnd / playerSeaSize);
                    if (playerTheater.placeShip(hShip,x,y,rndGen.nextBoolean()) )
                    {   hShip.setInBattle(true);
                        break;
                    } // placement fault continue to the next attempt
                }
            } else placed = false;
        }
      return placed;
    }
    /*
     * #fireBoom  метод для реализации выстрела противника по кораблю игрока
     * @params (x,y) координаты цели на поле игрока
     * @return boolean попал противник или нет по кораблю игрока
     * также устанавливает свойство defeated если все корабли поражены
    */
    public synchronized boolean fireBoom(int xTarget, int yTarget) {
        boolean fireShot = false;

        if ((xTarget>=0)&&(xTarget<10)&&(yTarget>=0)&&(yTarget<10)) {
            fireShot = playerSea[0].getCell(xTarget,yTarget).fireCell();

            if (fireShot) {
                for (Ship hShip:hNavy) {
                    if (hShip.isInBattle()) {
                        return fireShot;
                    }
                }
                // No ships in the Battle. DEFEAT
                defeated =true;
            }
        }
        return fireShot;
    }
    // @return  коллекция кораблей игрока
    public ArrayList<Ship> getPlayerNavyArray(){
        return hNavy;
    }
    // @return строка наименований координат игрового поля (задается игроком?)
    public String[] getCoordNameSea () {
        return this.coordinateNameSea;
    }
    //  @return  ссылка поле игрока
    public SeaField getPlayerSea () {
        return this.playerSea[0];
    }
    //    @return  ссылка поле противника игрока
    public SeaField getTargetSea () {
        return this.playerSea[1];
    }
    //   @return  true если игрок-наблюдатель, команды ввода из интерфейса не разрешены
    boolean isObserver() {
        return isObserver;
    }
    // @return boolean если игрок побежден
    public boolean isDefeated () {return defeated;}
     // устанавливает очередность хода для игрока
    public synchronized void setMyTurn(boolean myTurn) {this.myTurn = myTurn; }
    // @return boolean true если следующий ход этого игрока
    public synchronized boolean isMyTurn() {return this.myTurn; }
    //  связывает игрока с контроллером правил игры
    public synchronized void sethGame(GameSeaBattle gameSeaBattle){ hGame = gameSeaBattle; }

    public synchronized GameSeaBattle gethGame() {return hGame;}
    /*
    * Player#getPlayerName()
    * @return playerName
    */
    public String getPlayerName() {
        return playerName;}
    //  @return размер игрового поля игрока
    public short getPlayerSeaSize() {
        return playerSeaSize;
    }
}
