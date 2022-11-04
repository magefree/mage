package mage.cards.v;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class VeteransPowerblade extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.SOLDIER, "Soldier");

    public VeteransPowerblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip Soldier {W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{W}"), new TargetPermanent(filter)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private VeteransPowerblade(final VeteransPowerblade card) {
        super(card);
    }

    @Override
    public VeteransPowerblade copy() {
        return new VeteransPowerblade(this);
    }
}
