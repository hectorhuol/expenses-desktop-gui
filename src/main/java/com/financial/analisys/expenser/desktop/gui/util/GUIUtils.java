package com.financial.analisys.expenser.desktop.gui.util;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.financial.analisys.expenser.desktop.gui.component.FinancialToggleButton;
import com.financial.analisys.expenser.desktop.gui.dto.GuiData;

public class GUIUtils {

	private GUIUtils() {
	}

	private static Border lineBorder = BorderFactory.createLineBorder(
			Color.BLUE, 3);
	private static Font borderFont = new Font("Verdana", Font.BOLD, 20);

	public static JTable createTable(GuiData guiData) {
		if (guiData != null)
			return new JTable(guiData.getData(), guiData.getColumnNames());
		else
			return new JTable(10, 3);
	}

	public static List<FinancialToggleButton> createToggleButtons(
			GuiData guiData) {
		List<FinancialToggleButton> buttons = new ArrayList<>();
		if (guiData != null) {
			Object[][] data = guiData.getData();
			for (int i = 0; i < data.length; i++) {
				buttons.add(new FinancialToggleButton(data[i][0].toString(),
						data[i][1].toString()));
			}

		} else {
			for (int i = 0; i < 5; i++)
				buttons.add(new FinancialToggleButton(Integer.toString(i),
						"something" + i));
		}

		return buttons;
	}

	public static boolean isANumber(String expenseValue) {
		return Pattern.compile("-?([0-9]+(\\.[0-9]+)?)").matcher(expenseValue)
				.matches();
	}

	public static Border createTitledBorder(String label) {
		TitledBorder border = BorderFactory.createTitledBorder(lineBorder,
				label);
		border.setTitleFont(borderFont);
		return border;
	}

	public static Border createLineBorder() {
		return lineBorder;
	}
}
