package ru.sergeykravchenko.seabattle.uicontroller;
/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h4>Java 1 Lesson 3 Домашнее задание/курсовая работа</h4>
 * <p>Класс Управления приложением и интерфейса с пользователем для игры Морской бой </p>
 * <p>Предназначен для реализации всех функций и данных связанных со средой исполнения и интерфейсом с пользолателем игры/p>
 * <p> Front & View Controller  патерна MVC   </p>
 * <p>методы : </p>
 * <ul>
 * <li>конструктор: запускает связь с интерфесами, инициализирует приложение, библиотеки, тестовый режим</li>
 * <li>getInstanceMode():  выдает код текущего режима работы игры  (настройка, игра, завершение, рестарт</li>
 * <li>TODO: для отработки команд пользователя и параметров, в режимах Игра, Настройка нужен будет еще метод ???</li>
 * <li> renderView(Player hhPlayer): выполняет отрисовку для заданного игрока </li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see    Player, ComputerPlayer, GameSeaBattle, TestSeaBattle
 *
 */
import ru.sergeykravchenko.seabattle.player.Player;
import ru.sergeykravchenko.seabattle.testseabattle.TestSeaBattle;

public class UIController {
    /*
    *
    */
    public static final short  CMD_QUIT = 0,
    CMD_STOP=1,CMD_START=2,CMD_PLAY=3,CMD_TUNER=4;
    private short nextCmd;
    private Byte[] playerCmdQueue = {Player.PLAYER_WAIT, Player.PLAYER_WAIT};
    private byte playerCmd = Player.PLAYER_WAIT;
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
        //boolean mode;


        System.out.println ("Instance started");
    }
    /*
    * внешний геттер для режима тестирования
    *
     */
    public boolean isModeTesting() {
        return modeTesting;
    }
    /*
     * внешний cеттер для режима тестирования
     *
     */
     public void setModeTesting(boolean modeTesting) {
        this.modeTesting = modeTesting;
    }
    /*
    *  UIController#getInstanceMode()
    *  @return возвращает команду управления режимом работы программы
    *  если задан тестовый режим, то команды поставляются из тест-контроллера hTestSeaBattle. ,
    *  в боевом режиме определяются очередью команд, формируемой на основе команд игроков, и событий в приложении
    */
     public short getInstanceMode() {
        if (modeTesting&&(hTestSeaBattle!=null)) {
            nextCmd = hTestSeaBattle.getTestCmd();
        } else nextCmd = UIController.CMD_QUIT;

        System.out.println("Get command queue received:" + nextCmd);
        return (nextCmd);
    }
   /*
    *  UIController#renderView()
    *  выполняет визуализацию игры и настроек игры для интерфейса игрока.
    */
    public void renderView(Player hhPlayer) {
        final String[] VIEW =
                {"VIEW QUIT", "VIEW OVER",
                        "VIEW_START", "VIEW_STEP"};
        //System.out.println ("Game View render");
        /* отрисовка сцены (координаты, рамки, блоки сообщений, кнопки, ) */
        short viewPointer = UIController.CMD_TUNER;
        switch (viewPointer) {
            case UIController.CMD_TUNER: renderTunes(hhPlayer);
            case UIController.CMD_PLAY:
            case UIController.CMD_START: renderSeaField(hhPlayer);
            case UIController.CMD_STOP:
            case UIController.CMD_QUIT:
                System.out.println(" View rendered for Player:" + hhPlayer.getPlayerName() );
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
    *
    * */
    public void renderSeaField(Player hhPlayer ){


        System.out.println("* * *Игровое поле игрока "+hhPlayer.getPlayerName()+" * * *");
        String[] coordName;
         /* отрисовка координатной сетки  */
        if (hhPlayer.getPlayerSea()!=null) {
            coordName = hhPlayer.getCoordNameSea();
            System.out.println("**"+coordName[1]);
        }
        /* отрисовка поля  */
        short i;
        for (i=0;i<hhPlayer.getPlayerSeaSize();i++)
            System.out.println("**" /*+ coordName[2][i]*/ + "0123456789" + " *");


    }
    /*
    * UIController#renderTunes(hhPlayer)
    *
    * выводит текущие настройки игры для игрока
    *
    *
    * */
    public void renderTunes(Player hhPlayer ){

        System.out.println("* * *Настройки для игрока "+hhPlayer.getPlayerName()+" * * *");

    }
    /*
     *   Получает следующую команду от Игрока
     */

    public byte getPlayerCmdNext() {
        int i; byte playerCmd;
        playerCmd = playerCmdQueue[0];
        for (i=0;i< playerCmdQueue.length-1;i++)
           playerCmdQueue[i] = playerCmdQueue[i+1];
        playerCmdQueue[playerCmdQueue.length-1]=Player.PLAYER_WAIT; //* Empty cell always wait
        return playerCmd;
    }
    /*
     *  записывает внеочередную команду для игрока
     */
    public void pushPlayerCmdStack( byte nextCmd) {
        int i;
        //* No overflow check yet
        for (i= playerCmdQueue.length-1;i>0;i--)
            playerCmdQueue[i]= playerCmdQueue[i-1];
        playerCmdQueue[0] = nextCmd;
    }
    /*
     *  записывает очередную команду для игрока
     */
    public void addPlayerCmdNext( byte nextCmd) {
        int i;
        //* No overflow check yet
        for (i=0;i< playerCmdQueue.length;i++) {
            if (playerCmdQueue[i] == Player.PLAYER_WAIT) {
                playerCmdQueue[i] = nextCmd;
            }
        }
    }
}