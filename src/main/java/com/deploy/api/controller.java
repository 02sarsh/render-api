package com.deploy.api;

import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.razorpay.*;
@Controller
public class controller {

	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private contactrepo conRepo;
	
	@Autowired
	private myorderrepo myorder;
	
	 @Value("${upload.dir}")
	    private String uploadDir;
	 
	 @Autowired
	 private sendemail Sendmail;
	
	@GetMapping("/home")
	
	public String test() {
	
//		user User=new user();
//		
//		User.setName("sarvesh");
//		User.setRole("admin");
//		User.setAbout("administrator");
//		User.setEmail("02sarb@gmail.com");
//		User.setEnabled(true);
//		User.setPassword("72777772222");
//		
//		userrepo.save(User);
		
		
		
        return "home";
	}

	@GetMapping("/signup")
	public String signup(Model m) {
		
	  m.addAttribute("User",new user());	
      return "signup";
		
	}
    
	@GetMapping("/about")
	public String about() {
		
      return "norm/javascript";
		
	}
    
	@GetMapping("/login")
	public String login() {
		
      return "login";
		
	}
    
	@PostMapping("/Register")
	public String register(@Valid @ModelAttribute("User") user User) {
		
		System.out.println(User);
		
		userrepo.save(User);
		return "signup";
	}
	
	@PostMapping("/logindata")
	public String logindata(@RequestParam String email,HttpSession session) {
		
		System.out.println(email);
		user User=userrepo.getuserbyemail(email);
		System.out.println(User);
		
		session.setAttribute("User", User);
		
		if(User!=null) {
			return "logindata";
		}
		
		return "login";
	}
	
	@GetMapping("/addcontact")
	public String addcontact(Model m) {
		
		m.addAttribute("contact",new contact());
		
		return "norm/addcontact";
		
	}
	
	@PostMapping("/process-contact")
	
	public String processcontact(@ModelAttribute("contact") contact Contact,HttpSession session,@RequestParam("file")MultipartFile file) throws IOException {
		
	    user User=(user) session.getAttribute("User");
	    Contact.setUser(User);
	    
	    byte[] bytes=file.getBytes();
	    
	    
	   // Path path=Paths.get(uploadDir+File.separator+file.getOriginalFilename());
	    
	   // Path path1= Files.write(path,bytes);
	   // System.out.println(path1);
		System.out.println(User);
		System.out.println(Contact);
		
	//	Contact.setProfileimage(file.getOriginalFilename());
		User.getContacts().add(Contact);
		
		userrepo.save(User);
		return "norm/addcontact";
	}
	
	
	@GetMapping("/showcontact/{page}")
	public String showcontacts(@PathVariable("page")Integer page ,Model m,HttpSession session) {
		
		user User=(user) session.getAttribute("User");
		System.out.println(User);
		
		Pageable pageable=PageRequest.of(page,2);
		Page<contact>Contacts=conRepo.findallcontacts(User.getId(),pageable);
		
		m.addAttribute("contactt",Contacts);
		m.addAttribute("currentpage",page);
		
		
		int total=Contacts.getTotalPages();
		List<Integer>pages=new ArrayList<Integer>();
		
		for(int i=1;i<=total;i++) {
			pages.add(i);
			}
		System.out.println(pages);
		m.addAttribute("totalpages",pages);
		
		int lastindex=pages.size()-1;
		int last=pages.get(lastindex);
		System.out.println(last);
		
		m.addAttribute("last",last);
		
		return "norm/showcontact";
		
	}
	
	@GetMapping("/contact/{cId}")
	public String getcontact(@PathVariable("cId")Integer cId,Model m) {
		
		Optional<contact> Contactopt=conRepo.findById(cId);
		contact Contact=Contactopt.get();
		System.out.println(cId);
		System.out.println(Contact);
		
		m.addAttribute("c",Contact);
		return "norm/viewcontact";
		
	}
	
	@GetMapping("/deletecontact/{cId}")
	
	public String deletecontact(@PathVariable("cId")Integer cId,Model m) {
		
		System.out.println(cId);

	
		Optional<contact> Contactopt=conRepo.findById(cId);
		contact Contact=Contactopt.get();
		
		System.out.println(Contact);
	   // Contact.setUser(null);
	   // this.userrepo.deleteById(2);
	   
		
		this.conRepo.deletecontact(cId);
	   this.conRepo.deleteById(cId);	
	
		return "redirect:/showcontact/0";
		
	}
	
	@PostMapping("/update-form/{cId}")
	public String updateform(@PathVariable("cId")Integer cId,Model m) {
		

		Optional<contact> Contactopt=conRepo.findById(cId);
		contact Contact=Contactopt.get();
		m.addAttribute("contact",Contact);
		System.out.println(cId);
	
		return"norm/updateform";
	}
	
@PostMapping("/update-contact/{cId}")
	
