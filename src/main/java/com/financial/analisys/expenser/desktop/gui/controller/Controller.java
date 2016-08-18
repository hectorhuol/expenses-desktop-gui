package com.financial.analisys.expenser.desktop.gui.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.financial.analisys.expenser.desktop.gui.dto.DayExpenses;
import com.financial.analisys.expenser.desktop.gui.dto.GuiData;
import com.financial.analisys.expenser.desktop.gui.dto.MonthExpenses;
import com.financial.analisys.expenser.desktop.gui.view.ExpensesDialog;
import com.financial.analisys.expenser.desktop.gui.view.FinancialGUI;
import com.financial.analisys.expenses.api.domain.Card;
import com.financial.analisys.expenses.api.domain.CardType;
import com.financial.analisys.expenses.api.domain.Category;
import com.financial.analisys.expenses.api.domain.Expense;
import com.financial.analisys.expenses.api.domain.User;
import com.financial.analisys.expenses.api.factory.FinancialsAnalisysFactory;
import com.financial.analisys.expenses.api.factory.GatewayType;
import com.financial.analisys.expenses.api.factory.ValidatorType;
import com.financial.analisys.expenses.api.factory.impl.FinancialsAnalisysFactoryImpl;
import com.financial.analisys.expenses.api.managers.CardsManager;
import com.financial.analisys.expenses.api.managers.CategoriesManager;
import com.financial.analisys.expenses.api.managers.ExpensesManager;
import com.financial.analisys.expenses.api.managers.UsersManager;
import com.financial.analisys.expenses.api.utils.FinancialUtils;

public class Controller {

	private ExpensesManager expensesManager;
	private CategoriesManager categoriesManager;
	private CardsManager cardsManager;
	private UsersManager usersManager;
	private User defaultUser;
	private FinancialsAnalisysFactory financialsAnalisysFactory;
	private FinancialGUI financialGUI;
	private ExpensesDialog expensesDialog;

	public void loadDataModel() {
		financialsAnalisysFactory = new FinancialsAnalisysFactoryImpl();
		expensesManager = ExpensesManager.getNewExpensesManager(
				financialsAnalisysFactory
						.createExpensesGateway(GatewayType.MAP),
				financialsAnalisysFactory
						.createExpensesReportsGateway(GatewayType.MAP),
				financialsAnalisysFactory
						.createExpensesValidator(ValidatorType.DEFAULT));

		categoriesManager = CategoriesManager.getNewCategoriesManager(
				financialsAnalisysFactory
						.createCategoriesGateway(GatewayType.MAP),
				financialsAnalisysFactory
						.createCategoriesValidator(ValidatorType.DEFAULT));

		cardsManager = CardsManager.getNewCardsManager(
				financialsAnalisysFactory.createCardsGateway(GatewayType.MAP),
				financialsAnalisysFactory
						.createCardsValidator(ValidatorType.DEFAULT));

		usersManager = UsersManager.getNewUsersManager(
				financialsAnalisysFactory.createUsersGateway(GatewayType.MAP),
				financialsAnalisysFactory
						.createUsersValidator(ValidatorType.DEFAULT));

		createDefaultUser();

	}

	private void createDefaultUser() {
		defaultUser = new User();
		defaultUser.setUserId("1");
		defaultUser.setUserName("Hector");
		defaultUser.setPassword("password");
		usersManager.createUser(defaultUser);
	}

	public GuiData loadExpenes() {

		List<Expense> expenses = getAllExpenses();

		GuiData guiData = new GuiData();
		Object[] columnNames = { "Id", "Date", "Value", "Category" };
		Object[][] data = new Object[expenses.size()][4];

		for (int i = 0; i < expenses.size(); i++) {
			Expense expense = expenses.get(i);
			data[i][0] = expense.getExpenseId();
			data[i][1] = expense.getDateAndHour();
			data[i][2] = expense.getValue();
			data[i][3] = expense.getCategory().getName();
		}

		guiData.setColumnNames(columnNames);
		guiData.setData(data);

		return guiData;
	}

	public GuiData loadCategories() {
		List<Category> categories = categoriesManager.getAllCategories();

		GuiData guiData = new GuiData();
		Object[] columnNames = { "Id", "Name" };
		Object[][] data = new Object[categories.size()][2];

		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			data[i][0] = category.getCategoryId();
			data[i][1] = category.getName();
		}

		guiData.setColumnNames(columnNames);
		guiData.setData(data);

		return guiData;
	}

	public GuiData loadCards() {
		List<Card> cards = cardsManager.getAllCards();

		GuiData guiData = new GuiData();
		Object[] columnNames = { "Id", "Name", "Type" };
		Object[][] data = new Object[cards.size()][3];

		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			data[i][0] = card.getCardId();
			data[i][1] = card.getName();
			data[i][2] = card.getType().name();
		}

		guiData.setColumnNames(columnNames);
		guiData.setData(data);

		return guiData;
	}

	public DayExpenses getDayExpenses() {
		List<Expense> dayExpensesList = expensesManager.getExpensesByDayByUser(
				LocalDate.now(), defaultUser);
		DayExpenses dayExpenses = new DayExpenses();

		Double total = 0.0;

		for (Expense expense : dayExpensesList)
			total += expense.getValue();

		dayExpenses.setTotal(total.toString());

		return dayExpenses;
	}

	public MonthExpenses getMonthExpenses() {
		List<Expense> monthExpensesList = expensesManager
				.getExpensesByMonthByUser(LocalDate.now(), defaultUser);
		MonthExpenses montExpenses = new MonthExpenses();

		Double total = 0.0;
		Double totalCredit = 0.0;

		for (Expense expense : monthExpensesList) {
			total += expense.getValue();
			if (expense.getCard() != null
					&& expense.getCard().getType().equals(CardType.CREDIT))
				totalCredit += expense.getValue();
		}

		montExpenses.setTotal(total.toString());
		montExpenses.setTotalCreditCard(totalCredit.toString());

		return montExpenses;
	}

	public void createExpense(String value, String selectedCategory,
			String selectedCard, LocalDateTime date) {
		Expense expense = new Expense();
		expense.setValue(Double.valueOf(value));
		expense.setCity("Bogota");
		expense.setDateAndHour(FinancialUtils
				.getLocalDateTimeString((date == null) ? LocalDateTime.now()
						: date));
		expense.setUser(defaultUser);

		Category category = new Category();
		category.setCategoryId(selectedCategory);
		category = categoriesManager.getCategory(category);
		expense.setCategory(category);

		if (selectedCard != null && !selectedCard.equals("")) {
			Card card = new Card();
			card.setCardId(selectedCard);
			card = cardsManager.getCard(card);
			expense.setCard(card);
		}

		expensesManager.createExpense(expense);
		financialGUI.initData();
	}

	public void createCard(String cardName, boolean isCreditCard) {
		Card card = new Card();
		card.setName(cardName.toUpperCase());
		card.setType((isCreditCard) ? CardType.CREDIT : CardType.DEBIT);
		cardsManager.createCard(card);
		expensesDialog.initData();
	}

	public void createCategory(String categoryName) {
		Category category = new Category();
		category.setName(categoryName.toUpperCase());
		categoriesManager.createCategory(category);
		expensesDialog.initData();
	}

	private List<Expense> getAllExpenses() {
		return expensesManager.getAllUserExpenses(defaultUser);
	}

	public void setGUI(FinancialGUI financialGUI) {
		this.financialGUI = financialGUI;

	}

	public void setExpensesDialog(ExpensesDialog expensesDialog) {
		this.expensesDialog = expensesDialog;
	}

}
