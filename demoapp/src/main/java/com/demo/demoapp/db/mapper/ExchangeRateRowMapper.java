package com.demo.demoapp.db.mapper;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.demoapp.domain.ExchangeRate;

public class ExchangeRateRowMapper implements RowMapper<ExchangeRate> {

    @Override
    public ExchangeRate mapRow(ResultSet resultSet, int i) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(resultSet.getInt("id"));
        exchangeRate.setShortName(resultSet.getString("short_name"));
        exchangeRate.setValidFrom(resultSet.getTimestamp("valid_from"));
        exchangeRate.setName(resultSet.getString("name"));
        exchangeRate.setCountry(resultSet.getString("country"));
        exchangeRate.setMove(resultSet.getBigDecimal("move"));
        exchangeRate.setAmount(resultSet.getBigDecimal("amount"));
        exchangeRate.setValBuy(resultSet.getBigDecimal("val_buy"));
        exchangeRate.setValSell(resultSet.getBigDecimal("val_sell"));
        exchangeRate.setValMid(resultSet.getBigDecimal("val_mid"));
        exchangeRate.setCurrBuy(resultSet.getBigDecimal("curr_buy"));
        exchangeRate.setCurrSell(resultSet.getBigDecimal("curr_sell"));
        exchangeRate.setCurrMid(resultSet.getBigDecimal("curr_mid"));
        exchangeRate.setVersion(resultSet.getInt("version"));
        exchangeRate.setCnbMid(resultSet.getBigDecimal("cnb_mid"));
        exchangeRate.setEcbMid(resultSet.getBigDecimal("ecb_mid"));
        return exchangeRate;
    }
}
