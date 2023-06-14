package com.kurtraschke.common;

import com.google.common.collect.DiscreteDomain;
import org.jetbrains.annotations.Contract;
import org.onebusaway.gtfs.model.calendar.ServiceDate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Domains {
    private Domains() {
    }

    @Contract(pure = true)
    public static DiscreteDomain<LocalDate> localDate() {
        return LocalDateDiscreteDomain.INSTANCE;
    }

    private static class LocalDateDiscreteDomain extends DiscreteDomain<LocalDate> {
        private static final LocalDateDiscreteDomain INSTANCE = new LocalDateDiscreteDomain();

        @Override
        public LocalDate next(final LocalDate value) {
            return value.plusDays(1);
        }

        @Override
        public LocalDate previous(final LocalDate value) {
            return value.minusDays(1);
        }

        @Override
        public long distance(final LocalDate start, final LocalDate end) {
            return ChronoUnit.DAYS.between(start, end);
        }

        @Override
        public LocalDate minValue() {
            return LocalDate.MIN;
        }

        @Override
        public LocalDate maxValue() {
            return LocalDate.MAX;
        }
    }

    @Contract(pure = true)
    public static DiscreteDomain<ServiceDate> serviceDate() {
        return ServiceDateDiscreteDomain.INSTANCE;
    }

    private static class ServiceDateDiscreteDomain extends DiscreteDomain<ServiceDate> {
        private static final ServiceDateDiscreteDomain INSTANCE = new ServiceDateDiscreteDomain();

        @Override
        public ServiceDate next(final ServiceDate value) {
            return value.next();
        }

        @Override
        public ServiceDate previous(final ServiceDate value) {
            return value.previous();
        }

        @Override
        public long distance(final ServiceDate start, final ServiceDate end) {
            return start.difference(end);
        }
    }
}
