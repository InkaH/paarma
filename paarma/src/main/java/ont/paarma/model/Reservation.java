package ont.paarma.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private int numPeriods;
	private int table;
	private BigDecimal tablePrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
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
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", numPeriods="
				+ numPeriods + ", table=" + table + ", tablePrice=" + tablePrice + "]";
	}
}
