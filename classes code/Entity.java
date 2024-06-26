abstract class Entity{
    protected String name;
    protected String description;
    protected int id;
    
    public String getEntityInfo(){
        return "Name: " + this.name + "\nDescription: " + this.description + "\nID: " + this.id;
    }
    
    abstract String getDetails();
    
    public String toString(){
        return "Entity Info:\n" + this.getEntityInfo() + "\n" +this.getDetails();
    }
    
    public int getEntityID(){
        return this.id;
    }
    
    public String getEntityName(){
        return this.name;
    }
    
    public String getEntityDescription(){
        return this.description;
    }
}