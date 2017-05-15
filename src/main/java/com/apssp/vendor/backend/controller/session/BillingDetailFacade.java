/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.controller.session;

import com.apssp.vendor.backend.entities.BillingDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author meyux
 */
@Stateless
public class BillingDetailFacade extends AbstractFacade<BillingDetail> {

    @PersistenceContext(unitName = "com.apssp.vendor.backend_APSSP_Vendor_BackEnd_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillingDetailFacade() {
        super(BillingDetail.class);
    }
    
}
