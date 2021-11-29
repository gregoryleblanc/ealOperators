package edu.ohio.inpp.acceleratorOperators;

import java.time.Duration;
// import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/optraining")
public class MainController {
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired 
    private AddressRepository addressRepository;
    @Autowired
    private CertificationRepository certificationRepository;


    @PostMapping(path = "/add")
    public @ResponseBody String addNewOperator (@RequestParam 
        String fullName,String loginName, String comments) {
            return "Operator Saved!";
        }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Operator> getAllUsers() {
        return operatorRepository.findAll();
    }

    @GetMapping(path = "/makedemooperators")
    public @ResponseBody String makeDemoOperator() {
        String[] firstNames = {"Bill", "Stewart", "Demo"};
        String[] lastNames = {"Operator 1", "Operator 2", "Operator 3"};
        String[] loginNames = {"demo1", "demo2", "demo3"};
        for (Integer i =0; i < firstNames.length; i++) {
            Operator operator = new Operator();
            // Create the operator and set the created time.
            operator.setCreated(LocalDateTime.now());
            // Save so that we can have the Id for phones, email, and address.
            operatorRepository.save(operator);
            operator.setFirstName(firstNames[i]);
            operator.setLastName(lastNames[i]);
            operator.setActive(true);
            operator.setComments("Here's a comment");
            operator.setLoginName(loginNames[i]);
            makeDemoPhones(operator.getId());
            makeDemoEmail(operator.getId());
            makeDemoAddress(operator.getId());
            operator.setUpdated(LocalDateTime.now());
            operatorRepository.save(operator);
        }
        return "Made demo users.";
    }

    public void makeDemoPhones(Integer owner) {
        String[] phones = {"(555)-111-1111", "(555)-222-2222"};
        String[] descriptions = {"Office 1", "Office 2"};
        for (int i = 0; i < phones.length; i++ ) {
            Phone phone = new Phone();
            phone.setNumber(phones[i]);
            phone.setDescription(descriptions[i]);
            phone.setOwner(owner);
            phoneRepository.save(phone);
        }
    }

    public void makeDemoEmail(Integer owner) {
        String[] emails = {"test1@ohio.edu", "test2@example.com"};
        String[] types = {"Ohio University", "Example"};
        for (int i = 0; i < emails.length; i++ ) {
            Email email = new Email();
            email.setAddress(emails[i]);
            email.setType(types[i]);
            email.setOwner(owner);
            emailRepository.save(email);
        }
    }

    public void makeDemoAddress(Integer owner) {
        String[] addresses = {"123 University Terrace, Athen OH 45701", "1 Ohio University, Athens OH 45701"};
        String[] descriptions = {"Edwards Accelerator Lab", "Main Campus Address"};
        for (int i = 0; i < addresses.length; i++) {
            Address address = new Address();
            address.setAddress(addresses[i]);
            address.setDescription(descriptions[i]);
            address.setOwner(owner);
            addressRepository.save(address);
        }
    }

    @GetMapping(path = "/makedemocertifications")
    public @ResponseBody String makeDemoCertification() {
        String[] names = {"Operator 1", "Operator 2", "Radiation Safety"};
        String[] shortNames = {"Op1", "Op2", "RAD"};
        Duration[] durations = {null, Duration.ofDays(90), Duration.ofDays(365)};
        for (int i = 0; i < names.length; i++) {
            Certification certification = new Certification();
            certification.setName(names[i]);
            certification.setShortName(shortNames[i]);
            certification.setDuration(durations[i]);
            certificationRepository.save(certification);
        }
        return "Made demo certifications.";
    }

    // @GetMapping(path = "/makedemotraining")
    // public @ResponseBody String makeDemoTrainings() {
    //     LocalDate[] trainingDate = {LocalDate.of(2010,9,21), LocalDate.of(2012,9,6)};
    //     return "Didn't make anything.";
    // }

}
