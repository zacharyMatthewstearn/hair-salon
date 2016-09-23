import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Stylist {
  // Member Variables
  private int id;
  private String name;

  // Constructor
  public Stylist(String _name) {
    name = _name;
  }

  // Static Methods
  public static List<Stylist> getAll() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM stylists")
        .executeAndFetch(Stylist.class);
    }
  }
  public static Stylist getById(int _id) {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM stylists WHERE id = :id")
        .addParameter("id", _id)
        .executeAndFetchFirst(Stylist.class);
    }
  }

  // Overrides
  @Override
  public boolean equals(Object _otherStylist) {
    if(!(_otherStylist instanceof Stylist))
      return false;
    Stylist newStylist = (Stylist) _otherStylist;
      return id == newStylist.getId() &&
             name.equals(newStylist.getName());
  }

  // Getters
  public int getId() {
    return id;
  }
  public String getName() {
    return name;
  }

  // Setters
  public void setName(String _newName) {
    name = _newName;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE stylists SET name = :name WHERE id = :id", true)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  // Other Non-Static Methods
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO stylists (name) VALUES (:name)", true)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM stylists WHERE id = :id")
        .addParameter("id", id)
        .executeUpdate();
    }
  }
  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM clients WHERE stylistId = :id")
        .addParameter("id", id)
        .executeAndFetch(Client.class);
    }
  }
}
