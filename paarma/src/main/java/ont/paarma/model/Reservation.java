package ont.paarma.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Reservation {
	private int id;
	private int userId;
	private String startDate;
	private String endDate;
	private int numPeriods;
	private int table;
	private BigDecimal tablePrice = new BigDecimal(50);
	private BigDecimal totalPrice;
	
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getNumPeriods() {
		return numPeriods;
	}
	public void setNumPeriods(int numPeriods) {
		this.numPeriods = numPeriods;
	}
	public int getTable() {
		return table;
	}
	public void setTable(int table) {
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
	public void setTotalPrice() {
		
		this.totalPrice = tablePrice.multiply(new BigDecimal(numPeriods));
	}
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", userId=" + userId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", numPeriods=" + numPeriods + ", table=" + table + ", tablePrice=" + tablePrice + ", totalPrice="
				+ totalPrice + "]";
	}
}
