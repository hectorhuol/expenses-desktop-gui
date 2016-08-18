package com.financial.analisys.expenser.desktop.gui.dto;

public class MonthExpenses {
	
	private String total;
	private String totalCreditCard;
	private String totalDebitCard;

	public String getTotalCreditCard() {
		return totalCreditCard;
	}

	public void setTotalCreditCard(String totalCreditCard) {
		this.totalCreditCard = totalCreditCard;
	}

	public String getTotalDebitCard() {
		return totalDebitCard;
	}

	public void setTotalDebitCard(String totalDebitCard) {
		this.totalDebitCard = totalDebitCard;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}