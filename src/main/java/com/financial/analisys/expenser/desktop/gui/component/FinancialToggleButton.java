package com.financial.analisys.expenser.desktop.gui.component;

import javax.swing.JToggleButton;

public class FinancialToggleButton extends JToggleButton {

	private static final long serialVersionUID = 1L;

	private String id;

	public FinancialToggleButton(String id, String name) {
		this.id = id;
		super.setText(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}