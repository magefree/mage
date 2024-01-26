package mage.cards.t;

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
public final class ThinkingCap extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.DETECTIVE, "Detective");

    public ThinkingCap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2)));

        // Equip Detective {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetPermanent(filter)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private ThinkingCap(final ThinkingCap card) {
        super(card);
    }

    @Override
    public ThinkingCap copy() {
        return new ThinkingCap(this);
    }
}
