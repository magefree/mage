
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageToPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author spjspj
 */
public final class CommencementOfFestivities extends CardImpl {

    public CommencementOfFestivities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Prevent all combat damage that would be dealt to players this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToPlayersEffect(Duration.EndOfTurn, true));
    }

    private CommencementOfFestivities(final CommencementOfFestivities card) {
        super(card);
    }

    @Override
    public CommencementOfFestivities copy() {
        return new CommencementOfFestivities(this);
    }
}
