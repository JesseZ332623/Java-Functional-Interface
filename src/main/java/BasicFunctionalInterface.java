import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * JDK 中 java.base.util.function 包下提供了 43 个预定义的函数式接口，
 * 但显然不需要记住那么多，只需要掌握其中核心的 6 个。
 */
public class BasicFunctionalInterface
{
    /**
     * UnaryOperator{@literal <T>}
     * 表示对 T 的实例执行一个操作，返回一个新的 T 的实例。
     */
    public static void unaryOperatorUsage(
            String str,
            UnaryOperator<String> unaryOperator)
    {
        System.out.printf(
                "Call unaryOperatorUsage(%s, %s)\n",
                str, "operator(str) -> String"
        );

        System.out.println("Old String: " + str);

        String newStr = unaryOperator.apply(str);

        System.out.println("New String: " + newStr);

        System.out.println();
    }

    /**
     * BinaryOperator{@literal <T>}
     * 表示对 T 的实例 t1, t2 执行一个操作，返回一个新的 T 的实例。
     */
    public static void binaryOperatorUsage(
            String str_1, String str_2,
            BinaryOperator<String> binaryOperator
    )
    {
        System.out.printf(
                "Call unaryOperatorUsage(%s, %s, %s)\n",
                str_1, str_2, "operator(str_1, str_2) -> String"
        );

        System.out.printf("Old String: 1: %s, 2: %s\n", str_1, str_2);

        System.out.println(
                "New string: " + binaryOperator.apply(str_1, str_2)
        );

        System.out.println();
    }

    /**
     * Predicate{@literal <T>} 表示对 T 的实例执行一个判断实例状态的操作，
     * 返回一个 boolean 类型。
     * <p>
     * Predicate 指的是谓词。
     */
    public static void predicateUsage(
            Collection<Integer>            collection,
            Predicate<Collection<Integer>> predicate
    )
    {
        System.out.println(
                "Call predicateUsage(collection, " +
                "predicate(collection) -> boolean)"
        );

        System.out.println(predicate.test(collection));

        System.out.println();
    }

    /**
     * Function{@literal <T, R>}
     * 表示对 T 的实例执行一个操作，返回一个 R 的实例，
     * 常见用法是把一个容器或流转化成另一个容器或相反的操作。
     */
    public static void functionUsage(
            Stream<Integer> integerStream,
            @NotNull
            Function<Stream<Integer>, List<String>> function
    )
    {
        System.out.println(
                """
                Call functionUsage(
                        integerStream,
                        function(integerStream) -> List<String>
                    )
                """
        );

        List<String> list = function.apply(integerStream);

        list.forEach((n) -> System.out.print(n + " "));

        System.out.println("\n");
    }

    /**
     * Supplier{@literal <T>} （生产者）
     * 指的是一类能提供一个 T 的实例的函数。
     */
    public static void
    supplierUsage(Supplier<LocalDateTime> supplier)
    {
        System.out.println(
                "Call supplierUsage(supplier() -> LocalDateTime)"
        );

        System.out.println(supplier.get().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        System.out.println();
    }

    /**
     * Consumer{@literal <T>} （生产者）
     * 指的是一类能消费一个 T 的实例的函数。
     * <p>
     * 常见的就是用 System.out.print() 输出到文件。
     */
    public static void
    consumerUsage(String result, Consumer<String> consumer)
    {
        System.out.println("Call consumerUsage(result, consumer(result))");

        consumer.accept(result);

        System.out.println();
    }
}
