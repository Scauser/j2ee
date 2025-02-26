
package com.accenture.nes.webservices;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "EntityException", targetNamespace = "http://webservices.nes.accenture.com/")
public class EntityException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private EntityException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public EntityException_Exception(String message, EntityException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public EntityException_Exception(String message, EntityException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.accenture.nes.webservices.EntityException
     */
    public EntityException getFaultInfo() {
        return faultInfo;
    }

}
