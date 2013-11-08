package ru.sergeykravchenko.seabattle.player;

import ru.sergeykravchenko.seabattle.consoleapp.UIController;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaCell;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaField;

import java.util.Random;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;
/*
 *  Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 курсовая работа. класс ComputerPlayer для игры морской бой/h5>
 * <p>Класс реализует создание и методы работы с объектом Игрока-компьютера в игре  Морской бой </p>
 * <p> MVC: класс контроллера интерфейса  игры</p>
 * <p>наследует класс игрока <code>Player</code></p>
 * <p>реализует интерфейс асинхронного выполнения потока <code>callable</code> </p>
  * <p>методы:</p> <dl>
 * <dt>конструктор: @params <code>UIController</code> - ссылка на контроллер отображения (@deprecated)TODO:убрать параметр</dt>
 * <dd>инициализирует данные об игроке, задает имя компьютера, статус активности (наблюдатель или нет).</dd>

 * <dt>  <code>call()</code>:@return int[2] координаты следующего выстрела компьютера (x,y) </dt>
 * <dd> реализует вычисление следующего хода компьютера, по заданной <code>Strategy</code> стратегии,
 * если установлен флаг <code>myTurn</code> и компьютер в игре <code>(hGame!=null)</code>
 * </dd>
 * <dl>
 *
 * @author Sergey Kravchenko
 * @version 1.0
 * @see  Player, SeaField, SeaCell
 *
 */
public class ComputerPlayer  extends Player implements Callable {
    public enum Strategy {STRIKEBACK, RANDOM }
    public Strategy currentStrategy;
    private int lastX, lastY; // координаты последнего выстрела
    private int lastDestroyedX, lastDestroyedY; // координаты последнего успешного выстрела
    private int nextX, nextY; // координаты следующего выстрела (-1, если ход еще не подобран)

