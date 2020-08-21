package mage.client.table;

/**
 * @author JayDi85
 */
public class ColumnInfo {
    private final Integer index;
    private final Integer width;
    private final String headerName;
    private final String headerHint;
    private final Class colClass;

    public ColumnInfo(Integer index, Integer width, Class colClass, String headerName, String headerHint) {
        this.index = index;
        this.width = width;
        this.colClass = colClass;
        this.headerName = headerName;
        this.headerHint = headerHint;
    }


    public Integer getIndex() {
        return index;
    }

    public Integer getWidth() {
        return width;
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getHeaderHint() {
        return headerHint;
    }

    public Class getColClass() {
        return colClass;
    }
}
