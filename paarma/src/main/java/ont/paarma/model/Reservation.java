package ont.paarma.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Reservation {
	private int id;
	@NotNull
	@Min(1)
	private int userId;
	private Date startDate;
	private java.sql.Date startDateSql;
	private Date endDate;
	private java.sql.Date endDateSql;
	@NotNull
    @Min(1)
	private int numPeriods;
	@NotEmpty
    @Length(min = 1, max = 50)
	private String table;
	private BigDecimal tablePrice = new BigDecimal("50.00");
	private BigDecimal totalPrice;
	
	public Reservation(){
		super();
	}
	
	public Reservation(int userId, Date startDate, int numPeriods, String table){
		this.userId = userId;
		this.startDate = startDate;
		this.numPeriods = numPeriods;
		this.table = table; 
		this.setEndDate(this.getStartDate(), numPeriods);
		this.setStartDateSql(startDate);
		this.setEndDateSql(this.getEndDate());
		this.setTotalPrice(tablePrice);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int id) {
		this.userId = id;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public java.sql.Date getStartDateSql() {
		return startDateSql;
	}
	
	public void setStartDateSql(Date startDate) {	
		this.startDateSql = new java.sql.Date(startDate.getTime());	
	}
	
	public void setStartDateFromSql(java.sql.Date dateFromDb) {	
		this.startDate = new Date(dateFromDb.getTime());	
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        //String dateInString = "07/06/2013";
//
//        try {
//
//            Date date = formatter.parse(startDateStr);
//            System.out.println("start date:"+startDate);
//            this.startDate = date;
//        } catch (ParseException e) {
//            e.printStackTrace();
//            }
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date startDate, int numPeriods) {
		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		int daysToAdd = numPeriods * 7;
		c.add(Calendar.DATE, daysToAdd);
//		String output = sdf.format(c.getTime());
		System.out.println("end date:"+c.getTime());
		this.endDate = c.getTime();
	}
		
	public java.sql.Date getEndDateSql() {
		return endDateSql;
	}

	public void setEndDateSql(Date endDate) {
		this.endDateSql = new java.sql.Date(endDate.getTime());	
	}

	public void setEndDateFromSql(java.sql.Date dateFromDb) {	
		System.out.println("setEndDateFromSql method db date value:"+dateFromDb);
		this.endDate = new Date(dateFromDb.getTime());	
	}
	
	public int getNumPeriods() {
		return numPeriods;
	}
	
	public void setNumPeriods(int numPeriods) {
		this.numPeriods = numPeriods;
	}
	
	public String getTable() {
		return table;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public BigDecimal getTablePrice() {
		return tablePrice;
	}
	
	public void setTablePrice(BigDecimal tablePrice) {
		this.tablePrice = tablePrice;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(BigDecimal tablePrice) {	
		this.totalPrice = tablePrice.multiply(new BigDecimal(numPeriods));
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", userId=" + userId + ", startDate=" + startDate + ", startDateSql="
				+ startDateSql + ", endDate=" + endDate + ", endDateSql=" + endDateSql + ", numPeriods=" + numPeriods
				+ ", table=" + table + ", tablePrice=" + tablePrice + ", totalPrice=" + totalPrice + "]";
	}
}
