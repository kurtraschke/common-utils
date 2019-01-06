package com.kurtraschke.common;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LineString;
import org.geojson.LngLatAlt;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collector;

public class Collectors {
    private Collectors() {
    }

    @NotNull
    public static Collector<Feature, ?, FeatureCollection> toFeatureCollection() {
        return Collector.of(FeatureCollection::new,
                FeatureCollection::add,
                (left, right) -> {
                    left.addAll(right.getFeatures());
                    return left;
                });
    }

    @NotNull
    public static Collector<LngLatAlt, ?, LineString> toLineString() {
        return Collector.of(LineString::new,
                LineString::add,
                (left, right) -> {
                    right.getCoordinates().forEach(left::add);
                    return left;
                });
    }
}
