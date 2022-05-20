package com.test.soap.controller;

import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
public class AppController {
	
	@Path("/")
	public void run() throws SOAPException, IOException {
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        
        SOAPEnvelope envelope = soapPart.getEnvelope();
        
        SOAPBody soapBody = envelope.getBody();
        SOAPElement numberToDollars = soapBody.addChildElement("NumberToDollars", "", "http://www.dataaccess.com/webservicesserver/");
        SOAPElement dNum = numberToDollars.addChildElement("dNum").addTextNode("1.8");
        
        //soapMessage.writeTo(System.out);
        
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        SOAPMessage soapResponse = soapConnection.call(soapMessage, "https://www.dataaccess.com/webservicesserver/NumberConversion.wso");
        
        //soapResponse.writeTo(System.out);
        
        NodeList nodes = soapResponse.getSOAPBody().getElementsByTagName("m:NumberToDollarsResult");
        Node node = nodes.item(0);
        System.out.println(node.getTextContent());
        
        soapConnection.close();
        
	}

}
