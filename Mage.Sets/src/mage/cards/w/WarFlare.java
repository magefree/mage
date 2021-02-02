
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class WarFlare extends CardImpl {

    public WarFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{W}");

        // Creatures you control get +2/+1 until end of turn.  Untap those creatures.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, "Untap those creatures"));
    }

    private WarFlare(final WarFlare card) {
        super(card);
    }

    @Override
    public WarFlare copy() {
        return new WarFlare(this);
    }
}
