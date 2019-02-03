package com.kurtraschke.common;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import org.onebusaway.gtfs.model.calendar.ServiceDate;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {
    @Test
    void validateExtractionDestination() {
        final Path root = Paths.get("/some/where/");
        final Path safeDestination = Paths.get("directory/file.txt");
        final Path unsafeDestination = Paths.get("directory/../../file.txt");

        assertEquals(Paths.get("/some/where/directory/file.txt"), Utils.validateExtractionDestination(root, safeDestination));

        assertThrows(IllegalArgumentException.class, () -> {
            final Path destination = Utils.validateExtractionDestination(root, unsafeDestination);
        });
    }

    @Test
    void firstElementInRange() {
        final ServiceDate endpoint = new ServiceDate(1976, 3, 27);

        final Range<ServiceDate> unboundedRange = Range.all();
        final Range<ServiceDate> openRange = Range.downTo(endpoint, BoundType.OPEN);
        final Range<ServiceDate> closedRange = Range.downTo(endpoint, BoundType.CLOSED);

        assertThrows(IllegalArgumentException.class, () -> {
            final ServiceDate sd = Utils.firstElementInRange(unboundedRange, Domains.serviceDate());
        });

        assertEquals(endpoint.next(), Utils.firstElementInRange(openRange, Domains.serviceDate()));
        assertEquals(endpoint, Utils.firstElementInRange(closedRange, Domains.serviceDate()));
    }

    @Test
    void lastElementInRange() {
        final ServiceDate endpoint = new ServiceDate(2012, 9, 26);

        final Range<ServiceDate> unboundedRange = Range.all();
        final Range<ServiceDate> openRange = Range.upTo(endpoint, BoundType.OPEN);
        final Range<ServiceDate> closedRange = Range.upTo(endpoint, BoundType.CLOSED);

        assertThrows(IllegalArgumentException.class, () -> {
            final ServiceDate sd = Utils.lastElementInRange(unboundedRange, Domains.serviceDate());
        });

        assertEquals(endpoint.previous(), Utils.lastElementInRange(openRange, Domains.serviceDate()));
        assertEquals(endpoint, Utils.lastElementInRange(closedRange, Domains.serviceDate()));
    }
}