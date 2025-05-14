import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalOperatorMain
{
    /**
     * @param doubleBinaryOp 一个 JDK 预定义的函数式接口，
     *                       表示两个 double 参数进行某些计算后返回一个新的 double 的操作。
     */
    private static double
    calculate(
            double a, double b,
            @NotNull
            DoubleBinaryOperator doubleBinaryOp
    )
    {
        return doubleBinaryOp.applyAsDouble(a, b);
    }

    public static void doubleBinaryOperatorUsage()
    {
        double res = FunctionalOperatorMain.calculate(
                1919810.00, 114514.00,
                (a, b) -> ((a + b) * (a - b)) / 114 + 514 - 810
        );

        System.out.println("Result = " + res + '\n');
    }

    public static void methodReferenceUsage()
    {
        HashMap<String, Integer> hashMap
                = new HashMap<>(
                Map.ofEntries(
                        Map.entry("Jesse", 1),
                        Map.entry("Mike",  1),
                        Map.entry("Lisa",  1),
                        Map.entry("Bob",   1)
                )
        );

        hashMap.forEach(
                (k, v)
                        -> System.out.printf("[%s, %d]\n", k, v)
        );

        Scanner scanner = new Scanner(System.in);

        do
        {
            System.out.println("Enter a key to execute merge operator (Press q to quit): ");
            String key;

            if (Objects.equals((key = scanner.nextLine()), "q")) {
                break;
            }

            /*
             * 散列表的合并操作，它的行为模式如下：
             *  1. 如果 key 存在那将该 key 映射的值加上 value（这里是 1）
             *  2. 如果 key 不存在则参数中传入的 k, v 将称为该 map 的新元素。
             *
             * 这里使用了比 Lamba 表达式更加简洁的方法引用 Integer::sum，表示两个整数之和。
             */
            hashMap.merge(
                    key, 1, Integer::sum
            );

            hashMap.forEach(
                    (k, v)
                            -> System.out.printf("[%s, %d]\n", k, v)
            );

        } while (true);

        scanner.close();
    }

    public static void
    basicFunctionalInterfaceUsage()
    {
        BasicFunctionalInterface.unaryOperatorUsage(
                "JESSE", String::toLowerCase
        );

        BasicFunctionalInterface.binaryOperatorUsage(
                "My name is: ", "Jesse.",
                String::concat
        );

        BasicFunctionalInterface.predicateUsage(
                List.of(1, 2, 3, 4, 5),
                Collection::isEmpty
        );

        BasicFunctionalInterface.supplierUsage(
                LocalDateTime::now
        );

        BasicFunctionalInterface.consumerUsage(
                String.valueOf(LocalDateTime.now()),
                System.out::println
        );

        BasicFunctionalInterface.functionUsage(
                Stream.of(1, 1, 2, 3, 4, 5, 6),
                (stream) ->
                        stream.map(String::valueOf)
                              .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public static void enhancedFunctionalInterface()
    {
        EnhancedFunctionalInterface.demonstrateUnaryOperation();

        EnhancedFunctionalInterface.demonstrateBinaryOperator();

        EnhancedFunctionalInterface.demonstratePredicate();

        EnhancedFunctionalInterface.demonstrateFunction();

        EnhancedFunctionalInterface.demonstrateSupplier();

        EnhancedFunctionalInterface.demonstrateConsumer();
    }

    public static void main(String[] args)
    {
        // methodReferenceUsage();

        doubleBinaryOperatorUsage();

        basicFunctionalInterfaceUsage();

        enhancedFunctionalInterface();
    }
}
