package movie.projectMovieTicket.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import movie.projectMovieTicket.entities.User;
import movie.projectMovieTicket.helper.Message;
import movie.projectMovieTicket.repo.UserRepository;

@Controller
public class HomeController {
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;  //end code dependence injection
	
	
	@Autowired 
	private UserRepository userRepository;  // user repo dependence injection
	
	// GET request show ra trang chủ
	@RequestMapping("/")
	public String home(Model model, Principal principal) {
		model.addAttribute("title", "Trang chủ");
		
		return "home";
	}
	
	// GET request show ra trang đăng ký
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Đăng ký");
		model.addAttribute("user", new User() );
		return "signup";
	}
	
	// POST request để thực hiện đăng ký
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, 
			Model model, HttpSession session ) {
		
		try {
			user.setRole("ROLE_USER");
			user.setEnable(true);
			user.setImgUrl("User Profile Default Photo.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			this.userRepository.save(user);
			model.addAttribute("user", new User() );
			session.setAttribute("message", new Message("Đăng ký thành công.", "alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Có lỗi xảy ra." + e.getMessage(), "alert-danger"));
			return "signup";
		}
		
	}
	
	
	// GET request để hiển thị ra trang đăng nhập
	@GetMapping("/login")
	public String customLogin(Model model) {
		model.addAttribute("title", "Đăng nhập");
		return "login";
		
	}
	
	
}
