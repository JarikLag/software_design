package ru.dorokhov.sd.refactoring.functional;

import java.util.function.Function;

public final class FunctionalUtils {
    public static <T, R> Function<T, R> throwingFunctionWrapper(final ThrowingFunction<T, R, Exception> throwingFunction) {
        return i -> {
            try {
                return throwingFunction.apply(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
