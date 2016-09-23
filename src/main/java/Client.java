import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Client {
  // Member Variables
  private int id;
  private int stylistId;
  private String name;

  // Constructor
  public Client(int _stylistId, String _name) {
    stylistId = _stylistId;
    name = _name;
  }

  // Static Methods
  public static List<Client> getAll() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients")
        .executeAndFetch(Client.class);
    }
  }
  public static Client getById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients WHERE id = :id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Client.class);
    }
  }

  // Overrides
  @Override
  public boolean equals(Object _otherClient) {
    if(!(_otherClient instanceof Client))
      return false;
    Client newClient = (Client) _otherClient;
      return id == newClient.getId() &&
             stylistId == newClient.getStylistId() &&
             name.equals(newClient.getName());
  }

  // Getters
  public int getId() {
    return id;
  }
  public int getStylistId() {
    return stylistId;
  }
  public String getName() {
    return name;
  }

  // Setters
  public void setStylistId(int _newStylistId) {
    stylistId = _newStylistId;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE clients SET stylistId = :stylistId WHERE id = :id", true)
      .addParameter("stylistId", stylistId)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
  public void setName(String _newName) {
    name = _newName;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE clients SET name = :name WHERE id = :id", true)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  // Other Non-Static Methods
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO clients (stylistId, name) VALUES (:stylistId, :name)", true)
      .addParameter("stylistId", stylistId)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM clients WHERE id = :id")
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
