/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.controller;

import com.apssp.vendor.backend.entities.BillingDetail;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.apssp.vendor.backend.entities.BillingMaster;
import com.apssp.vendor.backend.controller.exceptions.IllegalOrphanException;
import com.apssp.vendor.backend.controller.exceptions.NonexistentEntityException;
import com.apssp.vendor.backend.controller.exceptions.PreexistingEntityException;
import com.apssp.vendor.backend.controller.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author meyux
 */
public class BillingDetailJpaController implements Serializable {

    public BillingDetailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BillingDetail billingDetail) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        BillingMaster billingMasterOrphanCheck = billingDetail.getBillingMaster();
        if (billingMasterOrphanCheck != null) {
            BillingDetail oldBillingDetailOfBillingMaster = billingMasterOrphanCheck.getBillingDetail();
            if (oldBillingDetailOfBillingMaster != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The BillingMaster " + billingMasterOrphanCheck + " already has an item of type BillingDetail whose billingMaster column cannot be null. Please make another selection for the billingMaster field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingMaster billingMaster = billingDetail.getBillingMaster();
            if (billingMaster != null) {
                billingMaster = em.getReference(billingMaster.getClass(), billingMaster.getRefId());
                billingDetail.setBillingMaster(billingMaster);
            }
            em.persist(billingDetail);
            if (billingMaster != null) {
                billingMaster.setBillingDetail(billingDetail);
                billingMaster = em.merge(billingMaster);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBillingDetail(billingDetail.getRefId()) != null) {
                throw new PreexistingEntityException("BillingDetail " + billingDetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BillingDetail billingDetail) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingDetail persistentBillingDetail = em.find(BillingDetail.class, billingDetail.getRefId());
            BillingMaster billingMasterOld = persistentBillingDetail.getBillingMaster();
            BillingMaster billingMasterNew = billingDetail.getBillingMaster();
            List<String> illegalOrphanMessages = null;
            if (billingMasterNew != null && !billingMasterNew.equals(billingMasterOld)) {
                BillingDetail oldBillingDetailOfBillingMaster = billingMasterNew.getBillingDetail();
                if (oldBillingDetailOfBillingMaster != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The BillingMaster " + billingMasterNew + " already has an item of type BillingDetail whose billingMaster column cannot be null. Please make another selection for the billingMaster field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (billingMasterNew != null) {
                billingMasterNew = em.getReference(billingMasterNew.getClass(), billingMasterNew.getRefId());
                billingDetail.setBillingMaster(billingMasterNew);
            }
            billingDetail = em.merge(billingDetail);
            if (billingMasterOld != null && !billingMasterOld.equals(billingMasterNew)) {
                billingMasterOld.setBillingDetail(null);
                billingMasterOld = em.merge(billingMasterOld);
            }
            if (billingMasterNew != null && !billingMasterNew.equals(billingMasterOld)) {
                billingMasterNew.setBillingDetail(billingDetail);
                billingMasterNew = em.merge(billingMasterNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = billingDetail.getRefId();
                if (findBillingDetail(id) == null) {
                    throw new NonexistentEntityException("The billingDetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingDetail billingDetail;
            try {
                billingDetail = em.getReference(BillingDetail.class, id);
                billingDetail.getRefId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The billingDetail with id " + id + " no longer exists.", enfe);
            }
            BillingMaster billingMaster = billingDetail.getBillingMaster();
            if (billingMaster != null) {
                billingMaster.setBillingDetail(null);
                billingMaster = em.merge(billingMaster);
            }
            em.remove(billingDetail);
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

    public List<BillingDetail> findBillingDetailEntities() {
        return findBillingDetailEntities(true, -1, -1);
    }

    public List<BillingDetail> findBillingDetailEntities(int maxResults, int firstResult) {
        return findBillingDetailEntities(false, maxResults, firstResult);
    }

    private List<BillingDetail> findBillingDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BillingDetail.class));
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

    public BillingDetail findBillingDetail(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BillingDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillingDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BillingDetail> rt = cq.from(BillingDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
