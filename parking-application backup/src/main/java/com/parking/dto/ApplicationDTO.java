package com.parking.dto;

import com.parking.model.Application;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ApplicationDTO {

    private Long id;

    private String officeTitle;

    private String username;

    private Application.Status status;

    private String updatedBy;

    public ApplicationDTO(Application application){
        this.id = application.getId();
        this.officeTitle = application.getOffice().getTitle();
        this.username = application.getUser().getUsername();
        this.status = application.getStatus();
        this.updatedBy = application.getUpdatedBy().getUsername();
    }
}
