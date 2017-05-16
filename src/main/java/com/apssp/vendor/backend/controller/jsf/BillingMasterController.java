package com.apssp.vendor.backend.controller.jsf;

import com.apssp.vendor.backend.entities.BillingMaster;
import com.apssp.vendor.backend.controller.jsf.util.JsfUtil;
import com.apssp.vendor.backend.controller.jsf.util.JsfUtil.PersistAction;
import com.apssp.vendor.backend.controller.session.BillingMasterFacade;

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


@Named("billingMasterController")
@SessionScoped
public class BillingMasterController implements Serializable {


    @EJB private com.apssp.vendor.backend.controller.session.BillingMasterFacade ejbFacade;
    private List<BillingMaster> items = null;
    private BillingMaster selected;

    public BillingMasterController() {
    }

    public BillingMaster getSelected() {
        return selected;
    }

    public void setSelected(BillingMaster selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BillingMasterFacade getFacade() {
        return ejbFacade;
    }

    public BillingMaster prepareCreate() {
        selected = new BillingMaster();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BillingMasterCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BillingMasterUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BillingMasterDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BillingMaster> getItems() {
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

    public BillingMaster getBillingMaster(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<BillingMaster> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BillingMaster> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=BillingMaster.class)
    public static class BillingMasterControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BillingMasterController controller = (BillingMasterController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "billingMasterController");
            return controller.getBillingMaster(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BillingMaster) {
                BillingMaster o = (BillingMaster) object;
                return getStringKey(o.getRefId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BillingMaster.class.getName()});
                return null;
            }
        }

    }

}
