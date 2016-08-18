package com.financial.analisys.expenser.desktop.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.financial.analisys.expenser.desktop.gui.controller.Controller;
import com.financial.analisys.expenser.desktop.gui.util.GUIUtils;

public class CardsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel cardPanel;
	private JLabel cardValueLabel;
	private JTextField cardValueField;
	private JToggleButton creditButton;
	private JToggleButton debitButton;
	private ButtonGroup cardTypeGroup;

	private JScrollPane scrollPane;
	private JTable cardsTable;

	private JButton addCard;

	private Controller controller;

	public CardsDialog(Controller controller) {
		this.controller = controller;
	}

	public void createAndShowDialog() {
		Font buttonFont = new Font("Verdana", Font.BOLD, 40);
		Font dataFont = new Font("Verdana", Font.BOLD, 40);
		Font labelFont = new Font("Verdana", Font.BOLD, 20);
		Font toggleFont = new Font("Verdana", Font.BOLD, 15);

		cardPanel = new JPanel(new GridLayout(0, 2));
		cardValueLabel = new JLabel("Card Name:");
		cardValueLabel.setFont(labelFont);
		cardValueField = new JTextField();
		cardValueField.setFont(dataFont);
		cardValueField.setForeground(Color.BLUE);
		debitButton = new JToggleButton("DEBIT");
		debitButton.setFont(toggleFont);
		creditButton = new JToggleButton("CREDIT");
		creditButton.setFont(toggleFont);
		cardTypeGroup = new ButtonGroup();
		cardTypeGroup.add(creditButton);
		cardTypeGroup.add(debitButton);
		cardPanel.add(cardValueLabel);
		cardPanel.add(cardValueField);
		cardPanel.add(creditButton);
		cardPanel.add(debitButton);

		scrollPane = new JScrollPane();
		cardsTable = new JTable(10, 2);
		scrollPane.setViewportView(cardsTable);

		addCard = new JButton("+");
		addCard.setFont(buttonFont);

		addCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cardName = cardValueField.getText();
				boolean isCreditCard = creditButton.isSelected();
				boolean isDebitCard = debitButton.isSelected();

				if (cardName == null || cardName.equals("")
						|| (!isCreditCard && !isDebitCard)) {
					JOptionPane.showMessageDialog(null,
							"The card name and type are mandatory",
							"Error Message", JOptionPane.ERROR_MESSAGE);
					return;
				}

				controller.createCard(cardName, isCreditCard);
				initData();
			}

		});

		initData();

		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(cardPanel, BorderLayout.NORTH);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.getContentPane().add(addCard, BorderLayout.SOUTH);
		this.setName("Add Category");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);
	}

	public void initData() {
		cardsTable = GUIUtils.createTable(controller.loadCards());
		cardsTable.setForeground(Color.BLUE);
		cardsTable.setFont(new Font("Verdana", Font.BOLD, 15));
		cardsTable.getTableHeader().setForeground(Color.BLUE);
		cardsTable.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 15));
		scrollPane.setViewportView(cardsTable);
		cardValueField.setText("");
		creditButton.setSelected(false);
		debitButton.setSelected(false);
	}
}