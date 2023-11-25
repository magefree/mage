package mage.client.table;

/**
 * GUI: add support of tooltip/hint for table's cells on mouse move (used by MageTable)
 *
 * @author JayDi85
 */
public interface TableModelWithTooltip {

    String getTooltipAt(int rowIndex, int columnIndex);

}
