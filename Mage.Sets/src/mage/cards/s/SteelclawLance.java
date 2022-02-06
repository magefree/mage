package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SteelclawLance extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.KNIGHT, "Knight");

    public SteelclawLance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 2)));

        // Equip Knight {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetControlledCreaturePermanent(filter), false));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));

    }

    private SteelclawLance(final SteelclawLance card) {
        super(card);
    }

    @Override
    public SteelclawLance copy() {
        return new SteelclawLance(this);
    }
}
