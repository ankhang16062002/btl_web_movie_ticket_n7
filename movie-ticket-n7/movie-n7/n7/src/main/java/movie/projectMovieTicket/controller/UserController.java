package movie.projectMovieTicket.controller;


import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import movie.projectMovieTicket.entities.Movieticket;
import movie.projectMovieTicket.entities.Purchase;
import movie.projectMovieTicket.entities.User;
import movie.projectMovieTicket.helper.Message;
import movie.projectMovieTicket.repo.MovieticketRepository;
import movie.projectMovieTicket.repo.PurchaseRepository;
import movie.projectMovieTicket.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;  //dependence injection

	@Autowired
	private MovieticketRepository movieticketRepository;   //dependence injection

	@Autowired
	private PurchaseRepository purchaseRepository;   // dependence injection
	
	

	// Khi có 1 request đến /user, nó sẽ lấy ra user đang đăng nhập hiện tại bằng principal đến gán vào model chung

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();   // Lấy ra email
		User user = userRepository.getUserByUserName(userName);
		model.addAttribute("user", user);
	}

	// GET request trả về trang dashbroad của người dùng

	@RequestMapping("/dashboard")
	public String userDashboard(Model model, Principal principal) {
		model.addAttribute("title", "Trang chủ người dùng");
		return "normaluser/user_dashboard";
	}

	
	
	// GET danh sách phim để show cho user

	
	@GetMapping("/show-upcoming-movie/{page}")
	public String showMovies(@PathVariable("page") Integer page, Model m) {

		LocalDate localDate = LocalDate.now();
		Date date = Date.valueOf(localDate);

		Pageable pageable = PageRequest.of(page, 5);
		Page<Movieticket> movietickets = this.movieticketRepository.findByDateGreaterThanEqualOrderByDateAsc(date,
				pageable);

		m.addAttribute("title", "Danh sách phim");
		m.addAttribute("movietickets", movietickets);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", movietickets.getTotalPages());

		return "normaluser/show_upcoming_movie";
	}

	// GET để hiện thị ra trang mua vé, chứa id để tìm ra phim

	@RequestMapping("/buy-movie-ticket/{id}")
	public String buyMovieTicket(@PathVariable("id") Integer id, Model model) {

		Movieticket movieticket = this.movieticketRepository.getById(id);
		model.addAttribute("title", movieticket.getMovieName() + " Mua vé");
		model.addAttribute("movieticket", movieticket);

		return "normaluser/buy_movie_ticket";
	}

	// POST để đặt vé, chứa id để tìm ra phim, với purchase truyển lên

	@PostMapping("/process-buy-ticket/{id}")
	public String processBuyTicket(@ModelAttribute Purchase purchase, @PathVariable("id") Integer id,
			Principal principal, Model model, HttpSession session) {

		Movieticket movieticket = this.movieticketRepository.getById(id);
		model.addAttribute("title", movieticket.getMovieName() + " Mua vé");
		model.addAttribute("movieticket", movieticket);

		try {

			if (purchase.getQuantity() <= 0) {
				throw new Exception("No seat has been selected.");
			}

			if (movieticket.getSeatRemaining() - purchase.getQuantity() < 0) {
				throw new Exception("We don't have enough seats.");
			}

			movieticket.setSeatRemaining(movieticket.getSeatRemaining() - purchase.getQuantity());

			String userName = principal.getName();
			User user = this.userRepository.getUserByUserName(userName);

			purchase.setUser(user);
			purchase.setMovieticket(movieticket);

			user.getPurchaseList().add(purchase);
			movieticket.getSoldList().add(purchase);

			purchaseRepository.save(purchase);   // lưu vào csdl
			session.setAttribute("message", new Message("Đặt vé thành công", "success"));

			return "normaluser/buy_movie_ticket";

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Có lỗi. " + e.getMessage(), "danger"));
			return "normaluser/buy_movie_ticket";
		}

	}

	
	
	// GET danh sách phim đã đặt
	
	
	@GetMapping("/show-user-purchase/{page}")
	public String showUserPurchase(@PathVariable("page") Integer page, Model m, Principal principal) {
		
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		
		LocalDate localDate = LocalDate.now(); 
		Date date = Date.valueOf(localDate);
		int status = 1;  //check truong status = 1

		Pageable pageable = PageRequest.of(page, 5);
		// get phim đã mua
		Page<Purchase> purchaseList = purchaseRepository.getPurchaseByUserAndMovieDateAndPaymentStatus(user.getUserId(), date, status, pageable );
		
		m.addAttribute("title", "Phim đã mua");
		m.addAttribute("purchaseList", purchaseList);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", purchaseList.getTotalPages());

		return "normaluser/show_user_purchase";

	}
	
	

	// GET để lấy lịch sử giao dịch
	
	
	@GetMapping("/show-user-transaction/{page}")
	public String showUserTransaction(@PathVariable("page") Integer page, Model m, Principal principal) {
		
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);	

		Pageable pageable = PageRequest.of(page, 5);
		Page<Purchase> purchaseList = purchaseRepository.getPurchaseByUser(user.getUserId(),pageable );
		
		m.addAttribute("title", "My Movie Watchlist");
		m.addAttribute("purchaseList", purchaseList);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", purchaseList.getTotalPages());
		

		return "normaluser/show_user_transaction";

	}
	
	
	// GET Lấy thông tin người dùng
	
	@GetMapping("/user-profile")
	public String userProfile(Model model) {
		model.addAttribute("Title", "Thông tin user");
		return "normaluser/user_profile";
	}
}
