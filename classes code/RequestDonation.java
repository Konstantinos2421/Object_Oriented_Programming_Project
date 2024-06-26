public class RequestDonation{
    private Entity entity;
    private double quantity;
    
    public RequestDonation(Entity entity, double quantity){
        this.entity = entity;
        this.quantity = quantity;
    }
    
    public int getRequestID(){
        return this.entity.getEntityID();
    }
    
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }
    
    public double getQuantity(){
        return this.quantity;
    }
    
    public String getRequestName(){
        return this.entity.getEntityName();
    }
    
    public Entity getRequestEntity(){
        return this.entity;
    }
}