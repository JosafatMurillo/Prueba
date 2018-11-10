/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.inbo.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import mx.inbo.controllers.exceptions.NonexistentEntityException;
import mx.inbo.entities.Answer;
import mx.inbo.entities.Question;

/**
 *
 * @author BODEGA
 */
public class AnswerJpaController implements Serializable {

    public AnswerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Answer answer) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Question idQuestion = answer.getIdQuestion();
            if (idQuestion != null) {
                idQuestion = em.getReference(idQuestion.getClass(), idQuestion.getIdQuestion());
                answer.setIdQuestion(idQuestion);
            }
            em.persist(answer);
            if (idQuestion != null) {
                idQuestion.getAnswerCollection().add(answer);
                idQuestion = em.merge(idQuestion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Answer answer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Answer persistentAnswer = em.find(Answer.class, answer.getIdAnswer());
            Question idQuestionOld = persistentAnswer.getIdQuestion();
            Question idQuestionNew = answer.getIdQuestion();
            if (idQuestionNew != null) {
                idQuestionNew = em.getReference(idQuestionNew.getClass(), idQuestionNew.getIdQuestion());
                answer.setIdQuestion(idQuestionNew);
            }
            answer = em.merge(answer);
            if (idQuestionOld != null && !idQuestionOld.equals(idQuestionNew)) {
                idQuestionOld.getAnswerCollection().remove(answer);
                idQuestionOld = em.merge(idQuestionOld);
            }
            if (idQuestionNew != null && !idQuestionNew.equals(idQuestionOld)) {
                idQuestionNew.getAnswerCollection().add(answer);
                idQuestionNew = em.merge(idQuestionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = answer.getIdAnswer();
                if (findAnswer(id) == null) {
                    throw new NonexistentEntityException("The answer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Answer answer;
            try {
                answer = em.getReference(Answer.class, id);
                answer.getIdAnswer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The answer with id " + id + " no longer exists.", enfe);
            }
            Question idQuestion = answer.getIdQuestion();
            if (idQuestion != null) {
                idQuestion.getAnswerCollection().remove(answer);
                idQuestion = em.merge(idQuestion);
            }
            em.remove(answer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Answer> findAnswerEntities() {
        return findAnswerEntities(true, -1, -1);
    }

    public List<Answer> findAnswerEntities(int maxResults, int firstResult) {
        return findAnswerEntities(false, maxResults, firstResult);
    }

    private List<Answer> findAnswerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Answer.class));
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

    public Answer findAnswer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Answer.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnswerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Answer> rt = cq.from(Answer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
