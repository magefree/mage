
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class BurstOfSpeed extends CardImpl {

    public BurstOfSpeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false));
    }

    private BurstOfSpeed(final BurstOfSpeed card) {
        super(card);
    }

    @Override
    public BurstOfSpeed copy() {
        return new BurstOfSpeed(this);
    }
}
