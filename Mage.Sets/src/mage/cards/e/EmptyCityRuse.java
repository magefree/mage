
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.SkipNextCombatEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EmptyCityRuse extends CardImpl {

    public EmptyCityRuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // Target opponent skips all combat phases of their next turn.
        this.getSpellAbility().addEffect(new SkipNextCombatEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public EmptyCityRuse(final EmptyCityRuse card) {
        super(card);
    }

    @Override
    public EmptyCityRuse copy() {
        return new EmptyCityRuse(this);
    }
}