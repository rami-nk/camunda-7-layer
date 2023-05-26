package io.miragon.layer.api.customermessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerMessageRequestDto {

    private String firstname;

    private String lastname;

    private String address;
}
