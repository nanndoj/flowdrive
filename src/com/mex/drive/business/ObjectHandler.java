package com.mex.drive.business;

import java.util.List;

import com.mex.drive.model.entity.BasicEntity;

/**
 * Interface for holding basic CRUD operations on model entities
 *
 * @author fernando
 *
 */
public interface ObjectHandler<T> {

  /**
   * Creates a new entity record into the datastore
   *
   * @return
   */
  T insert(T entity) throws Exception;

  /**
   * Updates the given entity record on the datastore
   *
   * @return
   */
  boolean update(T entity) throws Exception;

  /**
   * Removes an entity from the datastore
   *
   * @return
   */
  boolean delete(T entity) throws Exception;

  /**
   * List all entities from the datastore
   *
   * @return
   */
  <K extends T> List<K> listAll(Class<K> clazz);

  /**
   * List all entities from the datastore
   *
   * @return
   */
  <K extends BasicEntity> K get(Class<K> clazz, Object id);
}
