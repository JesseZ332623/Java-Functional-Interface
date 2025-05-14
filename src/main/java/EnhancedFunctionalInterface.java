import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class EnhancedFunctionalInterface
{
    private static final List<Integer> NUMBER_LIST
            = List.of(1, 2, 3 , 6, 245, 425, 45, 44, 98);

    private static final String SAMPLE_TEXT
            = "Welcome_To_Functional_Programing!";

    /**
     * UnaryOperation{@literal <T>} 更复杂操作的演示 (demonstrate)。
     */
    public static void demonstrateUnaryOperation()
    {
        // 去除下划线的操作。
        UnaryOperator<String> removeUnderLine
                = (s) -> s.replace('_', ' ');

        UnaryOperator<String> toLowerCaseFromFirstWords
                = (s) -> {
                String[] splitRes = s.split(" ");
                var builder = new StringBuilder().append(splitRes[0]);

                for (var n : splitRes)
                {
                    if (n.contains("Welcome")) { continue; }
                    builder.append(
                            n.replace(
                                    n.charAt(0),
                                    Character.toLowerCase(n.charAt(0))
                            )
                    ).append(" ");
                }

                return builder.toString();
            };

        /*
         * 不理解，明明 UnaryOperator<T> 是由 Function<T, R> 派生而来，
         * 但是编译器没能完成类型推断，所以我只能手动组合了（悲）。
        */
//      UnaryOperator<String> combinationOp
//                = toLowerCaseFromFirstWords.andThen(removeUnderLine);

        BasicFunctionalInterface.unaryOperatorUsage(
                SAMPLE_TEXT,
                (s) -> {
                    s = removeUnderLine.apply(s);
                    s = toLowerCaseFromFirstWords.apply(s);

                    return s;
                }
        );
    }

    /**
     * BinaryOperator{@literal <T>} 更复杂操作的演示。
     */
    public static void demonstrateBinaryOperator()
    {
        // 根据字符串的长度进行比较的操作
        BinaryOperator<String> checkLongerString
                = BinaryOperator.maxBy(Comparator.comparing(String::length));

        /* 按照 checkLongerString 的规则对两个字符串进行比较。*/
        BasicFunctionalInterface.binaryOperatorUsage(
                "114514", "1919810",
                checkLongerString
        );
    }

    /**
     * Predicate{@literal <T>} 更复杂操作的演示。
     */
    public static void demonstratePredicate()
    {
        // 操作 1：是否为非空容器？
        Predicate<Collection<Integer>> notEmpty
                = (collection) -> !collection.isEmpty();

        /*
        * 操作 2：是否全为偶数？
        * Stream<> 下有 anyMatch() 和 allMatch() 两个方法，可别搞混了。
        */
        Predicate<Collection<Integer>> isAllEvan
                = collection ->
                                        collection.stream().allMatch(
                                                (n) -> n % 2 == 0
                                        );

        // 对列表 NUMBER_LIST，执行组合了 操作 1 和 2 的操作。
        BasicFunctionalInterface.predicateUsage(
                NUMBER_LIST, notEmpty.and(isAllEvan)
        );
    }

    /**
     * Function{@literal <T, R>} 更复杂操作的演示。
     */
    public static void demonstrateFunction()
    {
        // 操作 1：将整数转化成 16 进制字符串
        Function<Integer, String> toHexString = Integer::toHexString;

        // 操作 2：为某字符串添加 0x 前缀
        Function<String, String>  addPrefix   = (s) -> "0x" + s;

        /*
         *  对 NUMBER_LIST 流执行如下操作：
         *      对流中的每一个整数执行组合了 操作 1 和 2 的操作，
         *      再将其收集成一个新的 ArrayList。
         */
        BasicFunctionalInterface.functionUsage(
            NUMBER_LIST.stream(),
           (stream) ->
                   stream.map((n) -> toHexString.andThen(addPrefix).apply(n))
                         .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    /**
     * Supplier{@literal <T>} 更复杂操作的演示。
     */
    public static void
    demonstrateSupplier()
    {
        Supplier<LocalDateTime> dateTimeSupplier = LocalDateTime::now;

        BasicFunctionalInterface.supplierUsage(dateTimeSupplier);
    }

    /**
     * Consumer{@literal <T>} 更复杂操作的演示。
     */
    public static void
    demonstrateConsumer()
    {
        // 操作 1：将字符串写入文件
        Consumer<String> writeToFile = (s) ->
        {
            try
            {
                Path path = Paths.get("./info.log");

                if (!Files.exists(path)) {
                    Files.createFile(path);
                }

                Files.writeString(
                        path, s,
                        StandardCharsets.UTF_8, StandardOpenOption.APPEND
                );
            }
            catch (IOException e)
            {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);
            }
        };

        // 操作 2：将字符串写入日志
        Consumer<String> writeToLog
                = (s) -> System.out.println("LOG: " + s);

        /*
        * 组合操作 1 和 2，共同消费字符串。
        */
        BasicFunctionalInterface.consumerUsage(
                "Today is: " +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        + "\n",
                writeToFile.andThen(writeToLog)
        );
    }
}
