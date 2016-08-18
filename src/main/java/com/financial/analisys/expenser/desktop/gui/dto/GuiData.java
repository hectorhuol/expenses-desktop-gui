package com.financial.analisys.expenser.desktop.gui.dto;

public class GuiData {
	
	private Object [][] data;
	private Object [] columnNames;
	public Object[][] getData() {
		return data;
	}
	public void setData(Object[][] data) {
		this.data = data;
	}
	public Object[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(Object[] columnNames) {
		this.columnNames = columnNames;
	}
}
