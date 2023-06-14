package com.kurtraschke.common;

import com.google.common.collect.BoundType;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Stream;

public class Utils {
    private Utils() {
    }

    @NotNull
    @Contract(pure = true)
    public static <T> Function<T, Boolean> pf(final Predicate<T> pred) {
        return pred::test;
    }

    @NotNull
    @Contract(pure = true)
    public static <T> Predicate<T> fp(final Function<T, Boolean> func) {
        return func::apply;
    }

    @NotNull
    @Contract(pure = true)
    public static FileFilter ff(final Predicate<File> fp) {
        return fp::test;
    }

    @NotNull
    @Contract(pure = true)
    public static <K, V, R> Function<Map.Entry<K, V>, R> ef(final BiFunction<K, V, R> func) {
        return e -> func.apply(e.getKey(), e.getValue());
    }

    @NotNull
    @Contract(pure = true)
    public static <K, V, R> Function<Pair<K, V>, R> pf(final BiFunction<K, V, R> func) {
        return e -> func.apply(e.getKey(), e.getValue());
    }

    @NotNull
    @Contract(pure = true)
    public static <K, V> Consumer<Map.Entry<K, V>> ec(final BiConsumer<K, V> func) {
        return e -> func.accept(e.getKey(), e.getValue());
    }

    @NotNull
    @Contract(pure = true)
    public static <K, V> Consumer<Pair<K, V>> pc(final BiConsumer<K, V> func) {
        return e -> func.accept(e.getKey(), e.getValue());
    }

    @Contract("null -> !null; !null -> !null")
    public static <T> Stream<T> arrayStream(@Nullable final T[] array) {
        return array == null ? Stream.empty() : Arrays.stream(array);
    }

    @NotNull
    public static Path validateExtractionDestination(final Path root, final Path toExtract) {
        //https://snyk.io/research/zip-slip-vulnerability#java

        final Path canonicalRoot = root.toAbsolutePath().normalize();

        final Path extractionDestination = canonicalRoot.resolve(toExtract).normalize();

        if (!extractionDestination.startsWith(canonicalRoot)) {
            throw new IllegalArgumentException(String.format("Attempted to extract file %s outside root %s", toExtract, root));
        }

        return extractionDestination;
    }

    public static <T extends Comparable<? super T>> T endpointFromRange(final Range<T> range,
                                                                        final DiscreteDomain<T> domain,
                                                                        final Predicate<Range<T>> hasBound,
                                                                        final Function<Range<T>, BoundType> boundTypeFunction,
                                                                        final Function<Range<T>, T> endpointGetter,
                                                                        final BiFunction<DiscreteDomain<T>, T, T> shifter) {

        if (hasBound.test(range)) {
            final T endpoint = endpointGetter.apply(range);
            return boundTypeFunction.apply(range) == BoundType.CLOSED ? endpoint : shifter.apply(domain, endpoint);
        } else {
            throw new IllegalArgumentException("Cannot obtain endpoint from unbounded range.");
        }
    }

    public static <T extends Comparable<? super T>> T firstElementInRange(final Range<T> range, final DiscreteDomain<T> domain) {
        return endpointFromRange(range, domain, Range::hasLowerBound, Range::lowerBoundType,
                Range::lowerEndpoint, DiscreteDomain::next);
    }

    public static <T extends Comparable<? super T>> T lastElementInRange(final Range<T> range, final DiscreteDomain<T> domain) {
        return endpointFromRange(range, domain, Range::hasUpperBound, Range::upperBoundType,
                Range::upperEndpoint, DiscreteDomain::previous);
    }
}
