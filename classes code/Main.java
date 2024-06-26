public class Main{
    public static void main(String[] args){
        Organization org = new Organization("Care Club"); 
        
        Material sugar = new Material("sugar", "",201569 , 5, 10, 15);
        Material rice = new Material("rice", "",208457 , 8, 16, 24);
        Material milk = new Material("milk", "",204731 , 10, 18, 26);
        
        Service MedicalSupport = new Service("Medical Support", "", 108598);
        Service NurserySupport = new Service("Nursery Support", "", 107541);
        Service BabySitting = new Service("Baby Sitting", "", 104218);
        
        try{
            org.addEntity(sugar);
            org.addEntity(rice);
            org.addEntity(milk);
            org.addEntity(MedicalSupport);
            org.addEntity(NurserySupport);
            org.addEntity(BabySitting);
        }catch(AlreadyExistsException aee){}
        
        Admin admin = new Admin("Thomas Rivera", "6945832184");
        Beneficiary beneficiary1 = new Beneficiary("Sophia Perez", "6982654838", 1);
        Beneficiary beneficiary2 = new Beneficiary("Paul Smith", "6938215646", 4);
        Donator donator = new Donator("Stella Davis", "6978963562");
        
        org.setAdmin(admin);
        org.insertBeneficiary(beneficiary1);
        org.insertBeneficiary(beneficiary2);
        org.insertDonator(donator);
        
        donator.addOffer(new RequestDonation(sugar, 6), org);
        donator.addOffer(new RequestDonation(rice, 10), org);
        donator.addOffer(new RequestDonation(milk, 8), org);
        donator.addOffer(new RequestDonation(MedicalSupport, 2), org);
        donator.addOffer(new RequestDonation(BabySitting, 4), org);
        donator.commitOffers(org);
        
        try{
            beneficiary1.addRequest(new RequestDonation(rice, 5), org);
            beneficiary1.addRequest(new RequestDonation(sugar, 2), org);
            beneficiary1.addRequest(new RequestDonation(MedicalSupport, 1), org);
        }catch(UnavainableQuantityException uqe){
        }catch(NotDeserveException nde){}
            
        
        Menu menu = new Menu(org);
        menu.run();
    }
}