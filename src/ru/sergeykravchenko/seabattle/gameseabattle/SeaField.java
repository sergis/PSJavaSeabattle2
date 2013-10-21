package ru.sergeykravchenko.seabattle.gameseabattle;

/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Lesson 3 Домашнее задание/курсовая работа. класс Игрового поля Игры морской бой/h5>
 * <p>Класс реализует создание и работу с игровым полем игры  Морской бой. MVC: субконтроллер модели игры</p>
 * <p>создает игровое поле заданного размера из классов ячеек поля, реализует методы управления полем и выдачи данных для отображения /p>
 * <p></p>
 * <p> </p>
 * <p>методы : </p> <ul>
 * <li>конструктор: запоминает играющих, инициализирует игровое поле, рейд с кораблями, устанавливает статус "настройка" </li>
 * <li> getCell(): возвращает ссылку на заданную ячейку игрового поля </li>
 * <li>TODO: setShip(): устанавливает корабль на поле игры </li>
 * <li>TODO: shotCell(): принимает выстрел создает игровое поле заданного размера</li>
 * <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see
 *
 * ? Player, ComputerPlayer, TestSeaBattle,
 * TODO: классы SeaCell
 *
 */
public class SeaField {
    private SeaCell[][] hSeaCell ;
    public short seaSize;
    public SeaField(short size){
        seaSize = size;
        hSeaCell = new SeaCell[size][size];
        for (int i=0;i<size;i++)
            for (int j=0;j<size;j++)
                hSeaCell[i][j]=new SeaCell();
        System.out.println("Seafield created "+size);
    }
/*public boolean isCellEmpty (short x,short y){
    return (hSeaCell[x][y] == null);
} */
// Placement for one of Ships on the player Sea Field
public boolean placeShip(Ship hShip, int xCoordinate, int yCoordinate,boolean straight) {
    boolean placed = false; // by default ship not placed
    int decks =  hShip.getDecksOnShip();
    if (hSeaCell[xCoordinate][yCoordinate].isEmpty()){
        int[][] surroundShip = new int[6+decks*3][2];

        if (straight) { // корабль расположен вертикально
            /*  координаты ячеек за кормой  корабля   */
            surroundShip[0][0] = xCoordinate-1;surroundShip[0][1] = yCoordinate-1; // bottom left
            surroundShip[1][0] = xCoordinate-1;surroundShip[1][1] = yCoordinate;   // low bottom
            surroundShip[2][0] = xCoordinate-1;surroundShip[2][1] = yCoordinate+1; // bottom  right
            /*  координаты ячеек перед носом корабля   */
            surroundShip[3][0] = xCoordinate-decks; surroundShip[3][1] = yCoordinate-1; // top left
            surroundShip[4][0] = xCoordinate-decks; surroundShip[4][1] = yCoordinate;    // top ahead
            surroundShip[5][0] = xCoordinate-decks; surroundShip[5][1] = yCoordinate+1;  // top right
            /* координаты палуб и ячеек справа и слева*/
            for (short i=0;i<decks;i++){
                surroundShip[6+i][0] = xCoordinate-i;        surroundShip[6+i][1] = yCoordinate;            // ship decks
                surroundShip[6+i+decks][0] = xCoordinate-i;  surroundShip[6+i+decks][1] = yCoordinate-1; // ship left board
                surroundShip[6+i+decks*2][0] = xCoordinate-i;surroundShip[6+i+decks*2][1] = yCoordinate+1; // ship right board
            }
        } else { // корабль расположен горизонтально
                        /*  координаты ячеек за кормой  корабля   */
            surroundShip[0][0] = xCoordinate-1;surroundShip[0][1] = yCoordinate-1; // bottom left
            surroundShip[1][0] = xCoordinate;  surroundShip[1][1] = yCoordinate-1;   // low bottom
            surroundShip[2][0] = xCoordinate+1;surroundShip[2][1] = yCoordinate-1; // bottom  right
            /*  координаты ячеек перед носом корабля   */
            surroundShip[3][0] = xCoordinate-1; surroundShip[3][1] = yCoordinate+decks; // top left
            surroundShip[4][0] = xCoordinate;   surroundShip[4][1] = yCoordinate+decks; // top ahead
            surroundShip[5][0] = xCoordinate+1; surroundShip[5][1] = yCoordinate+decks; // top right
            /* координаты палуб и ячеек справа и слева*/
            for (short i=0;i<decks;i++){
                surroundShip[6+i][0] = xCoordinate;          surroundShip[6+i][1] = yCoordinate+i;            // ship decks
                surroundShip[6+i+decks][0] = xCoordinate-1;  surroundShip[6+i+decks][1] = yCoordinate+i; // ship left board
                surroundShip[6+i+decks*2][0] = xCoordinate+1;surroundShip[6+i+decks*2][1] = yCoordinate+i; // ship right board
            }
        }
        //  проверяем доступность ячеек
        for (int i=0;i<6+decks*3;i++) {
            if ((((surroundShip[i][0]>=0)&&(surroundShip[i][1]>=0)
                    &&(surroundShip[i][0]<seaSize)&&(surroundShip[i][1]<seaSize)))){ // cell on the field
                if ((i>=6)&&(i<6+decks))
                    if (!hSeaCell[surroundShip[i][0]][surroundShip[i][1]].isEmpty())
                       {return false;} // cell not empty
            }else { // cell out of field
                 if ((i>=6)&&(i<6+decks))
                     return false; // no cells for the ship decks
            }
        }
        // ячейки доступны, заполняем поле кораблем

        for (int i=0;i<6+decks*3;i++) {
            if ((((surroundShip[i][0]>=0)&&(surroundShip[i][1]>=0)
                    &&(surroundShip[i][0]<seaSize)&&(surroundShip[i][1]<seaSize)))){ // cell on the field
                if ((i>=6)&&(i<6+decks)){// cell placed by deck
                    hSeaCell[surroundShip[i][0]][surroundShip[i][1]].setCellAsDeck(hShip);

                    hShip.putDeckOnField(i-6, hSeaCell[surroundShip[i][0]][surroundShip[i][1]]);

                } else {                 // cell placed as neighbored
                    hSeaCell[surroundShip[i][0]][surroundShip[i][1]].setCellNeighbored(); // set cell not empty
                }
            }

        }
    placed = true;
    }
    return placed; //Ship placed or not
}



    // @return ссылка на объект ячейки игрового поля
public SeaCell getCell(short x, short y){
    return hSeaCell[x][y];
}
}
