
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LoneFox
 */
public final class LastChance extends CardImpl {

    public LastChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{R}");

        // Take an extra turn after this one. At the beginning of that turn's end step, you lose the game.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect(true));
    }

    private LastChance(final LastChance card) {
        super(card);
    }

    @Override
    public LastChance copy() {
        return new LastChance(this);
    }
}
