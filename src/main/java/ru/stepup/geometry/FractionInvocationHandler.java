package ru.stepup.geometry;

import ru.stepup.geometry.annotation.Cache;
import ru.stepup.geometry.annotation.Mutator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class FractionInvocationHandler implements InvocationHandler {

    private Fractionable fraction;
    private Double doubleValCash;   // кэшируемая на уровне экземпляра класса величина

    public FractionInvocationHandler(Fractionable fraction) {
        this.fraction = fraction;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class clazz = this.fraction.getClass();
        Method md = clazz.getMethod(method.getName(), method.getParameterTypes());

        if (md.isAnnotationPresent(Cache.class)) {
            // есть аннотация "Cache" -> получаем значение из кэша, но если он пустой - значала заполняем его
            if (doubleValCash == null) {
                // кэш пустой - заполним его
                this.doubleValCash = (Double) method.invoke(this.fraction, args);
            }
            return this.doubleValCash;
        }

        if (md.isAnnotationPresent(Mutator.class)) {
            // вызван метод с аннотацией Mutator - очищаем кэш
            this.doubleValCash = null;
        }

        return method.invoke(this.fraction, args);
    }
}
