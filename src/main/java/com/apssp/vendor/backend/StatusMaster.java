/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meyux
 */
@Entity
@Table(name = "STATUS_MASTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusMaster.findAll", query = "SELECT s FROM StatusMaster s")
    , @NamedQuery(name = "StatusMaster.findByStatusId", query = "SELECT s FROM StatusMaster s WHERE s.statusId = :statusId")
    , @NamedQuery(name = "StatusMaster.findByStatusDesc", query = "SELECT s FROM StatusMaster s WHERE s.statusDesc = :statusDesc")})
public class StatusMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_ID")
    private Integer statusId;
    @Size(max = 255)
    @Column(name = "STATUS_DESC")
    private String statusDesc;

    public StatusMaster() {
    }

    public StatusMaster(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusMaster)) {
            return false;
        }
        StatusMaster other = (StatusMaster) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apssp.vendor.backend.StatusMaster[ statusId=" + statusId + " ]";
    }
    
}
