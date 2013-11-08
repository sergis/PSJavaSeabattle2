package ru.sergeykravchenko.seabattle.consoleapp;
/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h4>Java 1 Lesson 3 Домашнее задание/курсовая работа</h4>
 * <p>Класс Управления приложением и интерфейса с пользователем для игры Морской бой </p>
 * <p>Предназначен для реализации методов и данных игры, связанных со средой исполнения и интерфейсом пользолателя/p>
 * <p> Front & View Controller  патерна MVC   </p>
 * <p>методы : </p>
 * <ul>
 * <li>конструктор: запускает связь с интерфесами, инициализирует приложение, библиотеки, тестовый режим</li>
 * <li>getInstanceMode():  выдает код текущего режима работы игры  (настройка, игра, завершение, рестарт</li>
 * <li>TODO: для отработки команд пользователя и параметров, в режимах Игра, Настройка нужен будет еще метод ???</li>
 * <li> renderView(Player hhPlayer): выполняет отрисовку экрана для заданного игрока </li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see    Player, ComputerPlayer, GameSeaBattle, TestSeaBattle
 *
 */
import ru.sergeykravchenko.seabattle.gameseabattle.SeaCell;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaField;
import ru.sergeykravchenko.seabattle.gameseabattle.Ship;
import ru.sergeykravchenko.seabattle.player.Player;

import static ru.sergeykravchenko.seabattle.consoleapp.UIController.InstanceMode.*;

public class UIController {
    /*
    *
    */
    public enum InstanceMode {QUIT, STOP,
        START, PLAY, TUNER
    }

    private InstanceMode nextCmd;
    private Player.PlayerMode[] playerCmdQueue = {Player.PlayerMode.WAIT, Player.PlayerMode.WAIT};
    private Player.PlayerMode playerCmd = Player.PlayerMode.WAIT;
    private boolean modeTesting;
    private TestSeaBattle hTestSeaBattle;
    /*
    * инициализация приложения и интерфейса с игроками
    * Если задан режим теста, то инициализируется генератор тестовых последовательностей TestSeaBattle
     */
    public UIController(boolean modeTest){
        modeTesting = modeTest;
        hTestSeaBattle=null;
        if (modeTesting) {
            hTestSeaBattle = new TestSeaBattle();
        }
        nextCmd = START;
        //boolean mode;
        System.out.println ("Instance started");
    }

    /*
    *  UIController#getInstanceMode()
    *  @return возвращает команду управления режимом работы программы
    *  если задан тестовый режим, то команды поставляются из тест-контроллера hTestSeaBattle. ,
    *  в боевом режиме определяются очередью команд, формируемой на основе команд игроков, и событий в приложении
    */
     public InstanceMode getInstanceMode() {
        if (modeTesting&&(hTestSeaBattle!=null)) {
            nextCmd = hTestSeaBattle.getTestCmd();
        } else nextCmd = InstanceMode.QUIT;

        System.out.println("Get command queue received:" + nextCmd);
        return (nextCmd);
    }
   /*
    *  UIController#renderView()
    *  выполняет визуализацию игры и настроек игры для интерфейса игрока.
    */
    public void renderView(Player hhPlayer) {

    /* отрисовка сцены (координаты, рамки, блоки сообщений, кнопки, ) */
        InstanceMode viewPointer = nextCmd;
        switch (viewPointer) {
            case TUNER: renderTunes(hhPlayer);
                break;
            case PLAY:
            case START:
                       renderSeaField(hhPlayer);
                       break;
            case STOP:
            case QUIT:
                System.out.println(" Quit View rendered for Player:" + hhPlayer.getPlayerName() );
                break;
            default:
                System.out.println("Undefined view, please define VIEW switch");
        }
      // реальный вывод отрисовки 
    }
    /*
    * UIController#renderSeaField(hhPlayer)
    *
    * выводит текущие настройки игры для игрока
    *
    */
    public void renderSeaField(Player hhPlayer ){
        String[] coordName = hhPlayer.getCoordNameSea();
        short seaSize = hhPlayer.getPlayerSeaSize();
        renderSeaFieldConsole(hhPlayer);
        renderSeaFieldConsole(hhPlayer.getTargetSea(), seaSize, coordName,
                                "Игровое поле противника для "+hhPlayer.getPlayerName() );
    }
    // отрисовка игрового поля на консоли
    public  void   renderSeaFieldConsole(Player hhPlayer){
        renderSeaFieldConsole(hhPlayer.getPlayerSea(), hhPlayer.getPlayerSeaSize(),
                hhPlayer.getCoordNameSea(),"Игровое поле "+hhPlayer.getPlayerName() );
    }
    private void renderSeaFieldConsole(SeaField hSeaField, short seaSize, String[] coordName, String header){
        SeaCell cell;
        System.out.println("* * *"+ header+" * * *");
        if (hSeaField!=null) {
            /* отрисовка координатной сетки  */
            System.out.print("***");
            for (int i=0;i<seaSize;i++)
                System.out.print(" "+coordName[0].substring(i,i+1));
            System.out.println(" **"); // отбивка

            /* отрисовка игрового поля  */

            for (short i=0;i<seaSize;i++) {
                System.out.print(" "+coordName[1].substring(i,i+1)+"*");
                for (short j=0;j<seaSize;j++) {
                    cell = hSeaField.getCell(j,i);
                    System.out.print("|");
                    switch (cell.getCellState()) {
                    case EMPTY:
                         System.out.print("_"); break;
                    case DECK:
                        System.out.print("#");
                        break;
                    case NEIGHBORED:
                        System.out.print(".");
                        break;
                    default:
                            System.out.print("?");
                    }
                }
                System.out.println("| *");   // конец строки   отбивка
            }
        }
    }
   /*
    * UIController#renderTunes(hhPlayer)
    *
    * выводит текущие настройки игры для игрока
    */
    public void renderTunes(Player hhPlayer ){
        System.out.println("* * *Настройки для игрока "+hhPlayer.getPlayerName()+" * * *");
        System.out.println("* * Размер игрового поля "+hhPlayer.getPlayerSeaSize()+" * * *");
        System.out.println("* * Флот к бою * * *");
        if (hhPlayer.getPlayerNavyArray()!=null)
            for( Ship hShip : hhPlayer.getPlayerNavyArray()){
              System.out.println("* "+hShip.getDecksOnShip()+" decks ship*x: "
                      +hShip.getxCoordinate()+"*y: "+hShip.getyCoordinate()+" *v: "+hShip.isVertical());
            }
        else System.out.println("* No Fleet Assigned ***");
    }
    /*
     *   Получает следующую команду от Игрока
     */

    public Player.PlayerMode getPlayerCmdNext() {
        int i; Player.PlayerMode playerCmd;
        playerCmd = playerCmdQueue[0];
        for (i=0;i< playerCmdQueue.length-1;i++)
           playerCmdQueue[i] = playerCmdQueue[i+1];
        playerCmdQueue[playerCmdQueue.length-1]=Player.PlayerMode.WAIT; //* Empty cell always wait
        return playerCmd;
    }
    /*
     *  записывает внеочередную команду для игрока
     */
    public void pushPlayerCmdStack( Player.PlayerMode nextCmd) {
        int i;
        //* No overflow check yet
        for (i= playerCmdQueue.length-1;i>0;i--)
            playerCmdQueue[i]= playerCmdQueue[i-1];
        playerCmdQueue[0] = nextCmd;
    }
    /*
     *  записывает очередную команду для игрока
     */
    public void addPlayerCmdNext( Player.PlayerMode nextCmd) {
        int i;
        //* No overflow check yet
        for (i=0;i< playerCmdQueue.length;i++) {
            if (playerCmdQueue[i] == Player.PlayerMode.WAIT) {
                playerCmdQueue[i] = nextCmd;
            }
        }
    }
    //* внешний геттер для режима тестирования
    public boolean isModeTesting() {
        return modeTesting;
    }
     // * внешний cеттер для режима тестирования
     public void setModeTesting(boolean modeTesting) {
        this.modeTesting = modeTesting;
     }
}