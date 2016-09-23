import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
  // Rules
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  // Tests
  //// save
  @Test
  public void save_savesClientToDatabase_true() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    assertTrue(testClient.getId() > 0);
  }

  //// equals
  @Test
  public void equals_returnsFalseWhenUnequal_false() {
    Client testClient1 = new Client(1, "Magnus");
    testClient1.save();
    Client testClient2 = new Client(1, "Merle");
    testClient2.save();
    assertTrue(!testClient1.equals(testClient2));
  }
  @Test
  public void equals_returnsTrueWhenEqual_true() {
    Client testClient1 = new Client(1, "Taako");
    testClient1.save();
    Client testClient2 = testClient1;
    testClient2.save();
    assertTrue(testClient1.equals(testClient2));
  }

  //// getById
  @Test
  public void getById_returnsNullIfClientNotInDB_null() {
    Client testClient = new Client(1, "Taako");
    assertEquals(null, Client.getById(testClient.getId()));
  }
  @Test
  public void getById_fetchesCorrectClientFromDB_Client() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    assertTrue(Client.getById(testClient.getId()).equals(testClient));
  }

  //// getAll
  @Test
  public void getAll_returnsEmpyListIfNoClientsInDB_ListClient() {
    assertTrue(Client.getAll().size() == 0);
  }
  @Test
  public void getAll_fetchesAllClientsFromDB_ListClient() {
    Client testClient1 = new Client(1, "Merle");
    testClient1.save();
    Client testClient2 = new Client(1, "Magnus");
    testClient2.save();
    assertTrue(Client.getAll().get(1).equals(testClient2));
  }

  //// delete
  @Test
  public void delete_doesNotCrashIfClientIsNotInDB_null() {
    Client testClient = new Client(1, "Taako");
    testClient.delete();
    assertEquals(null, Client.getById(testClient.getId()));
  }
  @Test
  public void delete_removesClientFromDatabase_null() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    testClient.delete();
    assertEquals(null, Client.getById(testClient.getId()));
  }

  //// setName
  @Test
  public void setName_doesNotCrashIfClientIsNotInDB_null() {
    Client testClient = new Client(1, "Taako");
    testClient.setName("Barry Bluejeans");
    assertEquals(null, Client.getById(1));
  }
  @Test
  public void setName_changesNameAppropriately_String() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    testClient.setName("Barry Bluejeans");
    assertTrue(Client.getById(testClient.getId()).getName().equals("Barry Bluejeans"));
  }

}
