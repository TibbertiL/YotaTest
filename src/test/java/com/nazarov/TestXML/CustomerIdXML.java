package com.nazarov.TestXML;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://schemas.xmlsoap.org/soap/envelope")
public class CustomerIdXML {

    private String customerId;

    public CustomerIdXML(String customerId) {
        this.customerId = customerId;
    }

    public CustomerIdXML() {
    }

    @XmlElement(name = "customerId", namespace = "soap")
    public String getCustomerId() {
        return customerId;
    }
}
