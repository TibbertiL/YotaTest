package com.nazarov;

import java.util.Map;

public class Customer {
    private String name;
    private Long phone;
    private Map<String, String> additionalParameters;

    public Customer(String name, Long phone, Map<String, String> additionalParameters) {
        this.name = name;
        this.phone = phone;
        this.additionalParameters = additionalParameters;
    }

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public Long getPhone() {
        return phone;
    }

    public Map<String, String> getAdditionalParameters() {
        return additionalParameters;
    }
}
