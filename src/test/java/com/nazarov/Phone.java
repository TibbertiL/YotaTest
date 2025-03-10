package com.nazarov;

public class Phone {
    private Long phone;
    private String locale;

    public Phone(Long phone, String locale) {
        this.phone = phone;
        this.locale = locale;
    }

    public Phone() {
    }

    public Long getPhone() {
        return phone;
    }

    public String getLocale() {
        return locale;
    }
}
