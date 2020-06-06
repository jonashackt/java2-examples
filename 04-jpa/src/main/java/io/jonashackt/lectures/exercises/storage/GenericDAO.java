package io.jonashackt.lectures.exercises.storage;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> {

    private final Class<T> persistenceClass;

    private EntityManager entityManager;
    public GenericDAO(Class<T> type, EntityManager entityManager) {
        this.persistenceClass = type;
        this.entityManager = entityManager;
    }

    public T findById(final ID id) {
        final T result = entityManager.find(persistenceClass, id);
        return result;
    }

    public Collection<T> findAll() {
        Query query = entityManager.createQuery("SELECT e FROM " + persistenceClass.getCanonicalName() + " e");
        return (Collection<T>) query.getResultList();
    }

    public T create(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    public T update(T entity) {
        entityManager.getTransaction().begin();
        final T savedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return savedEntity;
    }

    public void delete (ID id) {
        T entity = this.findById(id);
        this.delete(entity);
    }

    private void delete(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void delete(List<T> entries) {
        entityManager.getTransaction().begin();
        for(T entry : entries) {
            entityManager.remove(entry);
        }
        entityManager.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Class<T> getPersistenceClass() {
        return persistenceClass;
    }
}
