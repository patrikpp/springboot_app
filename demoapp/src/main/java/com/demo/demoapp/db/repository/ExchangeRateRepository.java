package com.demo.demoapp.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.demo.demoapp.domain.ExchangeRate;
import com.demo.demoapp.db.mapper.ExchangeRateRowMapper;

@Repository
public class ExchangeRateRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ExchangeRateRowMapper exchangeRateRowMapper = new ExchangeRateRowMapper();

    public ExchangeRateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private int getTotalCount() {
        final String sql = "SELECT COUNT(*) AS row_count FROM exchange_rates";
        Integer value = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt("row_count"));

        return (value == null) ? 0 : value;
    }

    public Page<ExchangeRate> getAll(Pageable pageable) {
        final String sql = "SELECT * FROM exchange_rates ORDER BY short_name OFFSET ? ROWS LIMIT ?";

        List<ExchangeRate> exchangeRates = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, (int)pageable.getOffset());
                ps.setInt(2, pageable.getPageSize());
            }
        }, exchangeRateRowMapper);

        return new PageImpl<>(exchangeRates, pageable, getTotalCount());
    }

    public void saveAll(List<ExchangeRate> exchangeRates) {
        jdbcTemplate.batchUpdate("INSERT INTO exchange_rates (short_name, valid_from, name, country, move, amount, val_buy, " +
            "val_sell, val_mid, curr_buy, curr_sell, curr_mid, version, cnb_mid, ecb_mid) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            exchangeRates,
            100,
            (PreparedStatement ps, ExchangeRate exchangeRate) -> {
                ps.setString(1, exchangeRate.getShortName());
                ps.setTimestamp(2, exchangeRate.getValidFrom());
                ps.setString(3, exchangeRate.getName());
                ps.setString(4, exchangeRate.getCountry());
                ps.setBigDecimal(5, exchangeRate.getMove());
                ps.setBigDecimal(6, exchangeRate.getAmount());
                ps.setBigDecimal(7, exchangeRate.getValBuy());
                ps.setBigDecimal(8, exchangeRate.getValSell());
                ps.setBigDecimal(9, exchangeRate.getValMid());
                ps.setBigDecimal(10, exchangeRate.getCurrBuy());
                ps.setBigDecimal(11, exchangeRate.getCurrSell());
                ps.setBigDecimal(12, exchangeRate.getCurrMid());
                ps.setInt(13, exchangeRate.getVersion());
                ps.setBigDecimal(14, exchangeRate.getCnbMid());
                ps.setBigDecimal(15, exchangeRate.getEcbMid());
        });
    }
}