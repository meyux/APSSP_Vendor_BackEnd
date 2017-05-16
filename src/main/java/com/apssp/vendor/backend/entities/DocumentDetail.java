/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meyux
 */
@Entity
@Table(name = "DOCUMENT_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentDetail.findAll", query = "SELECT d FROM DocumentDetail d")
    , @NamedQuery(name = "DocumentDetail.findByDocumentId", query = "SELECT d FROM DocumentDetail d WHERE d.documentId = :documentId")
    , @NamedQuery(name = "DocumentDetail.findByDocNo", query = "SELECT d FROM DocumentDetail d WHERE d.docNo = :docNo")
    , @NamedQuery(name = "DocumentDetail.findByDocPage", query = "SELECT d FROM DocumentDetail d WHERE d.docPage = :docPage")})
public class DocumentDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DOCUMENT_ID")
    private Integer documentId;
    @Size(max = 25)
    @Column(name = "DOC_NO")
    private String docNo;
    @Column(name = "DOC_PAGE")
    private Integer docPage;
    @Lob
    @Column(name = "DOC_IMAGE")
    private byte[] docImage;
    @JoinColumn(name = "REF_ID", referencedColumnName = "REF_ID")
    @ManyToOne
    private BillingMaster refId;

    public DocumentDetail() {
    }

    public DocumentDetail(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public Integer getDocPage() {
        return docPage;
    }

    public void setDocPage(Integer docPage) {
        this.docPage = docPage;
    }

    public byte[] getDocImage() {
        return docImage;
    }

    public void setDocImage(byte[] docImage) {
        this.docImage = docImage;
    }

    public BillingMaster getRefId() {
        return refId;
    }

    public void setRefId(BillingMaster refId) {
        this.refId = refId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentId != null ? documentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentDetail)) {
            return false;
        }
        DocumentDetail other = (DocumentDetail) object;
        if ((this.documentId == null && other.documentId != null) || (this.documentId != null && !this.documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apssp.vendor.backend.entities.DocumentDetail[ documentId=" + documentId + " ]";
    }
    
}
