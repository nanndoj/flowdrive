package com.mex.drive.business;

import java.util.List;

import com.mex.drive.model.dao.DAO;
import com.mex.drive.model.entity.BasicEntity;

public abstract class BusinessObject implements ObjectHandler<BasicEntity> {
  @Override
  public BasicEntity insert(BasicEntity entity) throws Exception {
    DAO dao = DAO.getInstance(entity.getClass());
    return dao.insert(entity);
  }

  @Override
  public boolean update(BasicEntity entity) throws Exception {
    DAO dao = DAO.getInstance(entity.getClass());
    return dao.update(entity);
  }

  @Override
  public boolean delete(BasicEntity entity) throws Exception {
    DAO dao = DAO.getInstance(entity.getClass());
    return dao.delete(entity);
  }

  @Override
  public <K extends BasicEntity> K get(Class<K> clazz, Object id) {
    DAO dao = DAO.getInstance(clazz);
    return dao.get(clazz, id);
  }

  @Override
  public <K extends BasicEntity> List<K> listAll(Class<K> clazz) {
    DAO dao = DAO.getInstance(clazz);
    return dao.listAll(clazz);
  }

}