package movie.projectMovieTicket.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PURCHASE")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int purchaseId;
	private int quantity;
	private String  transactionId;
	private String transactionNumber;
	private int paymentStatus;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private Movieticket movieticket;

	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", quantity=" + quantity + ", transactionId=" + transactionId
				+ ", transactionNumber=" + transactionNumber + ", paymentStatus=" + paymentStatus + ", user=" + user
				+ ", movieticket=" + movieticket + "]";
	}

	
	
	
}
