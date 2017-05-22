/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.controller.jsf;

import com.apssp.vendor.backend.entities.BillingDetail;
import com.apssp.vendor.backend.entities.BillingMaster;
import com.apssp.vendor.backend.entities.DocumentDetail;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.validation.constraints.NotNull;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author meyux
 */
@Named(value = "vendorBillingSubmission")
@SessionScoped
public class VendorBillingSubmission implements Serializable {

    @EJB
    private com.apssp.vendor.backend.controller.session.BillingMasterFacade ejbBillingMasterFacade;
    @EJB
    private com.apssp.vendor.backend.controller.session.BillingDetailFacade ejbBillingDetailFacade;    
    @EJB
    private com.apssp.vendor.backend.controller.session.VendorLoginFacade ejbVendorLoginFacade;
    @EJB
    private com.apssp.vendor.backend.controller.session.VendorMasterFacade vendorMasterFacade;
    @EJB
    private com.apssp.vendor.backend.controller.session.DocumentDetailFacade ejbDocumentDetailFacade;

    private List<SelectItem> options = new ArrayList<SelectItem>();
    private String selectedDocumentType = "";
    private String documentSelectedNo = "";
    private boolean isRenderSelectedNo = true;

    private int documentSelectedPage = 0;
    private Date documentSelectedDate = new Date();

    private String genereratedRefID = "";
    @NotNull
    private String invoiceNo = "";
    private Date invoiceDate = new Date();

    private String loginId = "";
    private String password = "";
    private String vendorName = "";
    private int vendorId;

    private ArrayList<DocumentDetail> docCapturedList = null;
    private int countPhotoClick = 0;

    private BillingMaster billingMasterData = null;
    private BillingDetail billingDetailData = null;

    public boolean isIsRenderSelectedNo() {
        return isRenderSelectedNo;
    }

    public void setIsRenderSelectedNo(boolean isRenderSelectedNo) {
        this.isRenderSelectedNo = isRenderSelectedNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
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

    private String generateRefID() {
        return getMonthYear() + "-" + getSeriesNumber();
    }

    private String getMonthYear() {
        DateFormat formatter = new SimpleDateFormat("MMyyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    private String getSeriesNumber() {
        List<BillingMaster> billingMasters = ejbBillingMasterFacade.findAll();
        Integer iseries = new Integer("00000");
        iseries++;
        if (!billingMasters.isEmpty()) {
            String refID = billingMasters.get(billingMasters.size() - 1).getRefId();
            iseries = Integer.parseInt(refID.substring(refID.lastIndexOf("-") + 1));
            iseries++;
        }
        return String.format("%05d", iseries);
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

    public String getGenereratedRefID() {
        return genereratedRefID;
    }

    public void setGenereratedRefID(String genereratedRefID) {
        this.genereratedRefID = genereratedRefID;
    }

    public List<SelectItem> getOptions() {
        return options;
    }

    public void setOptions(List<SelectItem> options) {
        this.options = options;
    }

    public String getSelectedDocumentType() {
        return selectedDocumentType;
    }

    public void setSelectedDocumentType(String selectedDocumentType) {
        this.selectedDocumentType = selectedDocumentType;
    }

    public String getDocumentSelectedNo() {
        return documentSelectedNo;
    }

    public void setDocumentSelectedNo(String documentSelectedNo) {
        this.documentSelectedNo = documentSelectedNo;
    }

    public int getDocumentSelectedPage() {
        return documentSelectedPage;
    }

    public void setDocumentSelectedPage(int documentSelectedPage) {
        this.documentSelectedPage = documentSelectedPage;
    }

    public Date getDocumentSelectedDate() {
        return documentSelectedDate;
    }

    public void setDocumentSelectedDate(Date documentSelectedDate) {
        this.documentSelectedDate = documentSelectedDate;
    }

    /**
     * Creates a new instance of SelectDocumentType
     */
    public VendorBillingSubmission() {
        addItemsToSelectMenu();
    }

    private void addItemsToSelectMenu() {
        this.options.add(new SelectItem(1, "Invoice"));
        this.options.add(new SelectItem(2, "Delivery Receipt"));
        this.options.add(new SelectItem(3, "Statement of Account"));
        this.options.add(new SelectItem(4, "Billing Statement"));
        this.options.add(new SelectItem(5, "others"));
    }

    @PostConstruct
    public void init() {
        reset();
    }

    public void reset() {
        docCapturedList = new ArrayList<>();
        countPhotoClick = 0;
        billingMasterData = new BillingMaster();
        billingDetailData = new BillingDetail();
        setGenereratedRefID(generateRefID());
        billingMasterData.setRefId(getGenereratedRefID());
        billingDetailData.setRefId(getGenereratedRefID());
        billingDetailData.setBillingMaster(billingMasterData);
        setInvoiceNo("");
        setInvoiceDate(new Date());
        setSelectedDocumentType("");
    }

    public String checkLogin() {
        return testVendoSubmitPage();
//        if (this.getLoginId().isEmpty() || this.getPassword().isEmpty()) {
//            return "/index";
//        }
//
//        List<VendorLogin> vendors = ejbVendorLoginFacade.findAll();
//        if (!vendors.isEmpty()) {
//            for (VendorLogin vendor : vendors) {
//                if (this.getLoginId().equals(vendor.getLoginId()) && this.getPassword().equals(vendor.getPassword())) {
//                    setVendorId(vendor.getVendorId());
//                    setVendorName(vendorMasterFacade.find(vendor.getVendorId()).getVendorName());
//                    return "/vendorSubmit";
//                }
//            }
//        }
//        return "/index";
    }

    public void oncapture(CaptureEvent captureEvent) {
        documentSelectedPage = ++countPhotoClick;
        byte[] data = captureEvent.getData();
        DocumentDetail document = new DocumentDetail();
        document.setDocImage(data);
        document.setDocNo(genereratedRefID);
        document.setDocPage(countPhotoClick);
        docCapturedList.add(document);
    }

    public void onSubmitDocument() {
        if (!docCapturedList.isEmpty()) {
            populateBillingMaster();
            populateBillingDetails();
            ejbBillingMasterFacade.create(billingMasterData);
            ejbBillingDetailFacade.create(billingDetailData);
            for (DocumentDetail doc : docCapturedList) {
                doc.setRefId(billingMasterData);
                ejbDocumentDetailFacade.create(doc);
            }
            reset();
        }
    }

    private void populateBillingMaster() {
        billingMasterData.setInvoiceDate(getInvoiceDate());
        billingMasterData.setInvoiceNo(getInvoiceNo());
        billingMasterData.setVendorId(getVendorId());
    }

    private void populateBillingDetails() {
        billingDetailData.setDocDate(getDocumentSelectedDate());
        billingDetailData.setDocNo(getDocumentSelectedNo());
        billingDetailData.setPages(countPhotoClick);
        billingDetailData.setDocType(selectedDocumentType);
//        billingDetailData.setDocType(options.get(Integer.parseInt(selectedDocumentType)-1).getLabel());
        billingDetailData.setDocrefId(getGenereratedRefID());
        billingDetailData.setUploadBy(getVendorName());
        billingDetailData.setUploadDate(new Date());
    }

    private String testVendoSubmitPage() {
        setVendorId(3);
        setVendorName("testing");
        return "/vendorSubmit";
    }
}
