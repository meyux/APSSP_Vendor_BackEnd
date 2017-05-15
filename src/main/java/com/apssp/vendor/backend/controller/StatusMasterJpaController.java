/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apssp.vendor.backend.controller;

import com.apssp.vendor.backend.entities.StatusMaster;
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
public class StatusMasterJpaController implements Serializable {

    public StatusMasterJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StatusMaster statusMaster) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(statusMaster);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findStatusMaster(statusMaster.getStatusId()) != null) {
                throw new PreexistingEntityException("StatusMaster " + statusMaster + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StatusMaster statusMaster) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            statusMaster = em.merge(statusMaster);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = statusMaster.getStatusId();
                if (findStatusMaster(id) == null) {
                    throw new NonexistentEntityException("The statusMaster with id " + id + " no longer exists.");
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
            StatusMaster statusMaster;
            try {
                statusMaster = em.getReference(StatusMaster.class, id);
                statusMaster.getStatusId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The statusMaster with id " + id + " no longer exists.", enfe);
            }
            em.remove(statusMaster);
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

    public List<StatusMaster> findStatusMasterEntities() {
        return findStatusMasterEntities(true, -1, -1);
    }

    public List<StatusMaster> findStatusMasterEntities(int maxResults, int firstResult) {
        return findStatusMasterEntities(false, maxResults, firstResult);
    }

    private List<StatusMaster> findStatusMasterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StatusMaster.class));
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

    public StatusMaster findStatusMaster(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StatusMaster.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusMasterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StatusMaster> rt = cq.from(StatusMaster.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
