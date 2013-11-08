## Курсовой проект "Игра Морской бой"
* Курс Java Basic#1. Школа программирования www.prog-school.ru. преподаватель А.Ераскин  
CREDITS: 
* (c) 2013, Sergey Kravchenko. course work 
* (c) 2008, 2012 Oracle and/or its affiliates (FireworksDraws.java) 

## Features
* Реализация на графическом интерфейсе JavaFX
* код в соответствии с патерном MVC
* асинхронное многопточное выполнение
- расчета ходов компьютером
- анимация выстрелов и взрывов
- обработка GUI ввода игрока

###Game features.
* режим 1 игрока, 2-й игрок - компьютер
* фоновый рисунок, логотип, силуэты кораблей на карте игрока
* консоль окно (3 строки) со скроллингом сообщений компьютера
* меню - выход, about, расстановка кораблей
* кнопка PLAY - начало игры.
* указание игроком хода-выстрела на карте противника
* автоматическая расстановка кораблей для игрока и компьютера

* стратегия выбора ходов компьютером RANDOM
* экономия выстрелов компьютером.
* 1) учет соседних ячеек после гибели корабля игрока
* 2) добивание корабля после попаданий

* *анимация выстрелов с обеих сторон*
*  1) запуск и полет ракеты
*  2) взрыв ракеты в точке попадания
*  3) отметка попадания по ячейке
*  4) отметка поражения корабля

### Техническое задание, документация проекта, код
ТЗ и документация - в директории /doc  
код - в директории /src
### Правила версионного контроля
см. в документе TeamBuilding.md

