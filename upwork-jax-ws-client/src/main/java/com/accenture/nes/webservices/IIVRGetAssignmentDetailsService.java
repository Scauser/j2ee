
package com.accenture.nes.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import com.accenture.nes.dto.webservicedto.ivr.GetAssignmentDetailsRequestDTO;
import com.accenture.nes.dto.webservicedto.ivr.GetAssignmentDetailsResponseDTO;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "IIVRGetAssignmentDetailsService", targetNamespace = "http://webservices.nes.accenture.com/")
@XmlSeeAlso({
    com.accenture.nes.webservices.ObjectFactory.class,
    com.accenture.nes.util.ObjectFactory.class,
    com.accenture.nes.dto.webservicedto.ivr.ObjectFactory.class
})
public interface IIVRGetAssignmentDetailsService {


    /**
     * 
     * @param getAssignmentDetailsRequestDTO
     * @return
     *     returns com.accenture.nes.dto.webservicedto.ivr.GetAssignmentDetailsResponseDTO
     * @throws EntityException_Exception
     */
    @WebMethod(action = "urn:getAssignmentDetails")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAssignmentDetails", targetNamespace = "http://webservices.nes.accenture.com/", className = "com.accenture.nes.webservices.GetAssignmentDetails")
    @ResponseWrapper(localName = "getAssignmentDetailsResponse", targetNamespace = "http://webservices.nes.accenture.com/", className = "com.accenture.nes.webservices.GetAssignmentDetailsResponse")
    public GetAssignmentDetailsResponseDTO getAssignmentDetails(
        @WebParam(name = "getAssignmentDetailsRequestDTO", targetNamespace = "")
        GetAssignmentDetailsRequestDTO getAssignmentDetailsRequestDTO)
        throws EntityException_Exception
    ;

}
