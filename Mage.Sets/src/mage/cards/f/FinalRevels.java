
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class FinalRevels extends CardImpl {

    public FinalRevels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        this.getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false));
        Mode mode = new Mode();
        mode.getEffects().add(new BoostAllEffect(0, -2, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false));
        this.getSpellAbility().addMode(mode);
    }

    public FinalRevels(final FinalRevels card) {
        super(card);
    }

    @Override
    public FinalRevels copy() {
        return new FinalRevels(this);
    }
}
