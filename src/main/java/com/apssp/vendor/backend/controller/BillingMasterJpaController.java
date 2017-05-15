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
import com.apssp.vendor.backend.entities.BillingStatus;
import com.apssp.vendor.backend.entities.BillingDetail;
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
public class BillingMasterJpaController implements Serializable {

    public BillingMasterJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BillingMaster billingMaster) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingStatus billingStatus = billingMaster.getBillingStatus();
            if (billingStatus != null) {
                billingStatus = em.getReference(billingStatus.getClass(), billingStatus.getRefId());
                billingMaster.setBillingStatus(billingStatus);
            }
            BillingDetail billingDetail = billingMaster.getBillingDetail();
            if (billingDetail != null) {
                billingDetail = em.getReference(billingDetail.getClass(), billingDetail.getRefId());
                billingMaster.setBillingDetail(billingDetail);
            }
            em.persist(billingMaster);
            if (billingStatus != null) {
                BillingMaster oldBillingMasterOfBillingStatus = billingStatus.getBillingMaster();
                if (oldBillingMasterOfBillingStatus != null) {
                    oldBillingMasterOfBillingStatus.setBillingStatus(null);
                    oldBillingMasterOfBillingStatus = em.merge(oldBillingMasterOfBillingStatus);
                }
                billingStatus.setBillingMaster(billingMaster);
                billingStatus = em.merge(billingStatus);
            }
            if (billingDetail != null) {
                BillingMaster oldBillingMasterOfBillingDetail = billingDetail.getBillingMaster();
                if (oldBillingMasterOfBillingDetail != null) {
                    oldBillingMasterOfBillingDetail.setBillingDetail(null);
                    oldBillingMasterOfBillingDetail = em.merge(oldBillingMasterOfBillingDetail);
                }
                billingDetail.setBillingMaster(billingMaster);
                billingDetail = em.merge(billingDetail);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBillingMaster(billingMaster.getRefId()) != null) {
                throw new PreexistingEntityException("BillingMaster " + billingMaster + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BillingMaster billingMaster) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingMaster persistentBillingMaster = em.find(BillingMaster.class, billingMaster.getRefId());
            BillingStatus billingStatusOld = persistentBillingMaster.getBillingStatus();
            BillingStatus billingStatusNew = billingMaster.getBillingStatus();
            BillingDetail billingDetailOld = persistentBillingMaster.getBillingDetail();
            BillingDetail billingDetailNew = billingMaster.getBillingDetail();
            List<String> illegalOrphanMessages = null;
            if (billingStatusOld != null && !billingStatusOld.equals(billingStatusNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain BillingStatus " + billingStatusOld + " since its billingMaster field is not nullable.");
            }
            if (billingDetailOld != null && !billingDetailOld.equals(billingDetailNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain BillingDetail " + billingDetailOld + " since its billingMaster field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (billingStatusNew != null) {
                billingStatusNew = em.getReference(billingStatusNew.getClass(), billingStatusNew.getRefId());
                billingMaster.setBillingStatus(billingStatusNew);
            }
            if (billingDetailNew != null) {
                billingDetailNew = em.getReference(billingDetailNew.getClass(), billingDetailNew.getRefId());
                billingMaster.setBillingDetail(billingDetailNew);
            }
            billingMaster = em.merge(billingMaster);
            if (billingStatusNew != null && !billingStatusNew.equals(billingStatusOld)) {
                BillingMaster oldBillingMasterOfBillingStatus = billingStatusNew.getBillingMaster();
                if (oldBillingMasterOfBillingStatus != null) {
                    oldBillingMasterOfBillingStatus.setBillingStatus(null);
                    oldBillingMasterOfBillingStatus = em.merge(oldBillingMasterOfBillingStatus);
                }
                billingStatusNew.setBillingMaster(billingMaster);
                billingStatusNew = em.merge(billingStatusNew);
            }
            if (billingDetailNew != null && !billingDetailNew.equals(billingDetailOld)) {
                BillingMaster oldBillingMasterOfBillingDetail = billingDetailNew.getBillingMaster();
                if (oldBillingMasterOfBillingDetail != null) {
                    oldBillingMasterOfBillingDetail.setBillingDetail(null);
                    oldBillingMasterOfBillingDetail = em.merge(oldBillingMasterOfBillingDetail);
                }
                billingDetailNew.setBillingMaster(billingMaster);
                billingDetailNew = em.merge(billingDetailNew);
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
                String id = billingMaster.getRefId();
                if (findBillingMaster(id) == null) {
                    throw new NonexistentEntityException("The billingMaster with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BillingMaster billingMaster;
            try {
                billingMaster = em.getReference(BillingMaster.class, id);
                billingMaster.getRefId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The billingMaster with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            BillingStatus billingStatusOrphanCheck = billingMaster.getBillingStatus();
            if (billingStatusOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BillingMaster (" + billingMaster + ") cannot be destroyed since the BillingStatus " + billingStatusOrphanCheck + " in its billingStatus field has a non-nullable billingMaster field.");
            }
            BillingDetail billingDetailOrphanCheck = billingMaster.getBillingDetail();
            if (billingDetailOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BillingMaster (" + billingMaster + ") cannot be destroyed since the BillingDetail " + billingDetailOrphanCheck + " in its billingDetail field has a non-nullable billingMaster field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(billingMaster);
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

    public List<BillingMaster> findBillingMasterEntities() {
        return findBillingMasterEntities(true, -1, -1);
    }

    public List<BillingMaster> findBillingMasterEntities(int maxResults, int firstResult) {
        return findBillingMasterEntities(false, maxResults, firstResult);
    }

    private List<BillingMaster> findBillingMasterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BillingMaster.class));
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

    public BillingMaster findBillingMaster(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BillingMaster.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillingMasterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BillingMaster> rt = cq.from(BillingMaster.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
