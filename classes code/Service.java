public class Service extends Entity{
    public Service(String name, String description, int id){
        this.name = name;
        this.description = description;
        this.id = id;
    }
    
    public String getDetails(){
        return "Type of Entity: Service";
    }
}