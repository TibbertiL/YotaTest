package com.nazarov.TestXML;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://schemas.xmlsoap.org/soap/envelope")  // Указание на пространство имен
public class FindCustomerBodyXML {

    private Long phoneNumber;

    public FindCustomerBodyXML(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public FindCustomerBodyXML() {
    }

    @XmlElement(name = "phoneNumber", namespace = "soap")  // Указывает имя элемента в XML для этого поля.
    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
