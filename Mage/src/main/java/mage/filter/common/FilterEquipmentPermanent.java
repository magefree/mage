


package mage.filter.common;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public class FilterEquipmentPermanent extends FilterPermanent {

    public FilterEquipmentPermanent() {
        this("equipment");
    }

    public FilterEquipmentPermanent(String name) {
        super(name);
        this.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public FilterEquipmentPermanent(final FilterEquipmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterEquipmentPermanent copy() {
        return new FilterEquipmentPermanent(this);
    }
}
