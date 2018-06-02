
package mage.client.util;

import java.util.List;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author LevelX2
 */
public class MageTableRowSorter extends TableRowSorter<TableModel> {

    public MageTableRowSorter(TableModel m) {
        super(m);
    }
    /**
     * @param column
     * @inherited <p>
     */
    @Override
    public void toggleSortOrder(int column) {
        List<? extends SortKey> sortKeys = getSortKeys();
        if (!sortKeys.isEmpty()) {
            if (sortKeys.get(0).getSortOrder() == SortOrder.DESCENDING) {
                setSortKeys(null);
                return;
            }
        }
        super.toggleSortOrder(column);
    }
}
