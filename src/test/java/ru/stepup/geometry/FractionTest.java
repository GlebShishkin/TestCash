package ru.stepup.geometry;

import ru.stepup.geometry.annotation.Cache;
import ru.stepup.geometry.annotation.Mutator;

// копия класса Fraction с добавленным счетчиком вызовов кэширования
public class FractionTest implements Fractionable {
    private int num;
    private int denum;
    public int count = 0;

    public FractionTest(int num, int denum) {
        this.num = num;
        setDenum(denum);
        count = 0;
    }

    @Mutator
    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Mutator
    public void setDenum(int denum) {
        if (denum == 0) {
            throw new IllegalArgumentException("Делитель не может быть равным нулю");
        }
        this.denum = denum;
    }

    public int getDenum() {
        return denum;
    }

    @Override
    @Cache
    public double doubleValue() {
        System.out.println("    Вызов FractionTest.doubleValue для обновления кэшированого значения");
        count++;
        return (double) num/denum;
    }
}
