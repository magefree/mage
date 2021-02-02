package mage.cards.f;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FerozsBan extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Creature spells");

    public FerozsBan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Creature spells cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasingAllEffect(2, filter, TargetController.ANY)));
    }

    private FerozsBan(final FerozsBan card) {
        super(card);
    }

    @Override
    public FerozsBan copy() {
        return new FerozsBan(this);
    }
}
