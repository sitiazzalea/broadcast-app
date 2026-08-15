package com.broadcast.service;

import com.broadcast.model.AgeCategory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AgeCategoryResolver {
    public static class DateRange {
        private final LocalDate startDate;
        private final LocalDate endDate;

        public DateRange(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }
    }

    public DateRange resolve(AgeCategory category) {
        switch (category) {
            case BOOMER:
                return new DateRange(
                        LocalDate.of(1946, 1, 1),
                        LocalDate.of(1965, 1, 1)
                );

            case GEN_X:
                return new DateRange(
                        LocalDate.of(1965, 1, 1),
                        LocalDate.of(1981, 1, 1)
                );

            case GEN_Y:
                return new DateRange(
                        LocalDate.of(1981, 1, 1),
                        LocalDate.of(1997, 1, 1)
                );

            case GEN_Z:
                return new DateRange(
                        LocalDate.of(1997, 1, 1),
                        LocalDate.of(2013, 1, 1)
                );

            case GEN_ALPHA:
                return new DateRange(
                        LocalDate.of(2013, 1, 1),
                        LocalDate.now().plusDays(1)
                );

            default:
                throw new IllegalArgumentException(
                        "Unsupported age category: " + category
                );
        }
    }
}
