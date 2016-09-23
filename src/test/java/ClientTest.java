import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void save_savesClientToDatabase() {
    Client testClient = new Client(1, "Taako");
    testClient.save();
    assertTrue(testClient.getId() > 0);
  }
}
