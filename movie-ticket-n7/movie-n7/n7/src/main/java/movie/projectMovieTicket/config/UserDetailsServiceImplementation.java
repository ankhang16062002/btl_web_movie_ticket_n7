package movie.projectMovieTicket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import movie.projectMovieTicket.entities.User;
import movie.projectMovieTicket.repo.UserRepository;

public class UserDetailsServiceImplementation implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user  =  userRepository.getUserByUserName(username);

		if(user==null) {
			throw new UsernameNotFoundException("Could Not Found User !!");
		}

		CustomUserDetails customerUserDetails = new CustomUserDetails(user);
		return customerUserDetails;
	}

}

//Lớp UserDetailsServiceImplementation này có nhiệm vụ cung cấp một cách để 
//Spring Security tìm kiếm và lấy thông tin người dùng từ cơ sở dữ liệu hoặc 
//nguồn dữ liệu khác để xác thực và quản lý quyền hạn của người dùng trong ứng dụng của bạn.