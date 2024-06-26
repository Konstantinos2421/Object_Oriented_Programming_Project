import java.util.*;

public class RequestDonationList{
    protected ArrayList<RequestDonation> rdEntities = new ArrayList();
    
    public RequestDonation get(int id){
        int i = 0;
        
        for(i=0; i < rdEntities.size(); i++){
            if(rdEntities.get(i).getRequestID() == id)
                break;
        }
        
        return rdEntities.get(i);
    }
    
    public RequestDonation getListItem(int i){
        i--;
        return rdEntities.get(i);
    }
    
    public void add(RequestDonation rd, Organization org){
        boolean found = false;
        
        try{
            
            if(!org.entityExists(rd.getRequestEntity()))
                throw new NotFoundException();
            
            for(int i=0; i < rdEntities.size(); i++){
                if(rdEntities.get(i).getRequestID() == rd.getRequestID()){
                    rdEntities.get(i).setQuantity(rd.getQuantity() + rdEntities.get(i).getQuantity());
                    found = true;
                    break;
                }
            }
            if(found == false)
                rdEntities.add(rd);
                
        } catch(NotFoundException nfe){
            System.out.println("This entity is not distributed by the organization");
        }
    }
    
    public void remove(int id){
        for(int i=0; i < rdEntities.size(); i++){
            if(rdEntities.get(i).getRequestID() == id){
                rdEntities.remove(i);
                break;
            }
        }
    }
    
    public void modify(RequestDonation rd, double quantity){
        for(int i=0; i < rdEntities.size(); i++){
            if(rdEntities.get(i).getRequestID() == rd.getRequestID()){
                rdEntities.get(i).setQuantity(quantity);
                break;
            }
        }
    }
    
    public void monitor(){
        int i =1;
        for(RequestDonation rd : rdEntities){
            System.out.println(i + ") " + rd.getRequestName() + "(quantity: " + rd.getQuantity() + ")");
            i++;
        }
    }
    
    public void reset(){
        rdEntities.clear();
    }
    
    public boolean isEmpty(){
        return rdEntities.isEmpty();
    }
}