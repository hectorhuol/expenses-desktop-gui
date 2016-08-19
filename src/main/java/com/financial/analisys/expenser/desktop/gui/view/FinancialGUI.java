package com.financial.analisys.expenser.desktop.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.financial.analisys.expenser.desktop.gui.controller.Controller;
import com.financial.analisys.expenser.desktop.gui.dto.DayExpenses;
import com.financial.analisys.expenser.desktop.gui.dto.MonthExpenses;
import com.financial.analisys.expenser.desktop.gui.util.GUIUtils;

public class FinancialGUI extends JFrame {

	private static final String FONT_FAMILY = "Verdana";

	private static final long serialVersionUID = 1L;

	private JPanel addExpensePanel;
	private JScrollPane scrollPane;
	private JTable expensesTable;
	private JButton addExpenseButton;

	private JPanel resumePanel;
	private JLabel totalDayExpensesLabel;
	private JLabel totalMonthExpensesLabel;
	private JLabel totalByCardLabel;
	private JLabel totalDayExpensesTitleLabel;
	private JLabel totalMonthExpensesTitleLabel;
	private JLabel totalByCardTitleLabel;

	private Controller controller;
	private DayExpenses dayExpenses;
	private MonthExpenses monthExpenses;

	private ExpensesDialog expensesDialog;

	public FinancialGUI() {
		controller = new Controller();
		controller.setGUI(this);
	}

	public void createAndShowGUI() {
		Font buttonFont = new Font(FONT_FAMILY, Font.BOLD, 100);
		Font dataFont = new Font(FONT_FAMILY, Font.BOLD, 40);
		Font labelFont = new Font(FONT_FAMILY, Font.BOLD, 20);

		addExpensePanel = new JPanel(new GridLayout(0, 1));
		addExpenseButton = new JButton("+");
		addExpenseButton.setFont(buttonFont);
		addExpensePanel.add(addExpenseButton);

		expensesTable = new JTable(10, 3);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(expensesTable);

		resumePanel = new JPanel(new GridLayout(0, 2));
		resumePanel.setBorder(GUIUtils.createTitledBorder("Resume"));
		totalDayExpensesLabel = new JLabel("-", SwingConstants.CENTER);
		totalDayExpensesLabel.setFont(dataFont);
		totalMonthExpensesLabel = new JLabel("-", SwingConstants.CENTER);
		totalMonthExpensesLabel.setFont(dataFont);
		totalByCardLabel = new JLabel("-", SwingConstants.CENTER);
		totalByCardLabel.setFont(dataFont);
		totalDayExpensesTitleLabel = new JLabel("Day Expenses:");
		totalDayExpensesTitleLabel.setFont(labelFont);
		totalMonthExpensesTitleLabel = new JLabel("Month Expenses:");
		totalMonthExpensesTitleLabel.setFont(labelFont);
		totalByCardTitleLabel = new JLabel("Month Credit Card:");
		totalByCardTitleLabel.setFont(labelFont);
		resumePanel.add(totalDayExpensesTitleLabel);
		resumePanel.add(totalDayExpensesLabel);
		resumePanel.add(totalMonthExpensesTitleLabel);
		resumePanel.add(totalMonthExpensesLabel);
		resumePanel.add(totalByCardTitleLabel);
		resumePanel.add(totalByCardLabel);

		expensesDialog = new ExpensesDialog(controller);

		addExpenseButton.addActionListener(e -> {
			expensesDialog.createAndShowDialog();
		});

		initData();

		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(addExpensePanel, BorderLayout.NORTH);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.getContentPane().add(resumePanel, BorderLayout.SOUTH);
		this.setName("Financial GUI");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void initData() {
		controller.loadDataModel();
		dayExpenses = controller.getDayExpenses();
		monthExpenses = controller.getMonthExpenses();

		expensesTable = GUIUtils.createTable(controller.loadExpenes());
		expensesTable.setForeground(Color.RED);
		expensesTable.setFont(new Font(FONT_FAMILY, Font.BOLD, 15));
		expensesTable.getTableHeader().setFont(
				new Font(FONT_FAMILY, Font.BOLD, 15));
		expensesTable.getTableHeader().setForeground(Color.RED);
		scrollPane.setViewportView(expensesTable);

		if (dayExpenses != null)
			totalDayExpensesLabel.setText(dayExpenses.getTotal());
		if (monthExpenses != null) {
			totalMonthExpensesLabel.setText(monthExpenses.getTotal());
			totalByCardLabel.setText(monthExpenses.getTotalCreditCard());
		}
	}

}