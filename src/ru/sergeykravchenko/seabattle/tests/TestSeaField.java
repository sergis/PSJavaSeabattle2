package ru.sergeykravchenko.seabattle.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sergeykravchenko.seabattle.gameseabattle.SeaField;
import ru.sergeykravchenko.seabattle.gameseabattle.Ship;

/*
 * *   Java 1 Course work (IntelliJ Idea)
 * <h5>Java 1 Домашнее задание/курсовая работа. класс TestSeaField для игры морской бой/h5>
 * <p>Класс реализует проверки методов объектов SeaField игры  Морской бой </p>
 * <p>/p>
 * <p>методы : </p> <ul>
 * @author Sergey Kravchenko
 * @version 0.0
 * @see  , 
 * Created with IntelliJ IDEA. * Date: 22.10.13    * Time: 0:04
 */
public class TestSeaField {
    SeaField sea;
    Ship[] navyShip;
    @Before
    public void setUp() throws Exception {
        sea = new SeaField((short) 10);
        navyShip = new Ship[10];
        navyShip[0]=new Ship(4);
        navyShip[1]=new Ship(3); navyShip[2]=new Ship(3);
        navyShip[3]=new Ship(2); navyShip[4]=new Ship(2);navyShip[5]=new Ship(2);
        navyShip[6]=new Ship(1); navyShip[7]=new Ship(1); navyShip[8]=new Ship(1);navyShip[9]=new Ship(1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPlaceShip() throws Exception {

        assertTrue   ("No placement 1 deck to 5,5 vertical",sea.placeShip(navyShip[6],5,5,true));
        assertTrue   ("No placement 3 deck to 5,0 vertical",sea.placeShip(navyShip[2],5,0,true));
        assertTrue ("No placement 4 deck to 0,0 horizontal",sea.placeShip(navyShip[0],0,0,false));
        assertTrue ("No placement 3 deck to 0,5 horizontal",sea.placeShip(navyShip[1],0,5,false));
        assertTrue ("No placement 1 deck to 0,9 horizontal",sea.placeShip(navyShip[9],0,9,false));
        assertTrue ("No placement 1 deck to 9,9 horizontal",sea.placeShip(navyShip[8],9,9,false));
        assertTrue ("No placement 1 deck to 9,0 horizontal",sea.placeShip(navyShip[7],9,0,false));
     assertFalse ("Over placement 1 deck to 9,0 horizontal",sea.placeShip(navyShip[6],9,0,false));
          assertTrue ("No placement 2 deck to 3,9 vertical",sea.placeShip(navyShip[3],3,9,true));
       assertFalse ("Over placement 2 deck to 4,9 vertical",sea.placeShip(navyShip[4],4,9,true));
          assertTrue ("No placement 2 deck to 7,7 vertical",sea.placeShip(navyShip[4],7,7,true));
          assertTrue ("No placement 2 deck to 9,5 vertical",sea.placeShip(navyShip[5],9,5,true));

    }
}