	public String updatecontact(@PathVariable("cId")Integer cId,@ModelAttribute("contact") contact Contact,HttpSession session,@RequestParam("file")MultipartFile file) throws IOException {
		
	

	Optional<contact> Contactopt=conRepo.findById(cId);
	contact Contactold=Contactopt.get();	
	
	   user User=(user) session.getAttribute("User");
	   Contact.setUser(User);
	    System.out.println(cId);
	    System.out.println(file.getOriginalFilename());
		System.out.println("running");
		System.out.println(Contact);
		
        if(!file.isEmpty()) { 
		byte[] bytes=file.getBytes();
	    
	    
	    Path path=Paths.get(uploadDir+File.separator+file.getOriginalFilename());
	    
	    Path path1= Files.write(path,bytes);
		Contact.setProfileimage(file.getOriginalFilename());
        }
        else { System.out.println("no file uploaded");
        Contact.setProfileimage(Contactold.getProfileimage());
        }
	//	User.getContacts().add(Contact);
		
	//	userrepo.save(User);
		
		this.conRepo.save(Contact);
		
		return "redirect:/showcontact/0";
	}
	
@GetMapping("/search") 
public String search(@RequestParam("query")String Query,Model m){
	
	System.out.println(Query);
	List<contact>Contacts=this.conRepo.findByNameContaining(Query);
	System.out.println(Contacts);
	m.addAttribute("contac",Contacts);
	
	return "norm/result";
		
}
     @GetMapping("/change-password")
	public String chngepass(HttpSession session,Model m) {
    	 user User=(user) session.getAttribute("User");
    	// String str = Objects.toString(User);
    	 System.out.println(User.getName());
    	 m.addAttribute("user",User);
    	 
		return"norm/change-password";
	}
	
     @GetMapping("/processpass")
	public String processpass(HttpSession session,@RequestParam("oldpass")String oldpass,
			@RequestParam("newpass")String newpass,@RequestParam("newpass2")String newpass2) {
		
    	 user User=(user) session.getAttribute("User");
    	 String pass=User.getPassword();
    	 System.out.println(pass);
    	 
    	 if(pass.equals(oldpass) && newpass.equals(newpass2)) {
    		 
    		 User.setPassword(newpass);
    		 this.userrepo.save(User);
    		 return "success";
    	 }
    	 
    	 else if(!pass.equals(oldpass)) {
    		 new Throwable("old password wrong");
    		 return "redirect:/norm/change-password";
    	 }
    	 
    	 else {
    		 new Throwable("password mismatch error");
    		return "redirect:/norm/change-password";
    	 }
     } 
     
     @GetMapping("/password-reset")
		public String passreset() {
			
    	 
       	return"norm/password-reset";
			
		}
     
     @GetMapping("/send-otp")
     public String sendotp(@RequestParam("email")String email,Model m,HttpSession session) {
			
 	      user User= this.userrepo.getuserbyemail(email);
 	      session.setAttribute("User", User);
             System.out.println(User);
 	      if(User==null) { 	 
 	    	  String Message="wrong email-id";
 	    	  m.addAttribute("msg",Message);
 	      return"norm/password-reset";
		}
           
 	      else{
 	    	  
 	    	  Random random = new Random();
 	         int otp = 1000 + random.nextInt(9000);
 	         String otp1=String.valueOf(otp);
 	         System.out.println("Your OTP is: " + otp);
 	    	session.setAttribute("OTP", otp1);
 	    	
 	    	 this.Sendmail.sendEmail(otp1,"your otp", User.getEmail(), "coolsampatpatil@gmail.com");
 	    	   return"norm/enter-otp"; 	  
 	      }
 	      
     }
     
    
      @GetMapping("/verify")
      public String verifyotp(HttpSession session,@RequestParam("otp2")String otp2) {
    	  
	     String otp= (String) session.getAttribute("OTP");
    	  if(otp.equalsIgnoreCase(otp2))
	         return"norm/changepassword";
    	 
    	  else
    		  return"fail";
    	  
      }

      @GetMapping("/savepass")
		public String passsave(HttpSession session,@RequestParam("newpass")String password) {
			
  	 user User=(user) session.getAttribute("User");
  	 User.setPassword(password);
  	 this.userrepo.save(User);
  	 
     	return"success";
			
		}
  
      @PostMapping("/create_order")
      @ResponseBody
      public String createorder(@RequestBody Map<String,Object>data) throws RazorpayException {
    	 
    	  System.out.println(data);
    	  
    	  int amount=Integer.parseInt(data.get("amount").toString());
    	  RazorpayClient client=new RazorpayClient("rzp_test_y9PcfcR3YTRpHL", "ZTVV1wH7sNZHTYwz55QvGdnu");
    	  
    	  JSONObject orderRequest = new JSONObject();
    	  orderRequest.put("amount",amount);
    	  orderRequest.put("currency","INR");
    	  orderRequest.put("receipt", "receipt#1");
    	  
    	  Order order = client.Orders.create(orderRequest);
    	  System.out.println(order);
    	  
    	  user User=this.userrepo.getById(1);
    	  
    	  order Order=new order();
    	  
    	  Order.setAmount(order.get("amount")+"");
    	  Order.setOrderId(order.get("id"));
    	  Order.setReceipt(order.get("receipt"));
    	  Order.setStatus("created");
    	  Order.setUser(User);
    	  
    	  this.myorder.save(Order);
    	  
    	  return order.toString();
    	  
      }
      
      @PostMapping("/update_order")
      @ResponseBody
      public ResponseEntity<?>order(@RequestBody Map<String,Object> data){
    	  
        System.out.println(data);
    	  order myorder=this.myorder.findByOrderId(data.get("order_id").toString());
         System.out.println(myorder); 
        myorder.setOrderId(data.get("payment_id").toString());
        myorder.setStatus(data.get("status").toString());
        
        this.myorder.save(myorder);
        
        return ResponseEntity.ok(Map.of("msg","updated"));
      
      
      }
      
}


