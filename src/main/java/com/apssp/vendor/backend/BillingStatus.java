/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meyux
 */
@Entity
@Table(name = "BILLING_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BillingStatus.findAll", query = "SELECT b FROM BillingStatus b")
    , @NamedQuery(name = "BillingStatus.findByRefId", query = "SELECT b FROM BillingStatus b WHERE b.refId = :refId")
    , @NamedQuery(name = "BillingStatus.findByStatusId", query = "SELECT b FROM BillingStatus b WHERE b.statusId = :statusId")
    , @NamedQuery(name = "BillingStatus.findByStatusDate", query = "SELECT b FROM BillingStatus b WHERE b.statusDate = :statusDate")})
public class BillingStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "REF_ID")
    private String refId;
    @Column(name = "STATUS_ID")
    private Integer statusId;
    @Column(name = "STATUS_DATE")
    @Temporal(TemporalType.DATE)
    private Date statusDate;
    @JoinColumn(name = "REF_ID", referencedColumnName = "REF_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BillingMaster billingMaster;

    public BillingStatus() {
    }

    public BillingStatus(String refId) {
        this.refId = refId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public BillingMaster getBillingMaster() {
        return billingMaster;
    }

    public void setBillingMaster(BillingMaster billingMaster) {
        this.billingMaster = billingMaster;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (refId != null ? refId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillingStatus)) {
            return false;
        }
        BillingStatus other = (BillingStatus) object;
        if ((this.refId == null && other.refId != null) || (this.refId != null && !this.refId.equals(other.refId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apssp.vendor.backend.BillingStatus[ refId=" + refId + " ]";
    }
    
}
