/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.entities;

import com.apssp.vendor.backend.entities.BillingMaster;
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
@Table(name = "BILLING_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BillingDetail.findAll", query = "SELECT b FROM BillingDetail b")
    , @NamedQuery(name = "BillingDetail.findByRefId", query = "SELECT b FROM BillingDetail b WHERE b.refId = :refId")
    , @NamedQuery(name = "BillingDetail.findByDocType", query = "SELECT b FROM BillingDetail b WHERE b.docType = :docType")
    , @NamedQuery(name = "BillingDetail.findByPages", query = "SELECT b FROM BillingDetail b WHERE b.pages = :pages")
    , @NamedQuery(name = "BillingDetail.findByDocNo", query = "SELECT b FROM BillingDetail b WHERE b.docNo = :docNo")
    , @NamedQuery(name = "BillingDetail.findByDocDate", query = "SELECT b FROM BillingDetail b WHERE b.docDate = :docDate")
    , @NamedQuery(name = "BillingDetail.findByFilename", query = "SELECT b FROM BillingDetail b WHERE b.filename = :filename")
    , @NamedQuery(name = "BillingDetail.findByDocrefId", query = "SELECT b FROM BillingDetail b WHERE b.docrefId = :docrefId")
    , @NamedQuery(name = "BillingDetail.findByUploadBy", query = "SELECT b FROM BillingDetail b WHERE b.uploadBy = :uploadBy")
    , @NamedQuery(name = "BillingDetail.findByUploadDate", query = "SELECT b FROM BillingDetail b WHERE b.uploadDate = :uploadDate")})
public class BillingDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "REF_ID")
    private String refId;
    @Column(name = "PAGES")
    private Integer pages;
    @Size(max = 25)
    @Column(name = "DOC_NO")
    private String docNo;
    @Column(name = "DOC_DATE")
    @Temporal(TemporalType.DATE)
    private Date docDate;
    @Size(max = 50)
    @Column(name = "FILENAME")
    private String filename;
    @Size(max = 12)
    @Column(name = "DOCREF_ID")
    private String docrefId;
    @Size(max = 25)
    @Column(name = "UPLOAD_BY")
    private String uploadBy;
    @Column(name = "UPLOAD_DATE")
    @Temporal(TemporalType.DATE)
    private Date uploadDate;
    @Size(max = 30)
    @Column(name = "DOC_TYPE")
    private String docType;
    @JoinColumn(name = "REF_ID", referencedColumnName = "REF_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BillingMaster billingMaster;

    public BillingDetail() {
    }

    public BillingDetail(String refId) {
        this.refId = refId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDocrefId() {
        return docrefId;
    }

    public void setDocrefId(String docrefId) {
        this.docrefId = docrefId;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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
        if (!(object instanceof BillingDetail)) {
            return false;
        }
        BillingDetail other = (BillingDetail) object;
        if ((this.refId == null && other.refId != null) || (this.refId != null && !this.refId.equals(other.refId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apssp.vendor.backend.BillingDetail[ refId=" + refId + " ]";
    }
    
}
