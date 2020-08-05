package com.mccarthy.api.service.dao;

import com.mccarthy.api.error.exceptions.NoPortfolioException;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * H2 in memory db implementation of the data access interface.
 */
@Repository
public class DataAccessServiceH2Impl implements DataAccessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessServiceH2Impl.class);
    private static final String DELETE_SYMBOL_QUERY = "DELETE FROM SYMBOLS WHERE PORTFOLIO = '%s' AND NAME = '%s';";
    private static final String SELECT_PORTFOLIO_QUERY = "SELECT * FROM PORTFOLIOS WHERE ID = '%s'";
    private static final String DELETE_SYMBOLS_QUERY = "DELETE FROM SYMBOLS WHERE PORTFOLIO = '%s';";
    private static final String DELETE_PORTFOLIO_QUERY = "DELETE FROM PORTFOLIOS WHERE ID = '%s';";
    private static final String SELECT_SYMBOLS_QUERY = "SELECT * FROM SYMBOLS where portfolio ='%s';";
    private static final String MERGE_PORTFOLIO_QUERY = "MERGE INTO PORTFOLIOS (id) values ( ? );";
    private static final String MERGE_SYMBOLS_QUERY = "MERGE INTO SYMBOLS (portfolio, name, volume) VALUES (?, ?, ?);";
    private final JdbcTemplate jdbcTemplate;

    public DataAccessServiceH2Impl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Portfolio getPortfolio(String id) {
        LOGGER.info("Retrieving portfolio with id " + id + " from cache.");
        Portfolio portfolio = getPortfolioDetails(id);
        portfolio.setSymbols(getSymbols(id));

        return portfolio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void savePortfolio(Portfolio portfolio) {
        LOGGER.info("Saving portfolio to cache");
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
        LOGGER.info("Deleting portfolio with id " + id);
        deleteSymbolDetails(id);
        deletePortfolioDetails(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSymbol(String portfolioId, String symbol) {
        LOGGER.info("Deleting symbol " + symbol + " from portfolio with id " + portfolioId);
        String query = String.format(DELETE_SYMBOL_QUERY, portfolioId, symbol);
        jdbcTemplate.execute(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPortfolio(String id) {
        LOGGER.info("Checking does a portfolio with id " + id + " exist.");
        String query = String.format(SELECT_PORTFOLIO_QUERY, id);
        List<Portfolio> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Portfolio.class));

        return !result.isEmpty();
    }

    /**
     * Delete the symbol details from the h2 database.
     *
     * @param id Id of the portfolio to remove.
     */
    protected void deleteSymbolDetails(String id) {
        String query = String.format(DELETE_SYMBOLS_QUERY, id);
        jdbcTemplate.execute(query);
    }

    /**
     * Delete the portfolio from the portfolio table.
     *
     * @param id Id of the portfolio to remove.
     */
    protected void deletePortfolioDetails(String id) {
        String query = String.format(DELETE_PORTFOLIO_QUERY, id);
        jdbcTemplate.execute(query);
    }

    /**
     * Retrieve the portfolio details from the h2 database.
     *
     * @param id Id of the portfolio.
     * @return Portfolio.
     */
    protected Portfolio getPortfolioDetails(String id) {
        String query = String.format(DataAccessServiceH2Impl.SELECT_PORTFOLIO_QUERY, id);

        List<Portfolio> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Portfolio.class));
        if (result.isEmpty()) {
            throw new NoPortfolioException("Could not retrieve portfolio with id" + id);
        }

        return result.get(0);
    }

    /**
     * Get the symbols that are associated with this portfolio.
     *
     * @param id Portfolio id.
     * @return List of symbols associated with this portfolio.
     */
    protected List<Symbol> getSymbols(String id) {
        String query = String.format(SELECT_SYMBOLS_QUERY, id);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Symbol.class));
    }

    /**
     * Save the portfolio details to the h2 database.
     *
     * @param portfolioId Portfolio id.
     */
    protected void savePortfolioDetails(String portfolioId) {
        jdbcTemplate.update(MERGE_PORTFOLIO_QUERY, portfolioId);
    }

    /**
     * Save the symbol details of the portfolio to the h2 database.
     *
     * @param portfolioId Portfolio id.
     * @param symbols     List of symbols.
     */
    protected void saveSymbolDetails(String portfolioId, List<Symbol> symbols) {
        for (Symbol symbol : symbols) {
            jdbcTemplate.update(MERGE_SYMBOLS_QUERY, portfolioId, symbol.getName(), symbol.getVolume());
        }
    }
}
