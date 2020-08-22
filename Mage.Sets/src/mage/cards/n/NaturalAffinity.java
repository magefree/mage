
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author Plopman
 */
public final class NaturalAffinity extends CardImpl {

    public NaturalAffinity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // All lands become 2/2 creatures until end of turn. They're still lands.
        this.getSpellAbility().addEffect(new BecomesCreatureAllEffect(
                new CreatureToken(2, 2),
                "lands", StaticFilters.FILTER_LANDS, Duration.EndOfTurn, false));
    }

    public NaturalAffinity(final NaturalAffinity card) {
        super(card);
    }

    @Override
    public NaturalAffinity copy() {
        return new NaturalAffinity(this);
    }
}