package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinOriflamme extends CardImpl {

    public GoblinOriflamme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Attacking creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        )));
    }

    private GoblinOriflamme(final GoblinOriflamme card) {
        super(card);
    }

    @Override
    public GoblinOriflamme copy() {
        return new GoblinOriflamme(this);
    }
}
