package io.miragon.layer.service.bookavailablity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookAvailabilityData {

    private String title;

    private String author;

    private String isbn;
}