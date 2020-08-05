package com.mccarthy.api.service.dao;

import com.mccarthy.api.model.Portfolio;

/**
 * Interface defining the operations of a data access service.
 * Any implementation of this interface will provide the required functionality for a given data store, e.g. redis, mysql...
 * NoSQL solution, such as redis would be preferable.
 */
public interface DataAccess {

    /**
     * Retrieve a portfolio identified by the given id.
     *
     * @param id Portfolio id.
     * @return Portfolio identified by the given id.
     */
    Portfolio getPortfolio(String id);

    /**
     * Save the given portfolio to the cache.
     *
     * @param portfolio Portfolio to save.
     */
    void savePortfolio(Portfolio portfolio);

    /**
     * Delete the portfolio ientified by the given id from the cache.
     *
     * @param id Portfolio id.
     */
    void deletePortfolio(String id);

    /**
     * Delete the given symbol from the given portfolio.
     *
     * @param portfolioId Id of the portfolio to remove from.
     * @param symbol      Symbol to remove.
     */
    void deleteSymbol(String portfolioId, String symbol);

    /**
     * Return true if the given portfolio exists in the cache.
     *
     * @param id Id of the given portfolio.
     * @return true if the given portfolio exists in the cache.
     */
    boolean hasPortfolio(String id);
}
