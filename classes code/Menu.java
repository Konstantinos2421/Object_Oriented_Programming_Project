import java.util.*;
import java.util.concurrent.TimeUnit;

public class Menu{
    Organization org;
    Scanner scan = new Scanner(System.in);
    
    public Menu(Organization org){
        this.org = org;
    }
    
    public void run(){
        System.out.println("Insert your phone number:");
        String phone = scan.nextLine();
        
        try{
            Long.parseLong(phone);
        }catch(NumberFormatException nfe){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            run();
        }
        
        if(phone.equals(org.getAdmin().getUserPhone())){
            System.out.print("\f");
            showAdminOptions(org.getAdmin());
        }
        else if(org.donatorExists(phone)){
            System.out.print("\f");
            showDonatorOptions(org.getDonator(phone));
        }
        else if(org.beneficiaryExists(phone)){
            System.out.print("\f");
            showBeneficiaryOptions(org.getBeneficiary(phone));
        }
        else{
            System.out.println("\fNo account is associated with this phone number. Do you want to register in the organization? (y/n)");
            String answer = scan.nextLine();
            
            if(answer.equals("n"))
                System.exit(0);
            else if(answer.equals("y")){
                System.out.println("\fWhat type of user do you want to be?");
                System.out.println("1. Donator");
                System.out.println("2. Beneficiary");
                String typeOfUser = scan.nextLine();
                
                if (typeOfUser.equals("1")){
                    System.out.println("\fInsert your name:");
                    String name = scan.nextLine();
                    org.insertDonator(new Donator(name,phone));
                    System.out.print("\f");
                    showDonatorOptions(org.getDonator(phone));
                }else if(typeOfUser.equals("2")){
                    System.out.println("\fInsert your name:");
                    String name = scan.nextLine();
                    System.out.println("Insert the number of your family members:");
                    
                    int noPersons = 0;
                    try{
                        noPersons = Integer.parseInt(scan.nextLine());
                    }catch(NumberFormatException nfe){
                        System.out.println("\fYou gave an invalid answer");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        run();
                    }
                    
                    if(noPersons <= 0){
                        System.out.println("\fYou gave an invalid answer");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        run();
                    }
                
                    org.insertBeneficiary(new Beneficiary(name,phone,noPersons));
                    System.out.print("\f");
                    showBeneficiaryOptions(org.getBeneficiary(phone));
                }else{
                    System.out.println("\fYou gave an invalid answer");
                    try{
                        TimeUnit.SECONDS.sleep(5);
                    }catch (InterruptedException ie){}
                    System.out.print("\f");
                    run();
                }
            }
            else{
                System.out.println("\fYou gave an invalid answer");
                try{
                    TimeUnit.SECONDS.sleep(5);
                }catch (InterruptedException ie){}
                System.out.print("\f");
                run();
            }
        }
    }
    
