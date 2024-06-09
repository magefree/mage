


package mage.filter.common;

import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 * @author TheElk801
 */
public class FilterEquipmentPermanent extends FilterPermanent {

    public FilterEquipmentPermanent() {
        this("Equipment");
    }

    public FilterEquipmentPermanent(String name) {
        super(name);
        this.add(SubType.EQUIPMENT.getPredicate());
    }

    protected FilterEquipmentPermanent(final FilterEquipmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterEquipmentPermanent copy() {
        return new FilterEquipmentPermanent(this);
    }
}
