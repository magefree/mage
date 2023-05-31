package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DunedainBlade extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.HUMAN, "Human");

    public DunedainBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip Human {1}
        this.addAbility(new EquipAbility(
                Outcome.BoostCreature, new GenericManaCost(1),
                new TargetPermanent(filter), false
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private DunedainBlade(final DunedainBlade card) {
        super(card);
    }

    @Override
    public DunedainBlade copy() {
        return new DunedainBlade(this);
    }
}
