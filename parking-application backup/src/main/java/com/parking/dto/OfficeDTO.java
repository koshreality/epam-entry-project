package com.parking.dto;

import com.parking.model.Office;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OfficeDTO {

    private Long id;

    private String title;

    public OfficeDTO(Office office) {
        this.id = office.getId();
        this.title = office.getTitle();
    }
}
