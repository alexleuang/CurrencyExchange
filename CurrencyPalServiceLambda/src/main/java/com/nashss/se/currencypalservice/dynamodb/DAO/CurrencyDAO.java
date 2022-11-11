package com.nashss.se.currencypalservice.dynamodb.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.currencypalservice.dynamodb.models.Currency;
import com.nashss.se.currencypalservice.exceptions.CurrencyNotFoundException;
import com.nashss.se.currencypalservice.metrics.MetricsConstants;
import com.nashss.se.currencypalservice.metrics.MetricsPublisher;

/**
 * Accesses data for our currency objects
 */


public class CurrencyDAO {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a CurrencyDao object.
     * @param dynamoDBMapper the link used to interact with the Currency table
     * @param metricsPublisher the link used to record metrics
     */

    public CurrencyDAO(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Currency getCurrency(String currencyAbrv) {
        Currency currency = this.dynamoDBMapper.load(Currency.class, currencyAbrv);
        if (currency == null) {
            metricsPublisher.addCount(MetricsConstants.GETCURRENCY_CURRENCYNOTFOUND_COUNT, 1);
            throw new CurrencyNotFoundException("Could not find the currency : " + currencyAbrv);
        }
        metricsPublisher.addCount(MetricsConstants.GETCURRENCY_CURRENCYNOTFOUND_COUNT, 0);
        return currency;
    }

}
