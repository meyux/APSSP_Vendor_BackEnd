package com.apssp.vendor.backend.controller.jsf;

import com.apssp.vendor.backend.entities.VendorLogin;
import com.apssp.vendor.backend.controller.jsf.util.JsfUtil;
import com.apssp.vendor.backend.controller.jsf.util.JsfUtil.PersistAction;
import com.apssp.vendor.backend.controller.session.VendorLoginFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("vendorLoginController")
@SessionScoped
public class VendorLoginController implements Serializable {

    @EJB
    private com.apssp.vendor.backend.controller.session.VendorLoginFacade ejbFacade;
    private List<VendorLogin> items = null;
    private VendorLogin selected;
    @EJB
    private com.apssp.vendor.backend.controller.session.VendorMasterFacade vendorMasterFacade;
    private String vendorName = null;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public VendorLoginController() {
    }

    public VendorLogin getSelected() {
        return selected;
    }

    public void setSelected(VendorLogin selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VendorLoginFacade getFacade() {
        return ejbFacade;
    }

    public VendorLogin prepareCreate() {
        selected = new VendorLogin();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VendorLoginCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VendorLoginUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VendorLoginDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<VendorLogin> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public VendorLogin getVendorLogin(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<VendorLogin> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<VendorLogin> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public String checkLogin() {
        String loginId = selected.getLoginId();
        String pass = selected.getPassword();
        List<VendorLogin> vendors = getItems();
        
        if(loginId == null || pass == null)
            return "/index";
        if(!vendors.isEmpty()) {
            for (VendorLogin vendor: vendors) {
                if(loginId.equals(vendor.getLoginId()) && pass.equals(vendor.getPassword())) {
                    selected.setVendorId(vendor.getVendorId());
                    setVendorName(vendorMasterFacade.find(vendor.getVendorId()).getVendorName());
                    return "/vendorSubmit";
                }
            }
        }
        return "/index";
    }
    
    @FacesConverter(forClass = VendorLogin.class)
    public static class VendorLoginControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VendorLoginController controller = (VendorLoginController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "vendorLoginController");
            return controller.getVendorLogin(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof VendorLogin) {
                VendorLogin o = (VendorLogin) object;
                return getStringKey(o.getVendorId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), VendorLogin.class.getName()});
                return null;
            }
        }

    }

}
