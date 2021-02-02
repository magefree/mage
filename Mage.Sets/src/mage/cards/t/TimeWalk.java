
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TimeWalk extends CardImpl {

    public TimeWalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Take an extra turn after this one.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
    }

    private TimeWalk(final TimeWalk card) {
        super(card);
    }

    @Override
    public TimeWalk copy() {
        return new TimeWalk(this);
    }
}