    public void showDonatorOptions(Donator donator){
        System.out.println("Hello " + donator.getUserName()); 
        System.out.println("Phone: " + donator.getUserPhone());  
        System.out.println("Organization: " + org.getOrganizationName()); 
        System.out.println("Account Type: Donator\n"); 
        
        System.out.println("1. Add Offer");    
        System.out.println("2. Show Offers");   
        System.out.println("3. Commit"); 
        System.out.println("4. Back");   
        System.out.println("5. Logout");   
        System.out.println("6. Exit"); 
        
        String option = scan.nextLine();
        try{
            switch(option){
                case "1":
                    donatorOption1(donator);
                    break;
                    
                case "2":
                    donatorOption2(donator);
                    break;
                
                case "3":
                    donatorOption3(donator);
                    break;
                
                case "4":
                    System.out.print("\f");
                    run();
                    break;
                
                case "5":
                    System.out.print("\f");
                    run();
                    break;
                
                case "6":
                    System.exit(0);
                    break;
                
                default:
                    throw new InvalidAnswerException();
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showDonatorOptions(donator);
        }
    }
    
    public void donatorOption1(Donator donator){
        try{
            System.out.print("\f");
            org.listEntities();
            System.out.println("\nChoose an entity to see its details");
            String option = scan.nextLine();
            int i;
            try{
                i = Integer.parseInt(option);
            }catch(NumberFormatException nfe){
                throw new InvalidAnswerException();
            }
                    
            System.out.print("\f");
            try{
                System.out.println(org.getEntity(i).toString());
            }catch(IndexOutOfBoundsException ioobe){
                throw new InvalidAnswerException();
            }
            System.out.println("\nDo you want to offer this entity? (y/n)");
            option = scan.nextLine();
            switch(option){
                case "y":
                    if(org.getEntity(i) instanceof Material){
                        System.out.println("\fInsert the quantity you want to offer");
                        option = scan.nextLine();
                        double quantity = 0;
                        try{
                            quantity = Double.parseDouble(option);
                        }catch(NumberFormatException nfe){
                            throw new InvalidAnswerException();
                        }
                        donator.addOffer(new RequestDonation(org.getEntity(i), quantity), org);
                        System.out.println("\fThe offer was added successfully in your offer list");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showDonatorOptions(donator);
                    }
                    else if(org.getEntity(i) instanceof Service){
                        System.out.println("\fInsert the number of the hours you want to offer for this service");
                        option = scan.nextLine();
                        double quantity = 0;
                        try{
                            quantity = Double.parseDouble(option);
                        }catch(NumberFormatException nfe){
                            throw new InvalidAnswerException();
                        }
                        donator.addOffer(new RequestDonation(org.getEntity(i), quantity), org);
                        System.out.println("\fThe offer was added successfully in your offer list");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showDonatorOptions(donator);
                    }
                    break;
                            
                case "n":
                    System.out.print("\f");
                    showDonatorOptions(donator);
                    break;
                            
                default:
                    throw new InvalidAnswerException();
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showDonatorOptions(donator);
        }
    }
    
    public void donatorOption2(Donator donator){
        try{
            if(donator.offersListEmpty()){
                System.out.println("\fYour donation list is empty");
                try{
                    TimeUnit.SECONDS.sleep(5);
                }catch (InterruptedException ie){}
                System.out.print("\f");
                showDonatorOptions(donator);
            }
            else{
                System.out.println("\fOffers:");
                donator.monitorOffers();
                System.out.println("\na. Choose a supply line");
                System.out.println("b. Delete all offers");
                System.out.println("c. Commit");
                System.out.println("d. Back");
                String option = scan.nextLine();
                        
                switch(option){
                    case "a":
                        System.out.println("\fOffers:");
                        donator.monitorOffers();
                        System.out.println("\nChoose the supply line you want to modify");
                        option = scan.nextLine();
                        int i = 0;
                        try{
                            i = Integer.parseInt(option);
                        }catch(NumberFormatException nfe){
                            throw new InvalidAnswerException();
                        }
                        
                        try{
                            donator.get(i);
                        }catch(IndexOutOfBoundsException ioobe){
                            throw new InvalidAnswerException();
                        }
                        
                        System.out.println("\fa. Delete Offer");
                        System.out.println("b. Modify Offer");
                        System.out.println("c. Return to menu");
                        System.out.println("d. Back");
                        option = scan.nextLine();
                                
                        switch(option){
                            case "a":
                                try{
                                    donator.removeOffer(donator.get(i).getRequestID());
                                }catch(IndexOutOfBoundsException ioobe){
                                    throw new InvalidAnswerException();
                                }
                                System.out.println("\fThe offer was deleted successfully");
                                try{
                                    TimeUnit.SECONDS.sleep(5);
                                }catch (InterruptedException ie){}
                                System.out.print("\f");
                                showDonatorOptions(donator);
                                break;
                                        
                            case "b":
                                System.out.println("\fInsert the new quantity of the entity");
                                option = scan.nextLine();
                                double quantity = 0;
                                try{
                                    quantity = Double.parseDouble(option);
                                }catch(NumberFormatException nfe){
                                    throw new InvalidAnswerException();
                                }
                                        
                                try{
                                    donator.modifyOffer(donator.get(i), quantity);
                                }catch(IndexOutOfBoundsException ioobe){
                                    throw new InvalidAnswerException();
                                }
                                System.out.println("\fThe offer was modified successfully");
                                try{
                                    TimeUnit.SECONDS.sleep(5);
                                }catch (InterruptedException ie){}
                                System.out.print("\f");
                                showDonatorOptions(donator);
                                break;
                                
                            case "c":
                                System.out.print("\f");
                                showDonatorOptions(donator);
                                break;
                                
                            case "d":
                                System.out.print("\f");
                                donatorOption1(donator);
                                break;
                                        
                            default:
                                throw new InvalidAnswerException();
                        }
                        break;
                        
                    case "b":
                        donator.resetOffers();
                        System.out.println("\fYour offers were deleted successfully");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showDonatorOptions(donator);
                        break;
                                
                    case "c":
                        donator.commitOffers(org);
                        System.out.println("\fYour offers were added in the organization successfully");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showDonatorOptions(donator);
                        break;
                                
                    case "d":
                        System.out.print("\f");
                        showDonatorOptions(donator);
                        break;
                            
                    default:
                        throw new InvalidAnswerException();      
                }
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showDonatorOptions(donator);
        }
    }
    
    public void donatorOption3(Donator donator){
        if(donator.offersListEmpty()){
            System.out.println("\fYour donation list is empty.");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showDonatorOptions(donator);
        }
        else{
            donator.commitOffers(org);
            System.out.println("\fYour donations were added in the organization successfully.");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showDonatorOptions(donator);
        }    
    }
    
    public void showBeneficiaryOptions(Beneficiary beneficiary){
        System.out.println("Hello " + beneficiary.getUserName()); 
        System.out.println("Phone: " + beneficiary.getUserPhone());  
        System.out.println("Family persons number: " + beneficiary.getNoPersons());
        System.out.println("Organization: " + org.getOrganizationName()); 
        System.out.println("Account Type: Beneficiary\n"); 
        
        System.out.println("1. Add Request");    
        System.out.println("2. Show Requests");   
        System.out.println("3. Commit"); 
        System.out.println("4. Back");   
        System.out.println("5. Logout");   
        System.out.println("6. Exit"); 
        
        String option = scan.nextLine();
        try{
            switch(option){
                case "1":
                    beneficiaryOption1(beneficiary);
                    break;
                
                case "2":
                    beneficiaryOption2(beneficiary);
                    break;
                
                case "3":
                    beneficiaryOption3(beneficiary);
                    break;
                
                case "4":
                    System.out.print("\f");
                    run();
                    break;
                
                case "5":
                    System.out.print("\f");
                    run();
                    break;
                
                case "6":
                    System.exit(0);
                    break;
                
                default:
                    throw new InvalidAnswerException();
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showBeneficiaryOptions(beneficiary);
        }
    }
    
    public void beneficiaryOption1(Beneficiary beneficiary){
        try{
            System.out.print("\f");
            org.listEntities();
            System.out.println("\nChoose an entity to see its details");
            String option = scan.nextLine();
            int i = 0;
            try{
                i = Integer.parseInt(option);
            }catch(NumberFormatException nfe){
                throw new InvalidAnswerException();
            }
                    
            System.out.print("\f");
            try{
                System.out.println(org.getEntity(i).toString());
            }catch(IndexOutOfBoundsException ioobe){
                throw new InvalidAnswerException();
            }
                                    
            System.out.println("\nDo you want make a request for this entity? (y/n)");
            option = scan.nextLine();
            switch(option){
                case "y":
                    if(org.getEntity(i) instanceof Material){
                        try{
                            org.getDonation(org.getEntity(i).getEntityID());
                        }catch(IndexOutOfBoundsException ioobe){
                            System.out.println("\fThe quantity of the entity you chose is 0 so you cannot add it in your request list");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showBeneficiaryOptions(beneficiary);
                        }
                        
                        System.out.println("\fInsert the quantity of the entity you want to receive");
                        option = scan.nextLine();
                        double quantity = 0;
                        try{
                            quantity = Double.parseDouble(option);
                        }catch(NumberFormatException nfe){
                            throw new InvalidAnswerException();
                        }
                            
                        try{
                            beneficiary.addRequest(new RequestDonation(org.getEntity(i), quantity), org);
                        }catch(UnavainableQuantityException uqe){
                            System.out.println("\fThe quantity you asked is not avainable in the organization");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showBeneficiaryOptions(beneficiary);
                        }catch(NotDeserveException nde){
                            System.out.println("\fThe quantity you asked exceeds the quantity you deserve");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showBeneficiaryOptions(beneficiary);
                        }
                                
                        System.out.println("\fThe request was added successfully in your request list");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showBeneficiaryOptions(beneficiary);
                    }
                    else if(org.getEntity(i) instanceof Service){
                        try{
                            org.getDonation(org.getEntity(i).getEntityID());
                        }catch(IndexOutOfBoundsException ioobe){
                            System.out.println("\fThere are no avainable donations for the service you chose so you cannot add it in your request list");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showBeneficiaryOptions(beneficiary);
                        }
                            
                        System.out.println("\fInsert the hours of the service you want to receice");
                        option = scan.nextLine();
                        double quantity = 0;
                        try{
                            quantity = Double.parseDouble(option);
                        }catch(NumberFormatException nfe){
                            throw new InvalidAnswerException();
                        }
                            
                        try{
                            beneficiary.addRequest(new RequestDonation(org.getEntity(i), quantity), org);
                        }catch(UnavainableQuantityException uqe){
                            System.out.println("\fThe hours you asked are not avainable in the organization for this service");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showBeneficiaryOptions(beneficiary);
                        }catch(NotDeserveException nde){
                            System.out.println("\fThe hours of the service you asked exceeds the hours you deserve");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showBeneficiaryOptions(beneficiary);
                        }
                            
                        System.out.println("\fThe request was added successfully in your request list");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showBeneficiaryOptions(beneficiary);
                    }
                    break;  
                            
                case "n":
                    System.out.print("\f");
                    showBeneficiaryOptions(beneficiary);
                    break;
                            
                default:
                    throw new InvalidAnswerException();
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showBeneficiaryOptions(beneficiary);
        }
    }
    
    public void beneficiaryOption2(Beneficiary beneficiary){
        try{
            if(beneficiary.requestsListEmpty()){
                System.out.println("\fYour request list is empty");
                try{
                    TimeUnit.SECONDS.sleep(5);
                }catch (InterruptedException ie){}
                System.out.print("\f");
                showBeneficiaryOptions(beneficiary);
                }
            else{
                System.out.println("\fRequests");
                beneficiary.monitorRequests();
                System.out.println("\na. Choose a supply line");
                System.out.println("b. Delete all requests");
                System.out.println("c. Commit");
                System.out.println("d. Back");
                String option = scan.nextLine();
                        
                switch(option){
                    case "a":
                        System.out.println("\fRequests");
                        beneficiary.monitorRequests();
                        System.out.println("\nChoose the supply line you want to modify");
                        option = scan.nextLine();
                        int i=0;
                        try{
                            i = Integer.parseInt(option);
                        }catch(NumberFormatException nfe){
                            throw new InvalidAnswerException();
                        }
                        
                        try{
                            beneficiary.get(i);
                        }catch(IndexOutOfBoundsException ioobe){
                            throw new InvalidAnswerException();
                        }
                        
                        System.out.println("\fa. Delete Offer");
                        System.out.println("b. Modify Offer");
                        System.out.println("c. Return to menu");
                        System.out.println("d. Back");
                        option = scan.nextLine();
                                
                        switch(option){
                            case "a":
                                try{
                                    beneficiary.removeRequest(beneficiary.get(i).getRequestID());
                                }catch(IndexOutOfBoundsException ioobe){
                                    throw new InvalidAnswerException();
                                }
                                System.out.println("\fThe request was deleted successfully");
                                try{
                                    TimeUnit.SECONDS.sleep(5);
                                }catch (InterruptedException ie){}
                                System.out.print("\f");
                                showBeneficiaryOptions(beneficiary);
                                break;
                                    
                            case "b":
                                System.out.println("\fInsert the new quantity of the entity");
                                option = scan.nextLine();
                                double quantity = 0;
                                try{
                                    quantity = Double.parseDouble(option);
                                }catch(NumberFormatException nfe){
                                    throw new InvalidAnswerException();
                                }
                                        
                                try{
                                    try{
                                        beneficiary.modifyRequest(beneficiary.get(i),quantity, org);
                                    }catch(IndexOutOfBoundsException ioobe){
                                        throw new InvalidAnswerException();
                                    }
                                    System.out.println("\fRequest was modified successfully");
                                    try{
                                        TimeUnit.SECONDS.sleep(5);
                                    }catch (InterruptedException ie){}
                                    System.out.print("\f");
                                    showBeneficiaryOptions(beneficiary);
                                }catch (UnavainableQuantityException uqe){
                                    System.out.println("\fThe quantity you asked is not avainable");
                                    try{
                                        TimeUnit.SECONDS.sleep(5);
                                    }catch (InterruptedException ie){}
                                    System.out.print("\f");
                                    showBeneficiaryOptions(beneficiary);
                                }catch(NotDeserveException nde){
                                    System.out.println("\fThe quantity you asked exceeds the quantity you deserve");
                                    try{
                                        TimeUnit.SECONDS.sleep(5);
                                    }catch (InterruptedException ie){}
                                    System.out.print("\f");
                                    showBeneficiaryOptions(beneficiary);
                                }
                                break;
                                
                            case "c":
                                System.out.print("\f");
                                showBeneficiaryOptions(beneficiary);
                                break;
                            
                            case "d":
                                System.out.print("\f");
                                beneficiaryOption2(beneficiary);
                                break;
                                                
                            default:
                                throw new InvalidAnswerException();
                        }
                        break;
                            
                    case "b":
                        beneficiary.resetRequests();
                        System.out.println("\fYour requests were deleted successfully");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showBeneficiaryOptions(beneficiary);
                        break;
                            
                    case "c":
                        System.out.print("\f");
                        beneficiary.commitRequests(org);
                            
                        System.out.println("\nPress any key to continue");
                        scan.nextLine();
                        System.out.print("\f");
                        showBeneficiaryOptions(beneficiary);
                        break;
                        
                    case "d":
                        System.out.print("\f");
                        showBeneficiaryOptions(beneficiary);
                        break;
                            
                    default:
                        throw new InvalidAnswerException();
                }
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showBeneficiaryOptions(beneficiary);
        }
    }
    
    public void beneficiaryOption3(Beneficiary beneficiary){
        if(beneficiary.requestsListEmpty()){
            System.out.println("\fYour request list is empty");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showBeneficiaryOptions(beneficiary);
        }
        else{
            System.out.print("\f");
            beneficiary.commitRequests(org);
                        
            System.out.println("\nPress any key to continue");
            scan.nextLine();
            System.out.print("\f");
            showBeneficiaryOptions(beneficiary);
        }
    }
    
    public void showAdminOptions(Admin admin){
        System.out.println("Hello " + admin.getUserName()); 
        System.out.println("Phone: " + admin.getUserPhone());  
        System.out.println("Organization: " + org.getOrganizationName()); 
        System.out.println("Account Type: Admin\n"); 
        
        System.out.println("1. View");    
        System.out.println("2. Monitor Organization");  
        System.out.println("3. Edit Entity List");
        System.out.println("4. Back"); 
        System.out.println("5. Logout");   
        System.out.println("6. Exit");    
        
        String option = scan.nextLine();
        try{
            switch(option){
                case "1":
                    adminOption1(admin);
                    break;
                
                case "2":
                    adminOption2(admin);
                    break;
                    
                case "3":
                    adminOption3(admin);
                    break;
                
                case "4":
                    System.out.print("\f");
                    run();
                    break;
                
                case "5":
                    System.out.print("\f");
                    run();
                    break;
                
                case "6":
                    System.exit(0);
                    break;
                
                default:
                    throw new InvalidAnswerException();
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showAdminOptions(admin);
        }
    }
    
    public void adminOption1(Admin admin){
        try{
            System.out.print("\f");
            org.listEntities();
            System.out.println("\nChoose an entity to see its details or press 'b' to go back to menu");
            String option = scan.nextLine();
            
            if(option.equals("b")){
                System.out.print("\f");
                showAdminOptions(admin);
            }
            
            int i = 0;
            try{
                i = Integer.parseInt(option);
            }catch(NumberFormatException nfe){
                throw new InvalidAnswerException();
            }
                    
            System.out.print("\f");
            try{
                System.out.println(org.getEntity(i).toString());
            }catch(IndexOutOfBoundsException ioobe){
                throw new InvalidAnswerException();
            }
            System.out.println("\nPress any key to continue");
            scan.nextLine();
            System.out.print("\f");
            showAdminOptions(admin);
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showAdminOptions(admin);
        }
    }
    
    public void adminOption2(Admin admin){
        try{
            System.out.println("\fa. List Beneficiaries");
            System.out.println("b. List Donators");
            System.out.println("c. Reset Beneficiaries Received Lists");
            System.out.println("d. Back");
            String option = scan.nextLine();
                    
            switch(option){
                case "a":
                    System.out.print("\f");
                    org.listBeneficiaries();
                    System.out.println("\nChoose a beneficiary");
                    option = scan.nextLine();
                    int i = 0;
                    try{
                        i = Integer.parseInt(option);
                    }catch(NumberFormatException nfe){
                        throw new InvalidAnswerException();
                    }
                    System.out.println("\fReceived Donations:");
                    try{
                        org.getBeneficiary(i).monitorReceivedDonations();
                    }catch(IndexOutOfBoundsException ioobe){
                        throw new InvalidAnswerException();
                    }
                    System.out.println("a. Delete beneficiary's received list");
                    System.out.println("b. Remove beneficiary");
                    System.out.println("c. Return to menu");
                    System.out.println("d. Back");
                    option = scan.nextLine();
                            
                    switch(option){
                        case "a":
                            org.getBeneficiary(i).resetReceivedList();
                            System.out.println("\fBenefiary's received list was deleted successfully.");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showAdminOptions(admin);
                            break;
                                
                        case "b":
                            org.removeBeneficiary(org.getBeneficiary(i));
                            System.out.println("\fBenefiary was removed successfully.");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showAdminOptions(admin);
                            break;
                                
                        case "c":
                            System.out.print("\f");
                            showAdminOptions(admin);
                            break;
                                    
                        case "d":
                            System.out.print("\f");
                            adminOption1(admin);
                            break;
                                    
                        default:
                            throw new InvalidAnswerException();
                    }
                    break;
                            
                case "b":
                    System.out.print("\f");
                    org.listDonators();
                    System.out.println("\nChose a donator");
                    option = scan.nextLine();
                    i = Integer.parseInt(option);
                    System.out.print("\f");
                    try{
                        org.getDonator(i).monitorOffers();
                    }catch(IndexOutOfBoundsException ioobe){
                        throw new InvalidAnswerException();
                    }
                    System.out.println("a. Remove Donator");
                    System.out.println("b. Return to menu");
                    System.out.println("c. Back");
                    option = scan.nextLine();
                            
                    switch(option){
                        case "a":
                            org.removeDonator(org.getDonator(i));
                            System.out.println("\fDonator was removed successfully.");
                            try{
                                TimeUnit.SECONDS.sleep(5);
                            }catch (InterruptedException ie){}
                            System.out.print("\f");
                            showAdminOptions(admin);
                            break;
                                
                        case "b":
                            System.out.print("\f");
                            showAdminOptions(admin);
                            break;
                            
                        case "c":
                            System.out.print("\f");
                            adminOption1(admin);
                            break;
                                
                        default:
                            throw new InvalidAnswerException();
                    }
                    break;
                        
                case "c":
                    org.resetAllReceivedLists();
                    System.out.println("\fAll received list were deleted successfully.");
                    try{
                        TimeUnit.SECONDS.sleep(5);
                    }catch (InterruptedException ie){}
                    System.out.print("\f");
                    showAdminOptions(admin);
                    break;
                            
                case "d":
                    System.out.print("\f");
                    showAdminOptions(admin);
                    break;
                        
                default:
                    throw new InvalidAnswerException();
                    }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showAdminOptions(admin);
        }
    }
    
    public void adminOption3(Admin admin){
        System.out.println("\fa. Add Entity");
        System.out.println("b. Remove Entity");
        System.out.println("c. Back");
        String option = scan.nextLine();
        
        try{
            switch(option){
                case "a":
                    System.out.println("\fInsert the type of the entity(material/service):");
                    option = scan.nextLine();
                    switch(option){
                        case "material":
                            System.out.println("Insert the name of the entity:");
                            String name = scan.nextLine();
                            System.out.println("Insert a description for the entity:");
                            String description = scan.nextLine();
                            System.out.println("Insert an id for the entity:");
                            int id = 0;
                            try{
                                id = Integer.parseInt(scan.nextLine());
                            }catch(NumberFormatException nfe){
                                throw new InvalidAnswerException();
                            }
                            System.out.println("Insert the level1 quantity of the entity:");
                            double level1 = 0;
                            try{
                                level1 = Double.parseDouble(scan.nextLine());
                            }catch(NumberFormatException nfe){
                                throw new InvalidAnswerException();
                            }
                            System.out.println("Insert the level2 quantity of the entity:");
                            double level2 = 0;
                            try{
                                level1 = Double.parseDouble(scan.nextLine());
                            }catch(NumberFormatException nfe){
                                throw new InvalidAnswerException();
                            }
                            System.out.println("Insert the level3 quantity of the entity:");
                            double level3 = 0;
                            try{
                                level1 = Double.parseDouble(scan.nextLine());
                            }catch(NumberFormatException nfe){
                                throw new InvalidAnswerException();
                            }
                            
                            try{
                                org.addEntity(new Material(name, description, id, level1, level2, level3));
                                System.out.println("\fThe entity was added in the organization successfully");
                                try{
                                    TimeUnit.SECONDS.sleep(5);
                                }catch (InterruptedException ie){}
                                System.out.print("\f");
                                showAdminOptions(admin);
                            }catch(AlreadyExistsException aee){
                                System.out.println("\fThe entity you inserted already exists in the organization");
                                try{
                                    TimeUnit.SECONDS.sleep(5);
                                }catch (InterruptedException ie){}
                                System.out.print("\f");
                                showAdminOptions(admin);
                            }
                            break;
                            
                        case "service":
                            System.out.println("Insert the name of the entity:");
                            name = scan.nextLine();
                            System.out.println("Insert a description for the entity:");
                            description = scan.nextLine();
                            System.out.println("Insert an id for the entity:");
                            try{
                                id = Integer.parseInt(scan.nextLine());
                            }catch(NumberFormatException nfe){
                                throw new InvalidAnswerException();
                            }
                            
                            try{
                                org.addEntity(new Service(name, description, id));
                                System.out.println("\fThe entity was added in the organization successfully");
                                try{
                                    TimeUnit.SECONDS.sleep(5);
                                }catch (InterruptedException ie){}
                                System.out.print("\f");
                                showAdminOptions(admin);
                            }catch(AlreadyExistsException aee){
                                System.out.println("\fThe entity you inserted already exists in the organization");
                                try{
                                    TimeUnit.SECONDS.sleep(5);
                                }catch (InterruptedException ie){}
                                System.out.print("\f");
                                showAdminOptions(admin);
                            }
                            break;
                        
                        default:
                            throw new InvalidAnswerException();
                    }
                    break;
                    
                case "b":
                    System.out.print("\f");
                    org.listEntities();
                    System.out.println("\nChoose the entity you want to remove or press 'b' to go back to menu");
                    option = scan.nextLine();
                    if(option.equals("b")){
                        System.out.print("\f");
                        showAdminOptions(admin);
                    }
                    else{
                        int i = 0;
                        try{
                            i = Integer.parseInt(option);
                        }catch(NumberFormatException nfe){
                            throw new InvalidAnswerException();
                        }
                        
                        try{
                            org.removeEntity(org.getEntity(i));
                        }catch(IndexOutOfBoundsException ioobe){
                            throw new InvalidAnswerException();
                        }
                        System.out.println("\fThe entity removed of the organization successfully");
                        try{
                            TimeUnit.SECONDS.sleep(5);
                        }catch (InterruptedException ie){}
                        System.out.print("\f");
                        showAdminOptions(admin);
                    }
                    break;
                    
                case "c":
                    System.out.print("\f");
                    showAdminOptions(admin);
                    break;
                
                default:
                    throw new InvalidAnswerException();
            }
        }catch(InvalidAnswerException iae){
            System.out.println("\fYou gave an invalid answer");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException ie){}
            System.out.print("\f");
            showAdminOptions(admin);
        }
    }
}