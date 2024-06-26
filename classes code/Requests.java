import java.util.*;

public class Requests extends RequestDonationList{
    public boolean validRequestDonation(RequestDonation rd, Beneficiary beneficiary){
        boolean valid = true;
        double level = 0;
        
        if(rd.getRequestEntity() instanceof Material){
            if(beneficiary.getNoPersons() == 1)
                level = ((Material)rd.getRequestEntity()).getLevel1();
            else if(beneficiary.getNoPersons()>=2 && beneficiary.getNoPersons()<=4)
                level = ((Material)rd.getRequestEntity()).getLevel2();
            else if(beneficiary.getNoPersons() > 4)
                level = ((Material)rd.getRequestEntity()).getLevel3();
                
            if( beneficiary.entityQuantityReceived(rd.getRequestID()) + rd.getQuantity() > level )
                valid = false;
            else 
                valid = true;
            
        }else if(rd.getRequestEntity() instanceof Service)
            valid = true;
        
        return valid;
    }
    
    public void add(RequestDonation rd, Organization org, Beneficiary beneficiary) throws UnavainableQuantityException, NotDeserveException{
        if(rd.getQuantity() > org.getDonation(rd.getRequestID()).getQuantity())
            throw new UnavainableQuantityException();
        else if(!validRequestDonation(rd, beneficiary))
            throw new NotDeserveException();
        else
            super.add(rd, org);
    }
    
    public void modify(RequestDonation rd, double quantity, Organization org, Beneficiary beneficiary) throws UnavainableQuantityException, NotDeserveException {
        if(quantity > org.getDonation(rd.getRequestID()).getQuantity())
            throw new UnavainableQuantityException();
        else if(!validRequestDonation(new RequestDonation(rd.getRequestEntity(), quantity), beneficiary))
            throw new NotDeserveException();
        else
            super.modify(rd, quantity);
    }
    
    public void commit(Organization org, Beneficiary beneficiary){  
        for(int i=0; i < rdEntities.size(); i++){
            try{
                if(rdEntities.get(i).getQuantity() > org.getDonation(rdEntities.get(i).getRequestID()).getQuantity())
                    throw new UnavainableQuantityException();
                else if(!validRequestDonation(rdEntities.get(i), beneficiary))
                    throw new NotDeserveException();
                else{
                    org.modifyDonation(rdEntities.get(i), org.getDonation(rdEntities.get(i).getRequestID()).getQuantity() - rdEntities.get(i).getQuantity());
                    beneficiary.addReceivedDonation(rdEntities.get(i), org);
                    System.out.println("Your request for the entity \"" + rdEntities.get(i).getRequestName() + "\" was succesfully commited in the organization");
                    rdEntities.remove(i);
                    i--;
                }
            }catch(UnavainableQuantityException nqe){
                System.out.println("The quantity you chose for the entity \"" + rdEntities.get(i).getRequestName() + "\" is not avainable");
            }catch(NotDeserveException nde){
                System.out.println("The quantity you chose for the entity \"" + rdEntities.get(i).getRequestName() + "\" exceeds the quantity you deserve");
            }
        }
    }
}