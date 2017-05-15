/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.entities;

import com.apssp.vendor.backend.entities.VendorLogin;
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
@Table(name = "VENDOR_MASTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VendorMaster.findAll", query = "SELECT v FROM VendorMaster v")
    , @NamedQuery(name = "VendorMaster.findByVendorId", query = "SELECT v FROM VendorMaster v WHERE v.vendorId = :vendorId")
    , @NamedQuery(name = "VendorMaster.findByVendorNo", query = "SELECT v FROM VendorMaster v WHERE v.vendorNo = :vendorNo")
    , @NamedQuery(name = "VendorMaster.findByVendorName", query = "SELECT v FROM VendorMaster v WHERE v.vendorName = :vendorName")
    , @NamedQuery(name = "VendorMaster.findByTin", query = "SELECT v FROM VendorMaster v WHERE v.tin = :tin")
    , @NamedQuery(name = "VendorMaster.findByVatRegistered", query = "SELECT v FROM VendorMaster v WHERE v.vatRegistered = :vatRegistered")
    , @NamedQuery(name = "VendorMaster.findByAddress1", query = "SELECT v FROM VendorMaster v WHERE v.address1 = :address1")
    , @NamedQuery(name = "VendorMaster.findByAddress2", query = "SELECT v FROM VendorMaster v WHERE v.address2 = :address2")
    , @NamedQuery(name = "VendorMaster.findByAddress3", query = "SELECT v FROM VendorMaster v WHERE v.address3 = :address3")
    , @NamedQuery(name = "VendorMaster.findByAddress4", query = "SELECT v FROM VendorMaster v WHERE v.address4 = :address4")
    , @NamedQuery(name = "VendorMaster.findByCountryCode", query = "SELECT v FROM VendorMaster v WHERE v.countryCode = :countryCode")
    , @NamedQuery(name = "VendorMaster.findByVendorSiteCode", query = "SELECT v FROM VendorMaster v WHERE v.vendorSiteCode = :vendorSiteCode")
    , @NamedQuery(name = "VendorMaster.findByVendorSiteId", query = "SELECT v FROM VendorMaster v WHERE v.vendorSiteId = :vendorSiteId")
    , @NamedQuery(name = "VendorMaster.findByTermsName", query = "SELECT v FROM VendorMaster v WHERE v.termsName = :termsName")
    , @NamedQuery(name = "VendorMaster.findByTerms", query = "SELECT v FROM VendorMaster v WHERE v.terms = :terms")
    , @NamedQuery(name = "VendorMaster.findByAwtGroup", query = "SELECT v FROM VendorMaster v WHERE v.awtGroup = :awtGroup")
    , @NamedQuery(name = "VendorMaster.findByStatus", query = "SELECT v FROM VendorMaster v WHERE v.status = :status")
    , @NamedQuery(name = "VendorMaster.findByStatusDate", query = "SELECT v FROM VendorMaster v WHERE v.statusDate = :statusDate")})
public class VendorMaster implements Serializable {

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vendorMaster")
    private VendorLogin vendorLogin;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VENDOR_ID")
    private Integer vendorId;
    @Column(name = "VENDOR_NO")
    private Integer vendorNo;
    @Size(max = 255)
    @Column(name = "VENDOR_NAME")
    private String vendorName;
    @Size(max = 25)
    @Column(name = "TIN")
    private String tin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VAT_REGISTERED")
    private int vatRegistered;
    @Size(max = 255)
    @Column(name = "ADDRESS1")
    private String address1;
    @Size(max = 255)
    @Column(name = "ADDRESS2")
    private String address2;
    @Size(max = 255)
    @Column(name = "ADDRESS3")
    private String address3;
    @Size(max = 255)
    @Column(name = "ADDRESS4")
    private String address4;
    @Column(name = "COUNTRY_CODE")
    private Integer countryCode;
    @Column(name = "VENDOR_SITE_CODE")
    private Integer vendorSiteCode;
    @Column(name = "VENDOR_SITE_ID")
    private Integer vendorSiteId;
    @Size(max = 50)
    @Column(name = "TERMS_NAME")
    private String termsName;
    @Column(name = "TERMS")
    private Integer terms;
    @Size(max = 15)
    @Column(name = "AWT_GROUP")
    private String awtGroup;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "STATUS_DATE")
    @Temporal(TemporalType.DATE)
    private Date statusDate;

    public VendorMaster() {
    }

    public VendorMaster(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public VendorMaster(Integer vendorId, int vatRegistered) {
        this.vendorId = vendorId;
        this.vatRegistered = vatRegistered;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(Integer vendorNo) {
        this.vendorNo = vendorNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public int getVatRegistered() {
        return vatRegistered;
    }

    public void setVatRegistered(int vatRegistered) {
        this.vatRegistered = vatRegistered;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getVendorSiteCode() {
        return vendorSiteCode;
    }

    public void setVendorSiteCode(Integer vendorSiteCode) {
        this.vendorSiteCode = vendorSiteCode;
    }

    public Integer getVendorSiteId() {
        return vendorSiteId;
    }

    public void setVendorSiteId(Integer vendorSiteId) {
        this.vendorSiteId = vendorSiteId;
    }

    public String getTermsName() {
        return termsName;
    }

    public void setTermsName(String termsName) {
        this.termsName = termsName;
    }

    public Integer getTerms() {
        return terms;
    }

    public void setTerms(Integer terms) {
        this.terms = terms;
    }

    public String getAwtGroup() {
        return awtGroup;
    }

    public void setAwtGroup(String awtGroup) {
        this.awtGroup = awtGroup;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vendorId != null ? vendorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VendorMaster)) {
            return false;
        }
        VendorMaster other = (VendorMaster) object;
        if ((this.vendorId == null && other.vendorId != null) || (this.vendorId != null && !this.vendorId.equals(other.vendorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apssp.vendor.backend.VendorMaster[ vendorId=" + vendorId + " ]";
    }

    public VendorLogin getVendorLogin() {
        return vendorLogin;
    }

    public void setVendorLogin(VendorLogin vendorLogin) {
        this.vendorLogin = vendorLogin;
    }
    
}
