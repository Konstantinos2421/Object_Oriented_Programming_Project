public class Donator extends User{
    private Offers offersList = new Offers();
    
    public Donator(String name, String phone){
        this.name = name;
        this. phone = phone;
    }
    
    public RequestDonation getOffer(int id){
        return offersList.get(id);
    }
    
    public RequestDonation get(int i){
        return offersList.getListItem(i);
    }
    
    public void addOffer(RequestDonation rd, Organization org){
        offersList.add(rd, org);
    }
    
    public void removeOffer(int id){
        offersList.remove(id);
    }
    
    public void modifyOffer(RequestDonation rd, double quantity){
        offersList.modify(rd, quantity);
    }
    
    public void monitorOffers(){
        offersList.monitor();
    }
    
    public void resetOffers(){
        offersList.reset();
    }
    
    public void commitOffers(Organization org){
        offersList.commit(org);
    }
    
    public boolean offersListEmpty(){
        return offersList.isEmpty();
    }
}