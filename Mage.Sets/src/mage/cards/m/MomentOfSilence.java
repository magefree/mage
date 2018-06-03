
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.SkipNextCombatEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MomentOfSilence extends CardImpl {

    public MomentOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target player skips their next combat phase this turn.
        this.getSpellAbility().addEffect(new SkipNextCombatEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public MomentOfSilence(final MomentOfSilence card) {
        super(card);
    }

    @Override
    public MomentOfSilence copy() {
        return new MomentOfSilence(this);
    }
}
