
package mage.cards.f;

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
public final class FalsePeace extends CardImpl {

    public FalsePeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // Target player skips all combat phases of their next turn.
        this.getSpellAbility().addEffect(new SkipNextCombatEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public FalsePeace(final FalsePeace card) {
        super(card);
    }

    @Override
    public FalsePeace copy() {
        return new FalsePeace(this);
    }
}
