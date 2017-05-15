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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meyux
 */
@Entity
@Table(name = "VENDOR_LOGIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VendorLogin.findAll", query = "SELECT v FROM VendorLogin v")
    , @NamedQuery(name = "VendorLogin.findByVendorId", query = "SELECT v FROM VendorLogin v WHERE v.vendorId = :vendorId")
    , @NamedQuery(name = "VendorLogin.findByLoginId", query = "SELECT v FROM VendorLogin v WHERE v.loginId = :loginId")
    , @NamedQuery(name = "VendorLogin.findByPassword", query = "SELECT v FROM VendorLogin v WHERE v.password = :password")})
public class VendorLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VENDOR_ID")
    private Integer vendorId;
    @Size(max = 255)
    @Column(name = "LOGIN_ID")
    private String loginId;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @JoinColumn(name = "VENDOR_ID", referencedColumnName = "VENDOR_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private VendorMaster vendorMaster;

    public VendorLogin() {
    }

    public VendorLogin(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VendorMaster getVendorMaster() {
        return vendorMaster;
    }

    public void setVendorMaster(VendorMaster vendorMaster) {
        this.vendorMaster = vendorMaster;
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
        if (!(object instanceof VendorLogin)) {
            return false;
        }
        VendorLogin other = (VendorLogin) object;
        if ((this.vendorId == null && other.vendorId != null) || (this.vendorId != null && !this.vendorId.equals(other.vendorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apssp.vendor.backend.entities.VendorLogin[ vendorId=" + vendorId + " ]";
    }
    
}
