/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author meyux
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.apssp.vendor.backend.service.BillingDetailFacadeREST.class);
        resources.add(com.apssp.vendor.backend.service.BillingMasterFacadeREST.class);
        resources.add(com.apssp.vendor.backend.service.BillingStatusFacadeREST.class);
        resources.add(com.apssp.vendor.backend.service.StatusMasterFacadeREST.class);
        resources.add(com.apssp.vendor.backend.service.VendorMasterFacadeREST.class);
    }
    
}
