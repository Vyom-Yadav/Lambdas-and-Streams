package Streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// This is usage of some function of streams
public class Demo {
    public static List<People> create() {
        return Arrays.asList(
                new People("Vyom", 18, People.Gender.MALE),
                new People("Vrinda", 21, People.Gender.FEMALE),
                new People("Vyom", 17, People.Gender.MALE),
                new People("Vyom", 14, People.Gender.MALE),
                new People("Jack", 18, People.Gender.MALE)
        );
    }

    public static void main(String[] args) {
        List<People> list = create();

        System.out.println(
                list.stream()
                        .collect(Collectors.toMap(people -> people.getName() + " " + people.getAge(),
                                person -> person)));


        // given a list of people, create a map where
        // their name is the key and value is all the people with that name.

        System.out.println(
                list.stream()
                        .collect(Collectors.groupingBy(People::getName))

        );

        // given a list of people, create a map where
        // their name is the key and value is all the ages of people with that name.

        Map<String, List<Integer>> map = new HashMap<>();
        map = list.stream()
                .collect(Collectors.groupingBy(People::getName,
                        Collectors.mapping(People::getAge, Collectors.toList())));

        System.out.println(
                list.stream()
                        .collect(Collectors.groupingBy(People::getName,
                                Collectors.mapping(People::getAge, Collectors.toList())))
                // What this does is, groups name with age(list).
                // See output

        );

        List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        // Given an ordered list find the double of first even number greater
        // than 3.

        int result = 0;

        for (int e : numbers) {
            if (e > 3 && e % 2 == 0) {
                result = e * 2;
                break;
            }
        }

        System.out.println(result);

        // 8 units of work

        System.out.println(
                numbers.stream()
                        .filter(Demo::isGT3)
                        .filter(Demo::isEven)
                        .map(Demo::doubleIt)
                        .findFirst()
        );
        // 8 units only as streams are lazy, nothing happens till I call daddy.
        // Every element runs through the pipeline, if we get result we go home
        // don't care about the rest.

        // lazy evaluations is only effective only when you don't have
        // side effects (eg - println)

        System.out.println(Stream.iterate(100, e -> e + 1));
        // infinite stream, will give pipeline
        // head as it's lazy.

        // start with 100, create a series
        // 100,101,102,103, .....

        // Given a number k, and a count n, find total of the double of n
        // even nos starting with k where square root each number is > 20

        int k = 121;
        int n = 51;

        System.out.println(compute(k, n));

    }

    public static boolean isGT3(int num) {
        return num > 3;
    }

    public static boolean isEven(int num) {
        return num % 2 == 0;
    }

    public static int doubleIt(int num) {
        return num * 2;
    }

    public static int compute(int k, int n) {
        /*int result = 0;
        int index = k;
        int count = 0;
        while (count < n) {
            if (index % 2 == 0 && (Math.sqrt(index) > 20)) {
                result += index * 2;
                count++;
            }
            index++;
        }
        return result;*/

        //                    |
        // This is less scary V.

        return Stream.iterate(k, e -> e + 1)    // unbounded, lazy
                .filter(e -> e % 2 == 0)        // unbounded, lazy
                .filter(e -> Math.sqrt(e) > 20) // unbounded, lazy
                .mapToInt(e -> e * 2)           // unbounded, lazy
                .limit(n)                       // sized, lazy
                .sum();                         // eager
    }
}

// Simple POJO class
class People {

    private String name;
    private int age;
    private Gender gender;

    enum Gender {MALE, FEMALE}

    public People(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}


