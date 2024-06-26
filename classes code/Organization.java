import java.util.*;

public class Organization{
    private String name;
    private Admin admin;
    
    private RequestDonationList currentDonations = new RequestDonationList();   
    private ArrayList<Entity> entityList = new ArrayList();                     
    private ArrayList<Donator> donatorList = new ArrayList();                
    private ArrayList<Beneficiary> beneficiaryList = new ArrayList();  
    
    public Organization(String name){
        this.name = name;
    }
    
    public void setAdmin(Admin admin){
        this.admin = admin;
    }
    
    public Admin getAdmin(){
        return this.admin;
    }
    
    public String getOrganizationName(){
        return this.name;
    }
    
    public void addEntity(Entity entity) throws AlreadyExistsException{
        if(entityExists(entity))
            throw new AlreadyExistsException();
        else
            entityList.add(entity);
    }
    
    public void removeEntity(Entity entity){
        for(int i=0; i < entityList.size(); i++){
            if(entityList.get(i) == entity){
                entityList.remove(i);
                break;
            }
        }
    }
    
    public boolean entityExists(Entity entity){
        boolean found = false;
        for(int i=0; i < entityList.size(); i++){
            if(entity.getEntityID() == entityList.get(i).getEntityID()){
                found = true;
                break;
            }
        }
        
        return found;
    }
    
    public Entity getEntity(int i){
        i--;
        return entityList.get(i);
    }
    
    public void insertDonator(Donator donator){
        try{
            
            if( donatorExists(donator.getUserPhone()) )
                throw new AlreadyExistsException();
            donatorList.add(donator);
            
        }catch (AlreadyExistsException aee){
            System.out.println("This account already exists in the organization");
        }
    }
    
    public void removeDonator(Donator donator){
        for(int i=0; i < donatorList.size(); i++){
            if(donatorList.get(i) == donator){
                donatorList.remove(i);
                break;
            }
        }
    }
    
    public boolean donatorExists(String phone){
        try{
            return donatorList.contains(getDonator(phone));
        }catch(NullPointerException npe){
            return false;
        }
    }
    
    public Donator getDonator(String phone){
        int i=0;
        
        for(i=0; i < donatorList.size(); i++){
            if(donatorList.get(i).getUserPhone().equals(phone))
                break;
        }
        
        try{
            return donatorList.get(i);
        }catch(IndexOutOfBoundsException ioobe){
            return null;
        }
    }
    
    public Donator getDonator(int i){
        i--;
        return donatorList.get(i);
    }
    
    public void insertBeneficiary(Beneficiary beneficiary){
        try{
            
            if( beneficiaryExists(beneficiary.getUserPhone()) )
                throw new AlreadyExistsException();
            beneficiaryList.add(beneficiary);
            
        }catch(AlreadyExistsException aee){
            System.out.println("This account already exists in the organization");
        }
    }
    
    public void removeBeneficiary(Beneficiary beneficiary){
        for(int i=0; i < beneficiaryList.size(); i++){
            if(beneficiaryList.get(i) == beneficiary){
                beneficiaryList.remove(i);
                break;
            }
        }
    }
    
    public boolean beneficiaryExists(String phone){
        try{
            return beneficiaryList.contains(getBeneficiary(phone));
        }catch(NullPointerException npe){
            return false;
        }
    }
    
    public Beneficiary getBeneficiary(String phone){
        int i=0;
        
        for(i=0; i < beneficiaryList.size(); i++){
            if(beneficiaryList.get(i).getUserPhone().equals(phone))
                break;
        }
        
        try{
            return beneficiaryList.get(i);
        }catch(IndexOutOfBoundsException ioobe){
            return null;
        }
    }
    
    public Beneficiary getBeneficiary(int i){
        i--;
        return beneficiaryList.get(i);
    }
    
    public void listEntities(){
        int i;
        int j;
        
        System.out.println("1. Material (" + getMaterialsQuantity() + ")");
        for(i=0; i < entityList.size(); i++){
            if(entityList.get(i) instanceof Material){
                try{
                    j = i + 1;
                    System.out.println(j + ") " + entityList.get(i).getEntityName() + "(quantity: " + getDonation(entityList.get(i).getEntityID()).getQuantity() + ")");
                }catch(IndexOutOfBoundsException ioobe){
                    j = i + 1;
                    System.out.println(j + ") " + entityList.get(i).getEntityName() + "(quantity: 0.0)");
                }
            }
        }
        
        System.out.println("\n\n2. Services (" + getServicesQuantity() + ")");
        for(i=0; i < entityList.size(); i++){
            if(entityList.get(i) instanceof Service){
                try{
                    j = i + 1;
                    System.out.println(j + ") " + entityList.get(i).getEntityName() + "(quantity: " + getDonation(entityList.get(i).getEntityID()).getQuantity() + ")");
                }catch(IndexOutOfBoundsException ioobe){
                    j = i + 1;
                    System.out.println(j + ") " + entityList.get(i).getEntityName() + "(quantity: 0.0)");
                }
            }
        }
    }
    
    public void listBeneficiaries(){
        int i = 1;
        System.out.println("Beneficiaries:");
        for(Beneficiary beneficiary : beneficiaryList){
            System.out.println(i + ") " + beneficiary.getUserName() + "(phone: " + beneficiary.getUserPhone() + ")");
            i++;
        }
    }
    
    public void listDonators(){
        int i = 1;
        System.out.println("Donators:");
        for(Donator donator : donatorList){
            System.out.println(i + ") " + donator.getUserName() + "(phone: " + donator.getUserPhone() + ")");
            i++;
        }
    }
    
    public int getMaterialsQuantity(){
        int i = 0;
        for(Entity entity : entityList){
            if(entity instanceof Material)
                i++;
        }
        return i;
    }
    
    public int getServicesQuantity(){
        int i = 0;
        for(Entity entity : entityList){
            if(entity instanceof Service)
                i++;
        }
        return i;
    }
    
    public RequestDonation getDonation(int id){
        return currentDonations.get(id);
    }
    
    public void addDonation(RequestDonation rd){
        currentDonations.add(rd, this);
    }
    
    public void removeDonation(int id){
        currentDonations.remove(id);
    }
    
    public void modifyDonation(RequestDonation rd, double quantity){
        currentDonations.modify(rd, quantity);
    }
    
    public void monitorDonations(){
        currentDonations.monitor();
    }
    
    public void resetDonations(){
        currentDonations.reset();
    }
    
    public void resetAllReceivedLists(){
        for(int i=0; i < beneficiaryList.size(); i++)
            beneficiaryList.get(i).resetReceivedList();
    }
}