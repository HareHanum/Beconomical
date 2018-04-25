package com.apps.harel.beconomical.database;

public class Author {

    private String incomes;
    private String expenses;

    public Author() {
    }

    public Author(String incomes, String expenses) {
        this.incomes = incomes;
        this.expenses = expenses;
    }

    public String getIncomes() {
        return incomes;
    }

    public void setIncomes(String incomes) {
        this.incomes = incomes;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }
}
