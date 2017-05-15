/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.apssp.vendor.backend.BillingMaster;
import com.apssp.vendor.backend.BillingStatus;
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
public class BillingStatusJpaController implements Serializable {

    public BillingStatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BillingStatus billingStatus) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        BillingMaster billingMasterOrphanCheck = billingStatus.getBillingMaster();
        if (billingMasterOrphanCheck != null) {
            BillingStatus oldBillingStatusOfBillingMaster = billingMasterOrphanCheck.getBillingStatus();
            if (oldBillingStatusOfBillingMaster != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The BillingMaster " + billingMasterOrphanCheck + " already has an item of type BillingStatus whose billingMaster column cannot be null. Please make another selection for the billingMaster field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingMaster billingMaster = billingStatus.getBillingMaster();
            if (billingMaster != null) {
                billingMaster = em.getReference(billingMaster.getClass(), billingMaster.getRefId());
                billingStatus.setBillingMaster(billingMaster);
            }
            em.persist(billingStatus);
            if (billingMaster != null) {
                billingMaster.setBillingStatus(billingStatus);
                billingMaster = em.merge(billingMaster);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBillingStatus(billingStatus.getRefId()) != null) {
                throw new PreexistingEntityException("BillingStatus " + billingStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BillingStatus billingStatus) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingStatus persistentBillingStatus = em.find(BillingStatus.class, billingStatus.getRefId());
            BillingMaster billingMasterOld = persistentBillingStatus.getBillingMaster();
            BillingMaster billingMasterNew = billingStatus.getBillingMaster();
            List<String> illegalOrphanMessages = null;
            if (billingMasterNew != null && !billingMasterNew.equals(billingMasterOld)) {
                BillingStatus oldBillingStatusOfBillingMaster = billingMasterNew.getBillingStatus();
                if (oldBillingStatusOfBillingMaster != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The BillingMaster " + billingMasterNew + " already has an item of type BillingStatus whose billingMaster column cannot be null. Please make another selection for the billingMaster field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (billingMasterNew != null) {
                billingMasterNew = em.getReference(billingMasterNew.getClass(), billingMasterNew.getRefId());
                billingStatus.setBillingMaster(billingMasterNew);
            }
            billingStatus = em.merge(billingStatus);
            if (billingMasterOld != null && !billingMasterOld.equals(billingMasterNew)) {
                billingMasterOld.setBillingStatus(null);
                billingMasterOld = em.merge(billingMasterOld);
            }
            if (billingMasterNew != null && !billingMasterNew.equals(billingMasterOld)) {
                billingMasterNew.setBillingStatus(billingStatus);
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
                String id = billingStatus.getRefId();
                if (findBillingStatus(id) == null) {
                    throw new NonexistentEntityException("The billingStatus with id " + id + " no longer exists.");
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
            BillingStatus billingStatus;
            try {
                billingStatus = em.getReference(BillingStatus.class, id);
                billingStatus.getRefId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The billingStatus with id " + id + " no longer exists.", enfe);
            }
            BillingMaster billingMaster = billingStatus.getBillingMaster();
            if (billingMaster != null) {
                billingMaster.setBillingStatus(null);
                billingMaster = em.merge(billingMaster);
            }
            em.remove(billingStatus);
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

    public List<BillingStatus> findBillingStatusEntities() {
        return findBillingStatusEntities(true, -1, -1);
    }

    public List<BillingStatus> findBillingStatusEntities(int maxResults, int firstResult) {
        return findBillingStatusEntities(false, maxResults, firstResult);
    }

    private List<BillingStatus> findBillingStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BillingStatus.class));
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

    public BillingStatus findBillingStatus(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BillingStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillingStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BillingStatus> rt = cq.from(BillingStatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
