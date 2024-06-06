package ru.stepup.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

public class StartTest {

    @Test
    void FractionInvocationHandler() {
        // 1-ый способ кэширования с использованием InvocationHandler
        FractionTest fr = new FractionTest(1, 3);
        Fractionable fraction = Utils.cashProxy(fr);

        // делаем 3 запроса на получение дроби, сброс кэша и ещё 2 запроса на получение дроби
        DecimalFormat df = new DecimalFormat("#.###");
        Assertions.assertEquals(df.format(0.333), df.format(fraction.doubleValue()));
        System.out.println(fraction.doubleValue());
        Assertions.assertEquals(df.format(0.333), df.format(fraction.doubleValue())); // проверяем значение взятое из кэша

        fraction.setNum(5);

        System.out.println(fraction.doubleValue());
        Assertions.assertEquals(df.format(1.667), df.format(fraction.doubleValue()));
        Assertions.assertEquals(2, fr.count);   // кол-во обновлений кэша - вызова Fraction.doubleValue()
    }
}
