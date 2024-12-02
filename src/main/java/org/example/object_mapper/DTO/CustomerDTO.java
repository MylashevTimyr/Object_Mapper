package org.example.object_mapper.DTO;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
}
