package io.miragon.layer.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreCheckData {

    private String firstname;

    private String lastname;

    private int age;

    private int income;
}