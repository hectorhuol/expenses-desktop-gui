package com.financial.analisys.expenser.desktop.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import com.financial.analisys.expenser.desktop.gui.component.FinancialToggleButton;
import com.financial.analisys.expenser.desktop.gui.controller.Controller;
import com.financial.analisys.expenser.desktop.gui.util.GUIUtils;
import com.toedter.calendar.JDateChooser;

public class ExpensesDialog extends JDialog {

	private static final String FONT_FAMILY = "Verdana";

	private static final long serialVersionUID = 1L;

	private JPanel valuePanel;
	private JLabel expenseValueLabel;
	private JTextField expenseValueField;

	private JDateChooser calendar;
	private JLabel calendarLabel;

	private JScrollPane scrollPane;
	private JPanel centerPanel;
	private JPanel categoriesPanel;
	private JPanel cardsPanel;
	private JPanel addPanel;
	private JPanel southPanel;
	private JLabel categoriesLabel;
	private JButton addCategory;
	private JLabel cardsLabel;
	private JButton addCard;
	private List<FinancialToggleButton> categoriesButtons;
	private ButtonGroup categoriesGroup;
	private List<FinancialToggleButton> cardsButtons;
	private ButtonGroup cardsGroup;

	private JButton addExpense;

	private CategoriesDialog categoriesDialog;
	private CardsDialog cardsDialog;

	private Controller controller;

	private Font toggleFont = new Font(FONT_FAMILY, Font.BOLD, 15);

	public ExpensesDialog(Controller controller) {
		this.controller = controller;
	}

	public void createAndShowDialog() {
		controller.setExpensesDialog(this);

		Font buttonFont = new Font(FONT_FAMILY, Font.BOLD, 40);
		Font dataFont = new Font(FONT_FAMILY, Font.BOLD, 40);
		Font labelFont = new Font(FONT_FAMILY, Font.BOLD, 20);

		calendarLabel = new JLabel("Expense Date =");
		calendarLabel.setFont(labelFont);
		calendar = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
		calendar.getJCalendar().setFont(toggleFont);
		calendar.getJCalendar().setMaxDayCharacters(1);
		calendar.getJCalendar().setWeekOfYearVisible(false);
		calendar.setFont(labelFont);

		valuePanel = new JPanel(new GridLayout(0, 2, 0, 0));
		expenseValueLabel = new JLabel("Expense Value =");
		expenseValueLabel.setFont(labelFont);
		expenseValueField = new JTextField();
		expenseValueField.setForeground(Color.RED);
		expenseValueField.setFont(dataFont);
		valuePanel.add(expenseValueLabel);
		valuePanel.add(expenseValueField);
		valuePanel.add(calendarLabel);
		valuePanel.add(calendar);

		scrollPane = new JScrollPane();
		centerPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new GridLayout(0, 1));
		addPanel = new JPanel(new FlowLayout());
		categoriesPanel = new JPanel(new GridLayout(0, 3));
		categoriesPanel.setBorder(GUIUtils.createTitledBorder("Categories"));
		cardsPanel = new JPanel(new GridLayout(0, 1));
		cardsPanel.setBorder(GUIUtils.createTitledBorder("Cards"));
		categoriesGroup = new ButtonGroup();
		cardsGroup = new ButtonGroup();
		categoriesLabel = new JLabel("Add Category:", SwingUtilities.LEFT);
		categoriesLabel.setFont(labelFont);
		addCategory = new JButton("+");
		addCategory.setFont(labelFont);
		cardsLabel = new JLabel("Add Card:", SwingUtilities.LEFT);
		cardsLabel.setFont(labelFont);
		addCard = new JButton("+");
		addCard.setFont(labelFont);
		categoriesButtons = GUIUtils.createToggleButtons(controller
				.loadCategories());
		cardsButtons = GUIUtils.createToggleButtons(controller.loadCards());

		addPanel.add(categoriesLabel);
		addPanel.add(addCategory);
		addPanel.add(cardsLabel);
		addPanel.add(addCard);
		southPanel.add(addPanel);
		for (JToggleButton button : categoriesButtons) {
			button.setFont(toggleFont);
			categoriesPanel.add(button);
			categoriesGroup.add(button);
		}
		for (JToggleButton button : cardsButtons) {
			button.setFont(toggleFont);
			cardsPanel.add(button);
			cardsGroup.add(button);
		}
		centerPanel.add(cardsPanel, BorderLayout.AFTER_LINE_ENDS);
		centerPanel.add(categoriesPanel, BorderLayout.CENTER);
		centerPanel.add(southPanel, BorderLayout.SOUTH);
		scrollPane.setViewportView(centerPanel);

		addExpense = new JButton("+");
		addExpense.setFont(buttonFont);

		categoriesDialog = new CategoriesDialog(controller);
		cardsDialog = new CardsDialog(controller);

		addCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoriesDialog.createAndShowDialog();
			}
		});
		addCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardsDialog.createAndShowDialog();
			}
		});
		addExpense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String expenseValue = expenseValueField.getText();
				String selectedCategory = getSelectedButton(categoriesGroup);
				String selectedCard = getSelectedButton(cardsGroup);
				LocalDateTime date = (calendar.getDate() != null) ? LocalDateTime
						.ofInstant(calendar.getDate().toInstant(),
								ZoneId.systemDefault()) : null;

				if (expenseValue == null || "".equals(expenseValue)
						|| !GUIUtils.isANumber(expenseValue)) {
					JOptionPane.showMessageDialog(null,
							"The expense value should be a number",
							"Error Message", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (selectedCategory == null) {
					JOptionPane.showMessageDialog(null,
							"You should select a category", "Error Message",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				controller.createExpense(expenseValue, selectedCategory,
						selectedCard, date);

				setVisible(false);
			}

		});

		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(valuePanel, BorderLayout.NORTH);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.getContentPane().add(addExpense, BorderLayout.SOUTH);
		this.setName("Add Expense");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);
	}

	private String getSelectedButton(ButtonGroup group) {
		Enumeration<AbstractButton> elements = group.getElements();
		while (elements.hasMoreElements()) {
			FinancialToggleButton button = (FinancialToggleButton) elements
					.nextElement();
			if (button.isSelected()) {
				return button.getId();
			}
		}
		return null;
	}

	public void initData() {
		categoriesPanel.removeAll();
		;
		cardsPanel.removeAll();
		expenseValueField.setText("");
		categoriesButtons = GUIUtils.createToggleButtons(controller
				.loadCategories());
		cardsButtons = GUIUtils.createToggleButtons(controller.loadCards());
		for (JToggleButton button : categoriesButtons) {
			button.setFont(toggleFont);
			categoriesPanel.add(button);
			categoriesGroup.add(button);
		}
		for (JToggleButton button : cardsButtons) {
			button.setFont(toggleFont);
			cardsPanel.add(button);
			cardsGroup.add(button);
		}
		scrollPane.setViewportView(centerPanel);
		this.pack();
		this.setLocationRelativeTo(null);
	}
}
