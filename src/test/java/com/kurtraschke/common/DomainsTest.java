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

        assertEquals(domain.next(date), LocalDate.of(1904, 10, 28));
        assertEquals(domain.previous(date), LocalDate.of(1904, 10, 26));

        assertEquals(domain.distance(date, domain.next(domain.next(date))), 2);

        assertEquals(domain.minValue(), LocalDate.MIN);
        assertEquals(domain.maxValue(), LocalDate.MAX);
    }

    @Test
    void serviceDate() {
        final DiscreteDomain<ServiceDate> domain = Domains.serviceDate();

        final ServiceDate date = new ServiceDate(1863, 1, 10);

        assertEquals(domain.next(date), new ServiceDate(1863, 1, 11));
        assertEquals(domain.previous(date), new ServiceDate(1863, 1, 9)); //The Day Before (You know what they’ll call it, they’ll call it the Tube)

        assertEquals(domain.distance(date, domain.next(domain.next(date))), 2);
    }
}
