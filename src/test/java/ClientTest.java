import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void save_savesClientToDatabase_true() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    assertTrue(testClient.getId() > 0);
  }

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

  @Test
  public void getById_fetchesCorrectClientFromDB_Client() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    assertTrue(Client.getById(testClient.getId()).equals(testClient));
  }

  @Test
  public void delete_removesClientFromDatabase_null() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    testClient.delete();
    assertEquals(null, Client.getById(testClient.getId()));
  }

}
