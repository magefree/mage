package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class DampeningPulse extends CardImpl {

    public DampeningPulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");

        // Creatures your opponents control get -1/-0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(-1, -0, Duration.WhileOnBattlefield, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false)));
    }

    private DampeningPulse(final DampeningPulse card) {
        super(card);
    }

    @Override
    public DampeningPulse copy() {
        return new DampeningPulse(this);
    }
}
