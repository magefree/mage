package mage.components.table;

/**
 * GUI: add support of tooltip/hint for table's cells on mouse move (used by MageTable)
 * <p>
 * Make sure form and java files uses new MageTable(), not new JTable() code
 *
 * @author JayDi85
 */
public interface TableModelWithTooltip {

    String getTooltipAt(int rowIndex, int columnIndex);

}
