package mage.server.graphql.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameUpdate {

    private final String stockCode;
    private final String dateTime;
    private final BigDecimal stockPrice;
    private final BigDecimal stockPriceChange;

    public GameUpdate(String stockCode, LocalDateTime dateTime, BigDecimal stockPrice, BigDecimal stockPriceChange) {
        this.stockCode = stockCode;
        this.dateTime = dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        this.stockPrice = stockPrice;
        this.stockPriceChange = stockPriceChange;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getDateTime() {
        return dateTime;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public BigDecimal getStockPriceChange() {
        return stockPriceChange;
    }
}
