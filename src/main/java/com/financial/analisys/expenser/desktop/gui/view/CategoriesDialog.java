package com.financial.analisys.expenser.desktop.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.financial.analisys.expenser.desktop.gui.controller.Controller;
import com.financial.analisys.expenser.desktop.gui.util.GUIUtils;

public class CategoriesDialog extends JDialog {

	private static final String FONT_FAMILY = "Verdana";

	private static final long serialVersionUID = 1L;

	private JPanel categoryPanel;
	private JLabel categoryValueLabel;
	private JTextField categoryValueField;

	private JScrollPane scrollPane;
	private JTable categoriesTable;

	private JButton addCategory;

	private Controller controller;

	public CategoriesDialog(Controller controller) {
		this.controller = controller;
	}

	public void createAndShowDialog() {
		Font buttonFont = new Font(FONT_FAMILY, Font.BOLD, 40);
		Font dataFont = new Font(FONT_FAMILY, Font.BOLD, 40);
		Font labelFont = new Font(FONT_FAMILY, Font.BOLD, 20);

		categoryPanel = new JPanel(new GridLayout(0, 2));
		categoryValueLabel = new JLabel("Category Name:");
		categoryValueLabel.setFont(labelFont);
		categoryValueField = new JTextField();
		categoryValueField.setFont(dataFont);
		categoryValueField.setForeground(Color.BLUE);
		categoryPanel.add(categoryValueLabel);
		categoryPanel.add(categoryValueField);

		scrollPane = new JScrollPane();
		categoriesTable = new JTable(10, 2);
		scrollPane.setViewportView(categoriesTable);

		addCategory = new JButton("+");
		addCategory.setFont(buttonFont);

		addCategory.addActionListener(e -> {
			String categoryName = categoryValueField.getText();

			if (categoryName == null || "".equals(categoryName)) {
				JOptionPane.showMessageDialog(null,
						"The category name is mandatory", "Error Message",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			controller.createCategory(categoryName);
			initData();
		});

		initData();

		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(categoryPanel, BorderLayout.NORTH);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.getContentPane().add(addCategory, BorderLayout.SOUTH);
		this.setName("Add Category");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);
	}

	public void initData() {
		categoriesTable = GUIUtils.createTable(controller.loadCategories());
		categoriesTable.setForeground(Color.BLUE);
		categoriesTable.setFont(new Font(FONT_FAMILY, Font.BOLD, 15));
		categoriesTable.getTableHeader().setForeground(Color.BLUE);
		categoriesTable.getTableHeader().setFont(
				new Font(FONT_FAMILY, Font.BOLD, 15));
		scrollPane.setViewportView(categoriesTable);
		categoryValueField.setText("");
	}
}