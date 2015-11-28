/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.klaw.leafhouse.db.dto.SecurityState;
import org.klaw.leafhouse.security.SecurityThreshhold.SecurityRisk;

/**
 *
 * @author Klaw Strife
 */
@XmlRootElement
public class SecurityInformation {
    private SecurityRisk securityRisk;
    private SecurityState securityState;
    private List<Link> links;

    public SecurityInformation() {
    }

    public SecurityInformation(SecurityRisk securityRisk, SecurityState securityState, List<Link> links) {
        this.securityRisk = securityRisk;
        this.securityState = securityState;
        this.links = links;
    }

    public SecurityRisk getSecurityRisk() {
        return securityRisk;
    }

    public void setSecurityRisk(SecurityRisk securityRisk) {
        this.securityRisk = securityRisk;
    }

    public SecurityState getSecurityState() {
        return securityState;
    }

    public void setSecurityState(SecurityState securityState) {
        this.securityState = securityState;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
