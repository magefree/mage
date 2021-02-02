
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageToPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class DefendTheHearth extends CardImpl {

    public DefendTheHearth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Prevent all combat damage that would be dealt to players this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToPlayersEffect(Duration.EndOfTurn, true));
    }

    private DefendTheHearth(final DefendTheHearth card) {
        super(card);
    }

    @Override
    public DefendTheHearth copy() {
        return new DefendTheHearth(this);
    }
}
