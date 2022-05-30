package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeardedAxe extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                SubType.DWARF.getPredicate(),
                SubType.EQUIPMENT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Dwarves, Equipment, and/or Vehicles you control", xValue);

    public BeardedAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each Dwarf, Equipment, and/or Vehicle you control.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)
                .setText("equipped creature gets +1/+1 for each Dwarf, Equipment, and/or Vehicle you control")
        ).addHint(hint));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private BeardedAxe(final BeardedAxe card) {
        super(card);
    }

    @Override
    public BeardedAxe copy() {
        return new BeardedAxe(this);
    }
}
