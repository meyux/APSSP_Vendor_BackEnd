/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.controller;

import com.apssp.vendor.backend.VendorMaster;
import com.apssp.vendor.backend.controller.exceptions.NonexistentEntityException;
import com.apssp.vendor.backend.controller.exceptions.PreexistingEntityException;
import com.apssp.vendor.backend.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author meyux
 */
public class VendorMasterJpaController implements Serializable {

    public VendorMasterJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VendorMaster vendorMaster) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(vendorMaster);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVendorMaster(vendorMaster.getVendorId()) != null) {
                throw new PreexistingEntityException("VendorMaster " + vendorMaster + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VendorMaster vendorMaster) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            vendorMaster = em.merge(vendorMaster);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendorMaster.getVendorId();
                if (findVendorMaster(id) == null) {
                    throw new NonexistentEntityException("The vendorMaster with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            VendorMaster vendorMaster;
            try {
                vendorMaster = em.getReference(VendorMaster.class, id);
                vendorMaster.getVendorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendorMaster with id " + id + " no longer exists.", enfe);
            }
            em.remove(vendorMaster);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VendorMaster> findVendorMasterEntities() {
        return findVendorMasterEntities(true, -1, -1);
    }

    public List<VendorMaster> findVendorMasterEntities(int maxResults, int firstResult) {
        return findVendorMasterEntities(false, maxResults, firstResult);
    }

    private List<VendorMaster> findVendorMasterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VendorMaster.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public VendorMaster findVendorMaster(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VendorMaster.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendorMasterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VendorMaster> rt = cq.from(VendorMaster.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
