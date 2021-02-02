package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SphereOfResistance extends CardImpl {

    public SphereOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostIncreasingAllEffect(1, new FilterCard("Spells"), TargetController.ANY))
        );
    }

    private SphereOfResistance(final SphereOfResistance card) {
        super(card);
    }

    @Override
    public SphereOfResistance copy() {
        return new SphereOfResistance(this);
    }
}
