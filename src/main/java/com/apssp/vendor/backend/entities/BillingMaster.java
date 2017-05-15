/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.entities;

import com.apssp.vendor.backend.entities.BillingStatus;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "BILLING_MASTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BillingMaster.findAll", query = "SELECT b FROM BillingMaster b")
    , @NamedQuery(name = "BillingMaster.findByRefId", query = "SELECT b FROM BillingMaster b WHERE b.refId = :refId")
    , @NamedQuery(name = "BillingMaster.findByCompanyId", query = "SELECT b FROM BillingMaster b WHERE b.companyId = :companyId")
    , @NamedQuery(name = "BillingMaster.findByVendorId", query = "SELECT b FROM BillingMaster b WHERE b.vendorId = :vendorId")
    , @NamedQuery(name = "BillingMaster.findByInvoiceNo", query = "SELECT b FROM BillingMaster b WHERE b.invoiceNo = :invoiceNo")
    , @NamedQuery(name = "BillingMaster.findByInvoiceDate", query = "SELECT b FROM BillingMaster b WHERE b.invoiceDate = :invoiceDate")
    , @NamedQuery(name = "BillingMaster.findByInvoiceAmount", query = "SELECT b FROM BillingMaster b WHERE b.invoiceAmount = :invoiceAmount")})
public class BillingMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "REF_ID")
    private String refId;
    @Size(max = 4)
    @Column(name = "COMPANY_ID")
    private String companyId;
    @Column(name = "VENDOR_ID")
    private Integer vendorId;
    @Size(max = 25)
    @Column(name = "INVOICE_NO")
    private String invoiceNo;
    @Column(name = "INVOICE_DATE")
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;
    @Column(name = "INVOICE_AMOUNT")
    private Integer invoiceAmount;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "billingMaster")
    private BillingStatus billingStatus;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "billingMaster")
    private BillingDetail billingDetail;

    public BillingMaster() {
    }

    public BillingMaster(String refId) {
        this.refId = refId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Integer invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BillingStatus getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(BillingStatus billingStatus) {
        this.billingStatus = billingStatus;
    }

    public BillingDetail getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(BillingDetail billingDetail) {
        this.billingDetail = billingDetail;
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
        if (!(object instanceof BillingMaster)) {
            return false;
        }
        BillingMaster other = (BillingMaster) object;
        if ((this.refId == null && other.refId != null) || (this.refId != null && !this.refId.equals(other.refId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apssp.vendor.backend.BillingMaster[ refId=" + refId + " ]";
    }
    
}
