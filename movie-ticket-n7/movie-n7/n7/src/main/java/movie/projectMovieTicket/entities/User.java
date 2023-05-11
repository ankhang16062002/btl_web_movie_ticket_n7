package movie.projectMovieTicket.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private String userName;
	@Column(unique = true)
	private String email;
	private String password;
	private String phone;
	private Date dateOfBirth;
	private String gender;
	@Column(length = 50)
	private String about;

	private String role;
	private boolean enable;
	
	private String imgUrl;
	private int refundAmount;
	
	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER, mappedBy = "user")
	private List<Purchase> purchaseList;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Purchase> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(List<Purchase> purchaseList) {
		this.purchaseList = purchaseList;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", phone=" + phone + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", about=" + about
				+ ", role=" + role + ", enable=" + enable + ", imgUrl=" + imgUrl + ", refundAmount=" + refundAmount
				+ ", purchaseList=" + purchaseList + "]";
	}

	
	
}
