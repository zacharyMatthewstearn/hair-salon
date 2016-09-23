import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {
  // Rules
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  // Tests
  //// save
  @Test
  public void save_savesStylistToDatabase_true() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.save();
    assertTrue(testStylist.getId() > 0);
  }

  //// equals
  @Test
  public void equals_returnsFalseWhenUnequal_false() {
    Stylist testStylist1 = new Stylist("Magnus");
    testStylist1.save();
    Stylist testStylist2 = new Stylist("Merle");
    testStylist2.save();
    assertTrue(!testStylist1.equals(testStylist2));
  }
  @Test
  public void equals_returnsTrueWhenEqual_true() {
    Stylist testStylist1 = new Stylist("Taako");
    testStylist1.save();
    Stylist testStylist2 = testStylist1;
    testStylist2.save();
    assertTrue(testStylist1.equals(testStylist2));
  }

  //// getById
  @Test
  public void getById_returnsNullIfStylistNotInDB_null() {
    Stylist testStylist = new Stylist("Taako");
    assertEquals(null, Stylist.getById(testStylist.getId()));
  }
  @Test
  public void getById_fetchesCorrectStylistFromDB_Stylist() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.save();
    assertTrue(Stylist.getById(testStylist.getId()).equals(testStylist));
  }

  //// getAll
  @Test
  public void getAll_returnsEmptyListIfNoStylistsInDB_ListStylist() {
    assertTrue(Stylist.getAll().size() == 0);
  }
  @Test
  public void getAll_fetchesAllStylistsFromDB_ListStylist() {
    Stylist testStylist1 = new Stylist("Merle");
    testStylist1.save();
    Stylist testStylist2 = new Stylist("Magnus");
    testStylist2.save();
    assertTrue(Stylist.getAll().get(1).equals(testStylist2));
  }

  //// delete
  @Test
  public void delete_doesNotCrashIfStylistIsNotInDB_null() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.delete();
    assertEquals(null, Stylist.getById(testStylist.getId()));
  }
  @Test
  public void delete_removesStylistFromDatabase_null() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.save();
    testStylist.delete();
    assertEquals(null, Stylist.getById(testStylist.getId()));
  }

  //// setName
  @Test
  public void setName_doesNotCrashIfStylistIsNotInDB_null() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.setName("Barry Bluejeans");
    assertEquals(null, Stylist.getById(1));
  }
  @Test
  public void setName_changesNameAppropriately_String() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.save();
    testStylist.setName("Barry Bluejeans");
    assertTrue("DB", Stylist.getById(testStylist.getId()).getName().equals("Barry Bluejeans"));
    assertTrue("Member variable", testStylist.getName().equals("Barry Bluejeans"));
  }

  //// getClients
  @Test
  public void getClients_returnsEmtpyListIfNoStylistsInDB_ListStylist() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.save();
    assertTrue(testStylist.getClients().size() == 0);
  }
  @Test
  public void getClients_returnsAppropriateClients_ListStylist() {
    Stylist testStylist = new Stylist("Taako");
    testStylist.save();
    Client testClient1 = new Client(testStylist.getId(), "Merle");
    testClient1.save();
    Client testClient2 = new Client(testStylist.getId(), "Magnus");
    testClient2.save();
    assertTrue("Merle", testStylist.getClients().get(0).equals(testClient1));
    assertTrue("Magnus", testStylist.getClients().get(1).equals(testClient2));
  }

}
