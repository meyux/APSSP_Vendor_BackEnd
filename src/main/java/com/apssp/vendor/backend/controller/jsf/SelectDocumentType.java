/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.controller.jsf;

import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author meyux
 */
@Named(value = "selectDocumentType")
@SessionScoped
public class SelectDocumentType implements Serializable {

    private List<SelectItem> options = new ArrayList<SelectItem>();
    private String selectedDocumentType = "";
    private String documentSelectedNo = "";
    private int documentSelectedPage = 1;
    private Date documentSelectedDate = new Date();

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
    public SelectDocumentType() {
        this.options.add(new SelectItem(1, "Invoice"));
        this.options.add(new SelectItem(2, "Delivery Receipt"));
        this.options.add(new SelectItem(3, "Statement of Account"));
        this.options.add(new SelectItem(4, "Billing Statement"));
        this.options.add(new SelectItem(5, "others"));
    }

    public void oncapture(CaptureEvent captureEvent) {
        byte[] data = captureEvent.getData();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "photocam" + File.separator + "captured.png";
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (Exception e) {
            throw new FacesException("Error in writing captured image.");
        }
    }
}
