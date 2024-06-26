public class Beneficiary extends User{
    private int noPersons = 1;
    private RequestDonationList receivedList = new RequestDonationList();
    private Requests requestsList = new Requests();
    
    public Beneficiary(String name, String phone, int noPersons){
        this.name = name;
        this.phone = phone;
        this.noPersons = noPersons;
    }
    
    public int getNoPersons(){
        return this.noPersons;
    }
    
    public double entityQuantityReceived(int id){
        try{
            return receivedList.get(id).getQuantity();
        }catch(IndexOutOfBoundsException e){
            return 0;
        }
    }
    
    public RequestDonation getRequest(int id){
        return requestsList.get(id);
    }
    
    public RequestDonation get(int i){
        return requestsList.getListItem(i);
    }
    
    public void addRequest(RequestDonation rd, Organization org) throws UnavainableQuantityException, NotDeserveException{
        requestsList.add(rd, org, this);
    }
    
    public void addReceivedDonation(RequestDonation rd, Organization org){
        receivedList.add(rd, org);
    }
    
    public void removeRequest(int id){
        requestsList.remove(id);
    }
    
    public void modifyRequest(RequestDonation rd,double quantity, Organization org) throws UnavainableQuantityException, NotDeserveException{
        requestsList.modify(rd, quantity, org, this);
    }
    
    public void monitorRequests(){
        requestsList.monitor();
    }
    
    public void monitorReceivedDonations(){
        receivedList.monitor();
    }
    
    public void resetRequests(){
        requestsList.reset();
    }
    
    public void resetReceivedList(){
        receivedList.reset();
    }
    
    public void commitRequests(Organization org){
        requestsList.commit(org, this);
    }
    
    public boolean requestsListEmpty(){
        return requestsList.isEmpty();
    }
}