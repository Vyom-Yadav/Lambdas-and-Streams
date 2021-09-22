package MethodReferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);

        list.stream()
//                .map(Integer::toString) will give error as
//                          there are two potential candidates for this method
                .map(e -> String.valueOf(e))
                .map(e -> e.concat("hello")) // Because now out element
                // has been converted to a string
                .forEach(System.out::println);

        System.out.println(list.stream()
                        .reduce(0, Integer::sum)
                /*.reduce(0, (a,b)-> Integer.sum(a,b))*/);

        System.out.println(
                list.stream()
                        .map(String::valueOf)
//                        .reduce("", (carry, str) -> carry.concat(str))
                        .reduce("", String::concat)
        );

        //given values, double even numbers and total.

        // The OG Way

        System.out.println(list
                .stream()
                .filter(num -> num % 2 == 0)
                .mapToInt(num -> num * 2)
                .sum());

        System.out.println(list
                .parallelStream() // Caution use this carefully, work vs time
                .filter(num -> num % 2 == 0)
                .mapToInt(Demo::compute)
                .sum());

        // Double the even values and put them into a list

        // Wrong Way

        List<Integer> doubleOfEven = new ArrayList<>();

        list.stream()
                .filter(e -> (e % 2) == 0)
                .map(e -> e * 2)
                .forEach(doubleOfEven::add);
        // Don't do this, this is shared mutability, it's devil's work.

        System.out.println(doubleOfEven);

        List<Integer> doubleOfEven2 =
                list.stream()
                        .filter(e -> (e%2) == 0)
                        .map(e -> e*2)
                        .collect(Collectors.toList()); // Type of reduce

        System.out.println(doubleOfEven2);

    }

    public static int compute(int num) {
        // assume this is time intensive
        return num * 2;
    }

}
