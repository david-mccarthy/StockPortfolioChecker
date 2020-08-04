package com.mccarthy.api.service.dao;

import com.mccarthy.api.error.exceptions.NoPortfolioException;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataAccessH2 implements DataAccess {
    private final JdbcTemplate jdbcTemplate;

    public DataAccessH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Portfolio getPortfolio(String id) {
        Portfolio portfolio = getPortfolioDetails(id);
        portfolio.setSymbols(getSymbols(id));

        return portfolio;
    }

    @Override
    public void savePortfolio(Portfolio portfolio) {
        savePortfolioDetails(portfolio.getId());
        List<Symbol> symbols = portfolio.getSymbols();
        if (symbols != null) {
            saveSymbolDetails(portfolio.getId(), symbols);
        }
    }

    @Override
    public void deletePortfolio(String id) {
        deleteSymbolDetails(id);
        deletePortfolioDetails(id);
    }

    @Override
    public void deleteSymbol(String portfolioId, String symbol) {
        String query = String.format("DELETE FROM SYMBOLS WHERE PORTFOLIO = '%s' AND SYMBOL = '%s';", portfolioId, symbol);
        jdbcTemplate.execute(query);
    }

    @Override
    public boolean hasPortfolio(String id) {
        String query = String.format("SELECT * FROM DATA WHERE ID  = '%s'", id);
        List<Portfolio> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Portfolio.class));

        return !result.isEmpty();
    }

    protected void deleteSymbolDetails(String id) {
        String query = String.format("DELETE FROM SYMBOLS WHERE PORTFOLIO = '%s';", id);
        jdbcTemplate.execute(query);
    }

    protected void deletePortfolioDetails(String id) {
        String query = String.format("DELETE FROM DATA WHERE ID = '%s';", id);
        jdbcTemplate.execute(query);
    }

    protected Portfolio getPortfolioDetails(String id) {
        String request = String.format("SELECT * FROM DATA where id = '%s';", id);

        List<Portfolio> query = jdbcTemplate.query(request, new BeanPropertyRowMapper<>(Portfolio.class));
        if (query.isEmpty()) {
            throw new NoPortfolioException("Could not retrieve portfolio with id" + id);
        }

        return query.get(0);
    }

    protected List<Symbol> getSymbols(String id) {
        String query = String.format("SELECT * FROM SYMBOLS where portfolio ='%s';", id);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Symbol.class));
    }

    protected void savePortfolioDetails(String portfolioId) {
        String query = "MERGE INTO data (id) values ( ? );";
        jdbcTemplate.update(query, portfolioId);
    }

    protected void saveSymbolDetails(String portfolioId, List<Symbol> symbols) {
        String query = "MERGE INTO SYMBOLS (portfolio, symbol, volume) VALUES (?, ?, ?);";
        for (Symbol symbol : symbols) {
            jdbcTemplate.update(query, portfolioId, symbol.getSymbol(), symbol.getVolume());
        }
    }
}
