package com.mccarthy.api.service.dao;

import com.mccarthy.api.error.exceptions.NoPortfolioException;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * H2 in memory db implementation of the data access interface.
 */
@Repository
public class DataAccessH2 implements DataAccess {
    private final JdbcTemplate jdbcTemplate;

    public DataAccessH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Portfolio getPortfolio(String id) {
        Portfolio portfolio = getPortfolioDetails(id);
        portfolio.setSymbols(getSymbols(id));

        return portfolio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void savePortfolio(Portfolio portfolio) {
        savePortfolioDetails(portfolio.getId());
        List<Symbol> symbols = portfolio.getSymbols();
        if (symbols != null) {
            saveSymbolDetails(portfolio.getId(), symbols);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePortfolio(String id) {
        deleteSymbolDetails(id);
        deletePortfolioDetails(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSymbol(String portfolioId, String symbol) {
        String query = String.format("DELETE FROM SYMBOLS WHERE PORTFOLIO = '%s' AND SYMBOL = '%s';", portfolioId, symbol);
        jdbcTemplate.execute(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPortfolio(String id) {
        String query = String.format("SELECT * FROM PORTFOLIOS WHERE ID  = '%s'", id);
        List<Portfolio> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Portfolio.class));

        return !result.isEmpty();
    }

    /**
     * Delete the symbol details from the h2 database.
     *
     * @param id Id of the portfolio to remove.
     */
    protected void deleteSymbolDetails(String id) {
        String query = String.format("DELETE FROM SYMBOLS WHERE PORTFOLIO = '%s';", id);
        jdbcTemplate.execute(query);
    }

    /**
     * Delete the portfolio from the portfolio table.
     *
     * @param id Id of the portfolio to remove.
     */
    protected void deletePortfolioDetails(String id) {
        String query = String.format("DELETE FROM PORTFOLIOS WHERE ID = '%s';", id);
        jdbcTemplate.execute(query);
    }

    /**
     * Retrieve the portfolio details from the h2 database.
     *
     * @param id Id of the portfolio.
     * @return Portfolio.
     */
    protected Portfolio getPortfolioDetails(String id) {
        String request = String.format("SELECT * FROM PORTFOLIOS where id = '%s';", id);

        List<Portfolio> query = jdbcTemplate.query(request, new BeanPropertyRowMapper<>(Portfolio.class));
        if (query.isEmpty()) {
            throw new NoPortfolioException("Could not retrieve portfolio with id" + id);
        }

        return query.get(0);
    }

    /**
     * Get the symbols that are associated with this portfolio.
     *
     * @param id Portfolio id.
     * @return List of symbols associated with this portfolio.
     */
    protected List<Symbol> getSymbols(String id) {
        String query = String.format("SELECT * FROM SYMBOLS where portfolio ='%s';", id);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Symbol.class));
    }

    /**
     * Save the portfolio details to the h2 database.
     *
     * @param portfolioId Portfolio id.
     */
    protected void savePortfolioDetails(String portfolioId) {
        String query = "MERGE INTO PORTFOLIOS (id) values ( ? );";
        jdbcTemplate.update(query, portfolioId);
    }

    /**
     * Save the symbol details of the portfolio to the h2 database.
     *
     * @param portfolioId Portfolio id.
     * @param symbols     List of symbols.
     */
    protected void saveSymbolDetails(String portfolioId, List<Symbol> symbols) {
        String query = "MERGE INTO SYMBOLS (portfolio, symbol, volume) VALUES (?, ?, ?);";
        for (Symbol symbol : symbols) {
            jdbcTemplate.update(query, portfolioId, symbol.getSymbol(), symbol.getVolume());
        }
    }
}