  //  private SeaCell[][] neighbors;
   // boolean shipDestroyed = false;
    Random rndGen;
    public ComputerPlayer(UIController hInstance) {
        super(hInstance);
        lastDestroyedX=-1;lastDestroyedY=-1;
        lastX = -1; lastY=-1;  // нет еще попаданий
        nextX = -1; nextY=-1;  // не нашли еще хода
     //   neighbors = new SeaCell[3][3];
        isObserver = true; /* No Input needed for Computer Players*/
        cmdPlayer = Player.PlayerMode.START; // No Negotiations with other Players
        playerName += "Computer";
        currentStrategy = Strategy.RANDOM;

        rndGen = new Random();
        //    System.out.println ("Game Player Controller extended to:"+playerName);
       // System.out.println("Computer constructor:"+Thread.currentThread().getName() + "  thread! " );
    }
    @Override
    public int[] call() {
       // try {  //  for sleep only
            //System.out.println("Computer player "+Thread.currentThread().getName() + " went to sleep.");
            //sleep(3000l);
            if (gethGame()!=null) {
                // расчет следующего хода
                nextX = -1; nextY=-1; // не нашли еще хода
                int direction =0;
                SeaCell iCell = null;
                // расчет на добивание корабля. ToDo: учитывать какие корабли остались, м.сэкономить 1 выстрел
                if ((lastDestroyedX>=0)&&(lastDestroyedY>=0)){
                    // есть подсказка куда бить
                    direction=lastX-lastDestroyedX;
                    if (playerSea[1].getCell(lastX,lastY).isDestroyed()) {// хорошо бъем. надо продолжить
                        lastDestroyedX=lastX;lastDestroyedY=lastY;
                        if (direction==0) {// направление было вертикальное
                            tryUpDown(lastX, lastY, 4);
                        }else {// направление выстрелов было по горизонтали
                            tryLeftRight(lastX, lastY, 4);
                        }
                    }else { // последний выстрел был неудачным. откат
                        if (direction==0) {// направление было вертикальное
                            direction=lastY-lastDestroyedY; // щупаем ячейку вертикально назад
                            iCell= playerSea[1].getCell(lastDestroyedX,lastDestroyedY-direction);
                            if ((iCell!=null)&&((iCell.isEmpty())||(iCell.isDestroyed()) ))
                                 tryUpDown(lastDestroyedX,lastDestroyedY, 4);
                            else tryLeftRight(lastDestroyedX,lastDestroyedY, 4);
                        }else {// направление выстрелов было по горизонтали
                               //direction = lastY-lastDestroyedY;
                            iCell= playerSea[1].getCell(lastDestroyedX-direction,lastDestroyedY);
                            if ((iCell!=null)&&( (iCell.isEmpty())||(iCell.isDestroyed()) ) )
                                 tryLeftRight(lastDestroyedX,lastDestroyedY, 4);
                            else tryUpDown(lastDestroyedX,lastDestroyedY, 4);
                        }
                    }
                }else {
                    iCell= playerSea[1].getCell(lastX,lastY)     ;
                    if ((iCell!=null)&&(iCell.isDestroyed())) {
                        // нет подсказки, но было первое попадание
                        lastDestroyedX=lastX; lastDestroyedY=lastY;  // запоминаем подсказку
                        // случайно выбираем выстрел вертикально или горизонтально
                        if (rndGen.nextBoolean()){
                            if (!tryUpDown(lastX, lastY, 4))
                                tryLeftRight(lastX, lastY, 4);
                            }else if (!tryLeftRight(lastX, lastY, 4))
                        tryUpDown(lastX, lastY, 4);
                    }
                }
                if (nextX<0) { // если не был найден ход на добивание, выбираем по стратегии
                    if (lastDestroyedX>=0) {// подсказка была, значит убили весь корабль.
                        //надо пометить все соседние ячейки, чтобы не стрелять зря
                        // ToDO: проверить варианты, отметки соседей когда последней добивается палуба в середине корабля
                        direction=lastX-lastDestroyedX;
                        if (direction==0) {// направление выстрелов было вертикальное
                            direction=lastY-lastDestroyedY;
                            if ((direction)>0)
                                 direction=1;
                            else direction=-1;

                            iCell=playerSea[1].getCell(lastDestroyedX,lastDestroyedY-direction)      ;
                            if (((iCell)!=null )&&(direction!=0)&&(iCell.isDestroyed())) {// убитый корабль расположен по вертикали.заполняем справа и слева
                                  setNeighborsVertically(direction, lastX, lastY);
                            }
                            else { // НО,  убитый корабль расположен по горизонтали .заполняем сверху и снизу
                                  lastX=lastDestroyedX-1; // щупаем ячейку слева
                                  iCell=playerSea[1].getCell(lastX,lastDestroyedY);
                                  if (iCell==null) {lastX=lastDestroyedX++;} // слева граница начнем с пораженной
                                  else if (iCell.isDestroyed()) // слева пораженная ячейка начинаем справа
                                           lastX = lastDestroyedX + 1;
                                  setNeighborsHorizontally(lastX - lastDestroyedX, lastX, lastDestroyedY);
                            }
                        }else {//направление выстрелов было по горизонтали
                            iCell= playerSea[1].getCell(lastDestroyedX-direction,lastDestroyedY) ;
                            if ((iCell!=null)&&(iCell.isDestroyed()) ) {
                                // и убитый корабль расположен по горизонтали .заполняем сверху и снизу
                                if ((lastX-lastDestroyedX)>=0)
                                     direction =1;
                                else direction =-1;
                                setNeighborsHorizontally(direction, lastX, lastY);
                            } else { // НО,  убитый корабль расположен по вертикали.заполняем справа и слева
                                lastY=lastDestroyedY-1; // щупаем ячейку сверху
                                iCell=playerSea[1].getCell(lastDestroyedX,lastY);
                                if (iCell==null) lastY=lastDestroyedY++;//сверху граница начнем с пораженной
                                else if (iCell.isDestroyed()) // сверху пораженная ячейка, нам начать снизу
                                         lastY=lastDestroyedY+1;
                                setNeighborsVertically(lastY-lastDestroyedY, lastDestroyedX, lastY);
                            }
                        }
                    }
                    lastDestroyedX=-1;lastDestroyedY=-1; // забываем подсказку по недобитому кораблю
                    switch (currentStrategy) {
                        case STRIKEBACK:
                             //  no implementation, try random
                        case RANDOM:
                             for (int itry=100000;itry>0;itry--) {

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
                }
                // вычисление хода закончено, координаты занесены в nextX, nextY
                //        System.out.println("Computer turn. X: "+nextX+" Y:"+nextY);
            }
      /*  } catch (InterruptedException e) {
             e.printStackTrace();  //
        }
        finally {
            System.out.println("Computer player thread finished");
        }  */
        lastX=nextX; lastY = nextY;
        return new int[]{nextX, nextY};
    }
    // Устанавливает по горизонтали соседние ячейки вокруг убитого корабля
    // выполненяется только вокруг пораженных (Destroyed) ячеек
    // @param direction = 1 если установка д. идти влево от lastX,LastY и -1 если вправо
    // @param lastX,lastY координаты ячейки слева или справа от последней пораженной
    private void setNeighborsHorizontally(int direction, int lastX, int lastY) {
    //    System.out.println("Horizontal Neighbors around lastX:"+lastX+" lastY:"+lastY);
        for (int i = 0; i < 6; i++) {
            setCellNeighbored(playerSea[1], lastX - direction * i, lastY - 1);
  //          System.out.println("HN i:"+i+" lX:"+(lastX- direction * i)+" lY:"+(lastY-1));
            setCellNeighbored(playerSea[1], lastX  - direction * i, lastY +1);
   //         System.out.println("HN i:"+i+" lX:"+(lastX- direction * i)+" lY:"+(lastY+1));
            SeaCell iCell = playerSea[1].getCell(lastX - direction * i, lastY);
            if ((iCell != null) && (i > 0)) {
                if (!iCell.isDestroyed()) {
                    setCellNeighbored(playerSea[1], lastX - direction * i, lastY);
    //                System.out.println("HN i:"+i+" lX:"+(lastX- direction * i)+" lY:"+(lastY));
                    break;  // корабль закончился
                }
            }
        }
    }
    // Устанавливает по вертикали соседние ячейки вокруг убитого корабля
    // выполненяется только вокруг пораженных (Destroyed) ячеек
    // @param 1 если установка д. идти вверх от lastX,LastY и -1 если вниз
    // @param lastX,lastY координаты ячейки сверху или снизу от последней пораженной
    private void setNeighborsVertically(int direction, int lastX, int lastY) {
   //     System.out.println("Vertical Neighbors around lastX:"+lastX+" lastY:"+lastY);
        for (int i = 0; i < 6; i++) {
            setCellNeighbored(playerSea[1], lastX - 1, lastY - direction * i);
   //         System.out.println("VN i:"+i+" lX:"+(lastX-1)+" lY:"+(lastY- direction * i));
            setCellNeighbored(playerSea[1], lastX + 1, lastY - direction * i);
  //          System.out.println("VN i:"+i+" lX:"+(lastX+1)+" lY:"+(lastY- direction * i));
            SeaCell iCell = playerSea[1].getCell(lastX, lastY - direction * i);
            if ((iCell != null) && (i > 0)) {
                if (!iCell.isDestroyed())  {
                    setCellNeighbored(playerSea[1], lastX, lastY - direction * i);
 //                   System.out.println("VN i:"+i+" lX:"+(lastX)+" lY:"+(lastY- direction * i));
                    break; // корабль закончился
                }
            }
        }
    }

    // @return true еcли найдена ячейка на добивание слева или справа. координаты заносятся в nextX, nextY
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
    // устанавливает состояние ячейки Neighbored если она существует
    private void setCellNeighbored(SeaField map,int x,int y) {
        if ((x>=0)&&(y>=0)&&(x<playerSeaSize)&&(y<playerSeaSize)
            &&map.getCell(x,y).isEmpty() )
            map.getCell(x, y).setCellNeighbored();
    }
    // @return true еcли найдена ячейка на добивание справа. координаты занесены в nextX, nextY
    boolean tryRight(int xx,int yy, int depth) {
        SeaCell tryCell = playerSea[1].getCell(xx,yy);
        if (depth>0){
            if(tryCell!=null) {
                if (tryCell.isDestroyed()) {
                    if (tryRight(xx+1, yy, depth - 1))
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
