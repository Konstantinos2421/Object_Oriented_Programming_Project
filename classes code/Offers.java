import java.util.*;

public class Offers extends RequestDonationList{
    
    public void commit(Organization org){
        for(int i=0; i < rdEntities.size(); i++)
            org.addDonation(rdEntities.get(i));
        
        super.reset();
    }
    
}