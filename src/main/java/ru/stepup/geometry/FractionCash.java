package ru.stepup.geometry;

import ru.stepup.geometry.annotation.Cache;
import ru.stepup.geometry.annotation.Mutator;

import java.lang.reflect.Method;

// класс обертка (для 2-го варианта кэширования, без использования InvocationHandler)
public class FractionCash extends Fraction {

    private Double doubleValCash;   // кэшируемая на уровне экземпляра класса величина

    public FractionCash(int num, int denum) {
        super(num, denum);
        doubleValCash = null;   // сбросили кэш
    }

    public FractionCash(Fraction fraction) {
        super(fraction.getNum(), fraction.getDenum());
        doubleValCash = null;   // сбросили кэш
    }


    @Override
    public void setNum(int num) {
        super.setNum(num);
        if (isMethodMutator(Thread.currentThread().getStackTrace()[1].getMethodName()))  {
            doubleValCash = null;   // сбросили кэш
        }
    }

    @Override
    public void setDenum(int denum) {
        super.setDenum(denum);
        if (isMethodMutator(Thread.currentThread().getStackTrace()[1].getMethodName()))  {
            doubleValCash = null;   // сбросили кэш
        }
    }

    @Override
    public double doubleValue() {
        if (isMethodCashe(Thread.currentThread().getStackTrace()[1].getMethodName())) {
            // если кэш сброшен (= null) кэшируем значение заново, затем возвращаем
            if (doubleValCash == null) {
                doubleValCash = super.doubleValue();
            }
            return doubleValCash;   // возвращаем кэшированное значение
        }
        else    // на случай, если не обьявлена аннотация @Cash на родителе
            return super.doubleValue();
    }

    private boolean isMethodMutator(String methodName) {
        // проверяем аннотации "@Mutator" методов родительского класса
        Class clazz = this.getClass().getSuperclass();
        Method method;

        try {
            Class[] params = new Class[] {int.class};
            method = clazz.getMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        if (method.isAnnotationPresent(Mutator.class)) {
            return true;
        }
        return false;
    }

    private boolean isMethodCashe(String methodName) {
        // проверяем аннотации "@Cashe" методов родительского класса
        Class clazz = this.getClass().getSuperclass();
        Method method;

        try {
            Class[] params = null;
            method = clazz.getMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        if (method.isAnnotationPresent(Cache.class)) {
            return true;
        }
        return false;
    }
}
