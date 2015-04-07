package com.mex.drive.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.mex.drive.model.entity.History;
import com.mex.drive.model.entity.ProcessUnit;

public class DAOTest {

  // maximum eventual consistency
  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
                                                  new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

  @Before
  public void setUp() {
    this.helper.setUp();
  }

  @After
  public void tearDown() {
    this.helper.tearDown();
  }

  @Test
  public void shouldInsertEntityIntoDatastoreWithChildren() {
    DAO dao = new ProcessUnitDAO();

    ProcessUnit entity = new ProcessUnit();
    entity.setNumber(1234567);
    entity.setSubject("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    try {
      assertNull(entity.getId());
      dao.insert(entity);
      assertNotNull(entity.getId());

      // Check if the children was inserted
      assertNotNull(entity.getHistory());
      assertNotNull(entity.getHistory().get(0));
      assertNotNull(entity.getHistory().get(0).getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldDeleteEntityFromDatastore() {
    DAO dao = new ProcessUnitDAO();

    ProcessUnit entity = new ProcessUnit();
    entity.setNumber(1234567);
    entity.setSubject("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    try {
      // Insert a fake entity
      dao.insert(entity);

      // look for the object to remove
      ProcessUnit found = dao.get(ProcessUnit.class, entity.getId());
      assertNotNull(found);
      assertEquals(found.getId(), entity.getId());

      // Remove the object
      dao.delete(found);

      // look for the object to remove
      ProcessUnit deleted = dao.get(ProcessUnit.class, entity.getId());
      assertNull(deleted);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldGetEntityFromDatastore() {
    DAO dao = new ProcessUnitDAO();

    ProcessUnit entity = new ProcessUnit();
    entity.setNumber(1234567);
    entity.setSubject("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    assertNull(entity.getId());
    assertNull(history.getId());

    try {
      // Insert a fake entity
      dao.insert(entity);

      // look for the object to remove
      ProcessUnit found = dao.get(ProcessUnit.class, entity.getId());
      assertNotNull(found);
      assertEquals(found.getId(), entity.getId());

      // look for the object to remove
      History children = dao.get(History.class, history.getId());
      assertNotNull(children);
      assertEquals(children.getText(), history.getText());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldUpdateEntityFromDatastore() {
    DAO dao = new ProcessUnitDAO();

    ProcessUnit entity = new ProcessUnit();
    entity.setNumber(1234567);
    entity.setSubject("Any subject");

    List<History> list = new ArrayList<History>();
    History history = new History();
    history.setDate(new Date());
    history.setText("Any text");
    list.add(history);

    entity.setHistory(list);

    assertNull(entity.getId());
    assertNull(history.getId());

    try {
      // Insert a fake entity
      dao.insert(entity);

      // Create a new entity to replace the other one
      ProcessUnit newUnit = new ProcessUnit();
      newUnit.setId(entity.getId()); // They must have the same ID
      newUnit.setSubject("Text changed");

      dao.update(newUnit);

      // Get the new entity
      ProcessUnit found = dao.get(ProcessUnit.class, newUnit.getId());
      assertNotNull(found);
      assertEquals("Text changed", found.getSubject());
      assertNotNull(found.getHistory());
      assertNotEquals("Any text", found.getHistory().get(0).getText());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}