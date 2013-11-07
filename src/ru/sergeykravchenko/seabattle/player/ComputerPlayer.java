package ru.sergeykravchenko.seabattle.player;

import ru.sergeykravchenko.seabattle.consoleapp.UIController;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaCell;
import java.util.Random;
import static java.lang.Thread.sleep;
/*
 *  Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 курсовая работа. класс ComputerPlayer для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Игрока-компьютера в игре  Морской бой </p>
 * <p> MVC: класс контроллера интерфейса  игры</p>
 * <p>наследует класс игрока <code>Player</code></p>
 * <p>методы:</p> <dl>
 * <dt>конструктор: @params <code>UIController</code> - ссылка на контроллер отображения (@deprecated)TODO:убрать параметр</dt>
 * <dd>инициализирует данные об игроке, задает имя компьютера, статус активности (наблюдатель или нет).</dd>
 *
 * <dt>  <code>run()</code>: </dt>
 * <dd> реализует вычисление следующего хода компьютера, по заданной <code>Strategy</code> стратегии,
 * если установлен флаг <code>myTurn</code> и компьютер в игре <code>(hGame!=null)</code></dd>
 * <dl>
 * @author Sergey Kravchenko
 * @version 1.0
 * @see  Player, SeaField, SeaCell
 *
 */
public class ComputerPlayer  extends Player implements Runnable{
    public enum Strategy {STRIKEBACK, RANDOM }
    public Strategy currentStrategy;
    private int lastX, lastY; // координаты последнего выстрела
    private int nextX, nextY; // координаты следующего выстрела (-1, если ход еще не подобран)
    private SeaCell iCell;
    private SeaCell[][] neighbors;
    boolean shipDestroyed = false;
    public ComputerPlayer(UIController hInstance) {
        super(hInstance);
        lastX = -1; lastY=-1;  // нет еще попаданий
        nextX = -1; nextY=-1;  // не нашли еще хода
        neighbors = new SeaCell[3][3];
        isObserver = true; /* No Input needed for Computer Players*/
        cmdPlayer = Player.PlayerMode.START; // No Negotiations with other Players
        playerName += "Computer";
        currentStrategy = Strategy.RANDOM;
    //    System.out.println ("Game Player Controller extended to:"+playerName);
        System.out.println("Computer constructor:"+Thread.currentThread().getName() + "  thread! " );
    }
    @Override
    public void run() {
        try {
             while (true) {
                 sleep(10000);
                 if (gethGame()!=null) {
                    if (this.isMyTurn()) { // расчет следующего хода
                        nextX = -1; nextY=-1; // не нашли еще хода
                        // расчет на добивание корабля
                        if (!((tryUpDown(lastX, lastY, 4))||(tryLeftRight(lastX, lastY, 4))))
                            switch (currentStrategy) {
                                case STRIKEBACK:
                                     //  no implementation, try random
                                case RANDOM:
                                     for (int itry=100000;itry>0;itry--) {
                                         Random rndGen = new Random ();
                                         int rnd = rndGen.nextInt(playerSeaSize * playerSeaSize - 1);
                                         int x = (rnd % playerSeaSize);
                                         int y = (rnd / playerSeaSize);
                                         if (playerSea[1].getCell(x,y).isEmpty()) {
                                             nextX=x;nextY=y;
                                             break;
                                         }
                                     }
                                     if ((nextX<0)||(nextY<0)) { // случайный подбор не удался
                                         for (int i=0;i<playerSeaSize;i++)
                                            for (int j=0;j<playerSeaSize;j++){
                                                if (playerSea[1].getCell(i,j).isEmpty()) {
                                                    nextX=i;nextY=j;
                                                    break;
                                                }
                                         }

                                }

                                break; // end of RANDOM strategy case
                            }
                        // вычисление хода закончено, координаты занесены в nextX, nextY
                        // TODO: lastX=nextX;lastY=nextY;в случае удачного выстрела
                        System.out.println("Computer turn. X: "+nextX+" Y:"+nextY);
                    }
                 }
                 System.out.println("Computer player "+Thread.currentThread().getName() + " went to sleep.");
             }
        } catch (InterruptedException e) {
             e.printStackTrace();  //
        }
        finally {
            System.out.println("Computer player thread finished");
        }
    }
    // @return true еcли найдена ячейка на добивание слева или справа. координаты занесены в nextX, nextY
    private boolean tryLeftRight(int xx, int yy, int depth) {
        SeaCell tryCell = playerSea[1].getCell(xx,yy);
        if (depth>0){
            if(tryCell!=null) {
                if (tryCell.isDestroyed()) {
                    if (tryLeftRight(xx-1, yy, depth - 1))
                        return true;
                } else if (tryCell.isEmpty()) {
                    nextX=xx;   nextY=yy;
                    return true;
                }
            }
            // уперлись в верхнюю границу поля или выстрел, попытаемся вправо
            if (tryRight(xx,yy,4)) return true;
        } //else shipDestroyed = true;
        return false;
    }
    // @return true еcли найдена ячейка на добивание справа. координаты занесены в nextX, nextY
    boolean tryRight(int xx,int yy, int depth) {
        SeaCell tryCell = playerSea[1].getCell(xx,yy);
        if (depth>0){
            if(tryCell!=null) {
                if (tryCell.isDestroyed()) {
                    if (tryDown(xx+1, yy, depth - 1))
                        return true;
                } else if (tryCell.isEmpty()) {
                    nextX=xx;   nextY=yy;
                    return true;
                }
            }
        } //else shipDestroyed = true;
        return false;
    }
    // @return true еcли найдена ячейка на добивание вверху или внизу. координаты занесены в nextX, nextY
    boolean tryUpDown(int xx, int yy, int depth) {
    SeaCell tryCell = playerSea[1].getCell(xx,yy);
    if (depth>0){
       if(tryCell!=null) {
          if (tryCell.isDestroyed()) {
             if (tryUpDown(xx, yy - 1, depth - 1))
                return true;
             } else if (tryCell.isEmpty()) {
                       nextX=xx;   nextY=yy;
                       return true;
                   }
       }
       // уперлись в верхнюю границу поля. или выстрел, попытаемся вниз
       if (tryDown(xx,yy+1,4)) return true;

    } //else shipDestroyed = true;
    return false;
    }
    // @return true еcли найдена ячейка на добивание внизу. координаты занесены в nextX, nextY
    boolean tryDown(int xx,int yy, int depth) {
        SeaCell tryCell = playerSea[1].getCell(xx,yy);
        if (depth>0){
            if(tryCell!=null) {
                if (tryCell.isDestroyed()) {
                    if (tryDown(xx, yy + 1, depth - 1))
                        return true;
                } else if (tryCell.isEmpty()) {
                    nextX=xx;   nextY=yy;
                    return true;
                }
            }
        } //else shipDestroyed = true;
        return false;
    }
}
