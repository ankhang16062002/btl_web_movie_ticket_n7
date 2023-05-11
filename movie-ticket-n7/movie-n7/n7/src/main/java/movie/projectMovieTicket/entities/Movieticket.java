package movie.projectMovieTicket.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "MOVIETICKET")
public class Movieticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int movieId;
	private String movieName;
	private String genre;
	private String day;
	private String startTime;
	
	private Date date;
	private Date duration;
	
	private int totalSeat;
	private int seatRemaining;
	private int ticketPrize;
	
	private String movieImage;
	
	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER, mappedBy = "movieticket")
	private List<Purchase> soldList;

	public Movieticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Purchase> getSoldList() {
		return soldList;
	}

	public void setSoldList(List<Purchase> soldList) {
		this.soldList = soldList;
	}

	@Override
	public String toString() {
		return "Movieticket [movieId=" + movieId + ", movieName=" + movieName + ", genre=" + genre + ", day=" + day
				+ ", startTime=" + startTime + ", date=" + date + ", duration=" + duration + ", totalSeat=" + totalSeat
				+ ", seatRemaining=" + seatRemaining + ", ticketPrize=" + ticketPrize + ", movieImage=" + movieImage
				+ "]";
	}
	
	

	
	
}
