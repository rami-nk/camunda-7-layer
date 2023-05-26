package io.miragon.layer.service.bookavailablity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckBookAvailabilityService {

    public BookAvailabilityResult checkAvailability(BookAvailabilityData bookAvailabilityData) {
        log.info("Checking book availability for {} {} with isbn {}",
                bookAvailabilityData.getTitle(),
                bookAvailabilityData.getAuthor(),
                bookAvailabilityData.getIsbn()
        );
        return new BookAvailabilityResult(true);
    }
}