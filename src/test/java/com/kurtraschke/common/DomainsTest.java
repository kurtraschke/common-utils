package com.kurtraschke.common;

import com.google.common.collect.DiscreteDomain;
import org.junit.jupiter.api.Test;
import org.onebusaway.gtfs.model.calendar.ServiceDate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainsTest {
    @Test
    void localDate() {
        final DiscreteDomain<LocalDate> domain = Domains.localDate();

        final LocalDate date = LocalDate.of(1904, 10, 27);

        assertEquals(LocalDate.of(1904, 10, 28), domain.next(date));
        assertEquals(LocalDate.of(1904, 10, 26), domain.previous(date));

        assertEquals(2, domain.distance(date, domain.next(domain.next(date))));

        assertEquals(LocalDate.MIN, domain.minValue());
        assertEquals(LocalDate.MAX, domain.maxValue());
    }

    @Test
    void serviceDate() {
        final DiscreteDomain<ServiceDate> domain = Domains.serviceDate();

        final ServiceDate date = new ServiceDate(1863, 1, 10);

        assertEquals(new ServiceDate(1863, 1, 11), domain.next(date));
        assertEquals(new ServiceDate(1863, 1, 9), domain.previous(date)); //The Day Before (You know what they’ll call it, they’ll call it the Tube)

        assertEquals(2, domain.distance(date, domain.next(domain.next(date))));
    }
}
