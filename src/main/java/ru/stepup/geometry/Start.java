package ru.stepup.geometry;

import java.lang.reflect.Proxy;

public class Start {
    public static void main(String[] args) {

        // 1-ый способ кэширования с использованием InvocationHandler
        Fraction fr1 = new Fraction(1, 3);
        Fractionable fraction1 = Utils.cashProxy(fr1);
        System.out.println(fraction1.doubleValue());
        System.out.println(fraction1.doubleValue());
        System.out.println(fraction1.doubleValue());
        fraction1.setNum(5);
        System.out.println(fraction1.doubleValue());
        System.out.println(fraction1.doubleValue());

        // 2-ой способ кэширования через класс-обертку
        Fraction fr2 = new FractionCash(1, 3);
        // создание кэшируемой версии объекта класса Fraction
        Fractionable fraction2 = Utils.cash(fr2);
        // получаем данные из кэша, обновляя его, если требуется
        System.out.println(fraction2.doubleValue());
        System.out.println(fraction2.doubleValue());
        System.out.println(fraction2.doubleValue());
        fraction2.setNum(5);
        System.out.println(fraction2.doubleValue());
        System.out.println(fraction2.doubleValue());
    }
}
