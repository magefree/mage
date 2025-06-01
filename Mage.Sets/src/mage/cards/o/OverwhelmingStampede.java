
package mage.cards.o;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class OverwhelmingStampede extends CardImpl {

    public OverwhelmingStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Until end of turn, creatures you control gain trample and get +X/+X, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("Until end of turn, creatures you control gain trample"));
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES, GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES,
                Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText("and get +X/+X, where X is the greatest power among creatures you control"));
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
    }

    private OverwhelmingStampede(final OverwhelmingStampede card) {
        super(card);
    }

    @Override
    public OverwhelmingStampede copy() {
        return new OverwhelmingStampede(this);
    }
}