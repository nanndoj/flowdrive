package com.mex.drive.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.mex.drive.business.ObjectHandler;
import com.mex.drive.model.EMF;
import com.mex.drive.model.entity.BasicEntity;

public abstract class DAO implements ObjectHandler<BasicEntity> {

  @Override
  public BasicEntity insert(BasicEntity entity) throws Exception {
    // Create an entity manager
    EntityManager em = EMF.get().createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      // Persist the new entity into the datastore
      em.persist(entity);
      // Commit the transaction
      tx.commit();
    } catch (Exception e) {
      // Rollback if there's something wrong
      tx.rollback();
      return null;
    } finally {
      em.close();
    }
    return entity;
  }

  @Override
  public boolean update(BasicEntity entity) throws Exception {
    // Create an entity manager
    EntityManager em = EMF.get().createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      // Persist the new entity into the datastore
      em.merge(entity);
      // Commit the transaction
      tx.commit();
    } catch (Exception e) {
      // Rollback if there's something wrong
      tx.rollback();
      return false;
    } finally {
      em.close();
    }
    return true;
  }

  @Override
  public boolean delete(BasicEntity entity) throws Exception {
    // Create an entity manager
    EntityManager em = EMF.get().createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      // Find the entity
      BasicEntity found = em.find(entity.getClass(), entity.getId());
      em.remove(found);
      // Commit the transaction
      tx.commit();
    } catch (Exception e) {
      // Rollback if there's something wrong
      tx.rollback();
      return false;
    } finally {
      em.close();
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <K extends BasicEntity> List<K> listAll(Class<K> clazz) {
    // Create an entity manager
    EntityManager em = EMF.get().createEntityManager();
    try {
      Query query = em.createQuery("SELECT clazz FROM " + clazz.getName() + " clazz");
      return query.getResultList();
    } finally {
      em.close();
    }
  }

  @Override
  public <K extends BasicEntity> K get(Class<K> clazz, Key id) {
    // Create an entity manager
    EntityManager em = EMF.get().createEntityManager();
    try {
      return em.find(clazz, id);
    } finally {
      em.close();
    }
  }

}
