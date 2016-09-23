import org.sql2o.*;

public class Client {
  private int id;
  private int stylistId;
  private String name;

  public Client(int _stylistId, String _name) {
    stylistId = _stylistId;
    name = _name;
  }

  public int getId() {
    return id;
  }
  public int getStylistId() {
    return stylistId;
  }
  public String getName() {
    return name;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO clients (stylistId, name) VALUES (:stylistId, :name)", true)
      .addParameter("stylistId", stylistId)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }
}
