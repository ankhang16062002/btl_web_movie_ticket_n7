package movie.projectMovieTicket.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import movie.projectMovieTicket.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	// tạo 1 query custom để lấy người dùng bởi email
	@Query("select u from User u where u.email = :email") public User
	getUserByUserName(@Param("email") String email);
}
