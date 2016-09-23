import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    // GET Requests
    get("/",(request,response)->{
      return new ModelAndView(createModel(), layout);
    },new VelocityTemplateEngine());

    get("/stylists/:id",(request,response)->{
      int stylistId = Integer.parseInt(request.params(":id"));
      Stylist.setSelectedId(stylistId);
      Client.setSelectedId(0);
      return new ModelAndView(createModel(), layout);
    },new VelocityTemplateEngine());

    get("/clients/:id",(request,response)->{
      Client.setSelectedId(Integer.parseInt(request.params(":id")));
      return new ModelAndView(createModel(), layout);
    },new VelocityTemplateEngine());

    // POST Requests
    post("/stylists/update",(request,response)->{
      String nameInput = request.queryParams("stylistNameInput");
      switch (request.queryParams("hiddenStylistNameButton")) {
        case "Add":
          if(!nameInput.equals("")) {
            Stylist newStylist = new Stylist(nameInput);
            newStylist.save();
            Stylist.setSelectedId(newStylist.getId());
            Client.setSelectedId(0);
          }
          break;
        case "Update":
          if(!nameInput.equals("") && Stylist.getSelectedId() > 0)
            Stylist.getById(Stylist.getSelectedId()).setName(nameInput);
          break;
        case "Delete":
          if(Stylist.getSelectedId() > 0)
            Stylist.getById(Stylist.getSelectedId()).delete();
            Stylist.setSelectedId(0);
            Client.setSelectedId(0);
          break;
        default:
          System.out.println("Something has gone horribly wrong!!!");
      }
      return new ModelAndView(createModel(), layout);
    },new VelocityTemplateEngine());

    post("/clients/update",(request,response)->{
      int selectedStylistId = Stylist.getSelectedId();
      String nameInput = request.queryParams("clientNameInput");
      switch (request.queryParams("hiddenClientNameButton")) {
        case "Add":
          if(!nameInput.equals("")) {
            Client newClient = new Client(selectedStylistId, nameInput);
            newClient.save();
            Client.setSelectedId(newClient.getId());
          }
          break;
        case "Update":
          if(!nameInput.equals("") && Client.getSelectedId() > 0)
            Client.getById(Client.getSelectedId()).setName(nameInput);
          break;
        case "Delete":
          if(Client.getSelectedId() > 0)
            Client.getById(Client.getSelectedId()).delete();
            Client.setSelectedId(0);
          break;
        default:
          System.out.println("Something has gone horribly wrong!!!");
      }
      return new ModelAndView(createModel(), layout);
    },new VelocityTemplateEngine());
  }

  private static Map<String,Object> createModel() {
    Map<String,Object> model = new HashMap<>();
    model.put("selectedStylistId", Stylist.getSelectedId());
    model.put("selectedClientId", Client.getSelectedId());
    model.put("stylists", Stylist.getAll());
    if (Stylist.getSelectedId() > 0)
      model.put("clients", Stylist.getById(Stylist.getSelectedId()).getClients());
    else
      model.put("clients", new ArrayList<Client>());
    model.put("template", "templates/index.vtl");
    return model;
  }
}
