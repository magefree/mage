package mage.cards.b;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BurstOfSpeed extends CardImpl {

    public BurstOfSpeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, false
        ));
    }

    private BurstOfSpeed(final BurstOfSpeed card) {
        super(card);
    }

    @Override
    public BurstOfSpeed copy() {
        return new BurstOfSpeed(this);
    }
}
