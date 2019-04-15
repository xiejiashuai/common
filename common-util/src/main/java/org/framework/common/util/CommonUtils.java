import com.aihuishou.common.exception.ServiceException;
import com.aihuishou.common.response.ResponseCode;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 通用工具类，提供一些通用方法
 *
 * @author jiashuai.xie
 * @since 2019/4/3 17:08 3.0.9.RELEASE
 */
public abstract class CommonUtils {

    /**
     * 获取对象
     *
     * @param supplier 对象生产者
     * @param <T>      返回值对象类型
     * @return supplier.get()
     * @throws {@link ServiceException} if supplier.get() == null
     */
    public static <T> T supplyIfNotExistThrowException(Supplier<T> supplier) {

        return Optional.ofNullable(supplier.get()).orElseThrow(() -> new ServiceException(ResponseCode.Entity_NOT_EXIST));

    }

    /**
     * 如果给定的处理对象 t ,符合断言predicate逻辑，则执行consumer消费逻辑
     *
     * @param t         处理对象
     * @param predicate 是否匹配逻辑
     * @param consumer  消费处理逻辑
     * @param <T>       处理对象类型
     */
    public static <T> void consumerIfPredicate(T t, Predicate<T> predicate, Consumer<T> consumer) {
        if (predicate.test(t)) {
            consumer.accept(t);
        }
    }

    /**
     * 不匹配预期值，抛出异常
     *
     * @param actualValue    真实值
     * @param exceptedValues 预期值
     */
    @SafeVarargs
    public static <T extends Number> void checkIfNotMatchThrowException(T actualValue, T... exceptedValues) {
        Assert.isTrue(Arrays.asList(exceptedValues).contains(actualValue), "exceptedValues:" + String.valueOf(exceptedValues) + ",but:" + String.valueOf(actualValue));
    }

    /**
     * 匹配非预期值，抛出异常
     *
     * @param actualValue       真实值
     * @param notExceptedValues 非预期值
     */
    @SafeVarargs
    public static <T extends Number> void checkIfMatchThrowException(T actualValue, T... notExceptedValues) {
        Assert.isTrue(!Arrays.asList(notExceptedValues).contains(actualValue), "notExceptedValues:" + notExceptedValues + ",actualValue:" + actualValue);
    }

    /**
     * 数组对象转集合
     *
     * @param t   目标对象
     * @param <T> 目标对象类型
     * @return 集合数据
     */
    @SafeVarargs
    public static <T> Collection<T> of(T... t) {
        return Arrays.asList(t);
    }

    public static <T extends Number> T supply(Supplier<T> supplier, Supplier<T> defaultSupplier) {
        return Optional.ofNullable(supplier.get()).orElseGet(defaultSupplier);
    }

    public static Integer plus(Supplier<Integer> oneSupply, Supplier<Integer> anotherSupplier) {
        return supply(oneSupply, () -> 0) + supply(anotherSupplier, () -> 0);
    }

    public static Integer subWithAbs(Integer one, Integer two) {
        return Math.abs(Optional.ofNullable(one).orElse(0) - Optional.ofNullable(two).orElse(0));

    }

}
