package com.kurtraschke.common;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Range;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.onebusaway.gtfs.model.calendar.ServiceDate;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.stream.Stream;

public class Dates {
    private Dates() {
    }

    public static Stream<LocalDate> iterateDates(final LocalDate start, final LocalDate end) {
        return ContiguousSet.create(Range.closed(start, end), Domains.localDate()).stream();
    }

    public static Stream<ServiceDate> iterateServiceDates(final ServiceDate start, final ServiceDate end) {
        return ContiguousSet.create(Range.closed(start, end), Domains.serviceDate()).stream();
    }

    @NotNull
    @Contract("_ -> new")
    public static ServiceDate makeServiceDate(final LocalDate ld) {
        return new ServiceDate(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
    }

    @NotNull
    @Contract("_ -> new")
    public static ServiceDate makeServiceDate(final XMLGregorianCalendar xgc) {
        return new ServiceDate(xgc.getYear(), xgc.getMonth(), xgc.getDay());
    }

    @NotNull
    @Contract("_ -> new")
    public static LocalDate makeLocalDate(final ServiceDate sd) {
        return LocalDate.of(sd.getYear(), sd.getMonth(), sd.getDay());
    }
}
