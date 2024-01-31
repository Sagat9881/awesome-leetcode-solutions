package ru.apzakharov.hard_sessions;

import java.util.Arrays;
import java.util.Objects;

/**
 * Шеф-повар собрал данные об уровне удовлетворения от n блюд.
 * <p></p>
 * Шеф-повар может приготовить любое блюдо за 1 единицу времени.
 * Коэффициент удовлетворения блюдом определяется как время,
 * затраченное на приготовление этого блюда, включая предыдущие блюда, умноженное на уровень удовлетворенности,
 * т. е. время[i] * удовлетворение[i].
 * <p></p>
 * Возвращает максимальную сумму коэффициента,
 * которую повар может получить после приготовления некоторого количества блюд.
 * <p></p>
 * Блюда можно готовить в любом порядке, и повар может отказаться от любого количества блюд,
 * чтобы получить максимальное значение.
 */
public class ReducingDishes {
    private static final int[] satisfactionOnlyPositive = {1, 2, 3};
    // 1*1 + 2*2 + 3*3 = 1 + 4 + 9 = 14 - верно
    private static final int[] satisfactionPositiveAndZero = {0, 2, 3};
    // 0*1 + 2*2 + 3*3 = 0 + 4 + 9 = 13 - верно
    // 2*1 + 3*2 = 2 + 6 = 8 - не верно
    private static final int[] satisfactionOnlyNegative = {-1, -2, -3};
    // = 0 - верно
    private static final int[] satisfactionNegativeAndZero = {-1, -2, 0};
    // 0*1 = 0 - верно
    private static final int[] satisfactionMixed = {-1, 1, 2, -3};
    // (-1)*1 + 1*2 + 2*3 = (-1) + 2 + 6 = 7 - верно
    // 1*1 + 2*2 = 1 + 4 = 5 - не верно
    // (-3)*1 + (-1)*2 + 1*3 + 2*4 = (-3) + (-2) + 3 + 8 = 6 - не верно

    public static void main(String[] args) {
        final ReducingDishes service = new ReducingDishes();
        System.out.println("============");
        System.out.println(Arrays.toString(satisfactionOnlyPositive));
        System.out.println(service.maxSatisfaction(satisfactionOnlyPositive));

        System.out.println("============");
        System.out.println(Arrays.toString(satisfactionPositiveAndZero));
        System.out.println(service.maxSatisfaction(satisfactionPositiveAndZero));

        System.out.println("============");
        System.out.println(Arrays.toString(satisfactionOnlyNegative));
        System.out.println(service.maxSatisfaction(satisfactionOnlyNegative));

        System.out.println("============");
        System.out.println(Arrays.toString(satisfactionNegativeAndZero));
        System.out.println(service.maxSatisfaction(satisfactionNegativeAndZero));

        System.out.println("============");
        System.out.println(Arrays.toString(satisfactionMixed));
        System.out.println(service.maxSatisfaction(satisfactionMixed));
    }

    public int maxSatisfaction(int[] satisfaction) {
        // Надежный шаг - самый удачные блюда подавать в последнюю очередь (т.к. растет коэффициент времени)
        Arrays.sort(satisfaction);

        int[] satisfactionWithoutShit = discardShit(satisfaction);
        int[] satisfactionCost = getSatisfactionCost(satisfactionWithoutShit);

        return getSum(satisfactionCost, 0);
    }

    private int[] discardShit(int[] satisfaction) {
        int sum = getSum(satisfaction, 0);
        int index = 0;

        while (sum < 0 && index < satisfaction.length + 1) {
            sum = getSum(satisfaction, ++index);
        }

        if (index > 0) {
            int[] result = new int[satisfaction.length - index ];
            System.arraycopy(satisfaction, index, result, 0, result.length);
            return result;
        } else {
            return satisfaction;
        }
    }

    /**
     * @param satisfaction отсортированный в порядке возрастания массив с оценками удовлетворительности каждого блюда
     * @return Массив, содержащий те же оценки, в том же отсортированном порядке, но умноженные на индекс в исходном массиве (satisfaction)
     */
    private static int[] getSatisfactionCost(int[] satisfaction) {
        int[] satisfactionCost = new int[satisfaction.length];

        int i = 1;
        for (int j : satisfaction) {
            satisfactionCost[i - 1] = j * i;
            i++;
        }
        return satisfactionCost;
    }

    private static int getSum(int[] satisfaction, int skip) {
        return Arrays.stream(satisfaction).skip(skip).sum();
    }
}
