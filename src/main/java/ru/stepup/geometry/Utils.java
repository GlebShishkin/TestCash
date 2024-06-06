package ru.stepup.geometry;

import java.lang.reflect.Proxy;

public class Utils extends Object {

    // 1-ый способ кэширования с использованием InvocationHandler
    public static <T> T cashProxy(T obj) {

        return (T) Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new FractionInvocationHandler((Fractionable) obj)
        );
    }

    // 2-ой способ кэширования через класс-обертку (FractionCash)
    public static Fractionable cash(Fraction fraction) {
        return new FractionCash(fraction);
    }
}
