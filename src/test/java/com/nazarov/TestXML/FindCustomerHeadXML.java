package com.nazarov.TestXML;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://schemas.xmlsoap.org/soap/envelope")  // Указание на пространство имен
public class FindCustomerHeadXML {

    private String authToken;

    public FindCustomerHeadXML(String authToken) {
        this.authToken = authToken;
    }

    public FindCustomerHeadXML() {
    }

    @XmlElement(name = "authToken", namespace = "soap")
    public String getAuthToken() {
        return authToken;
    }
}
