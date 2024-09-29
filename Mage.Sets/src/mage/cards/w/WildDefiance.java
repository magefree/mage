package mage.cards.w;

import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class WildDefiance extends CardImpl {

    public WildDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(
                new BoostTargetEffect(3, 3, Duration.EndOfTurn).setText("that creature gets +3/+3 until end of turn"),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY));
    }

    private WildDefiance(final WildDefiance card) {
        super(card);
    }

    @Override
    public WildDefiance copy() {
        return new WildDefiance(this);
    }
}
