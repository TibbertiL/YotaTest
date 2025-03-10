package com.nazarov.TestXML;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"header", "body"})
@XmlRootElement(namespace = "http://schemas.xmlsoap.org/soap/Envelope")
public class Envelope {

    private FindCustomerHeadXML header;
    private FindCustomerBodyXML body;

    public Envelope(FindCustomerHeadXML header, FindCustomerBodyXML body) {
        this.header = header;
        this.body = body;
    }

    public Envelope() {
    }

    @XmlElement(name = "Header", namespace = "http://schemas.xmlsoap.org/soap/envelope")
    public FindCustomerHeadXML getHeader() {
        return header;
    }


    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope")
    public FindCustomerBodyXML getBody() {
        return body;
    }
}
