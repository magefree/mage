package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.SkipCombatStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MomentOfSilence extends CardImpl {
    
    public MomentOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target player skips their next combat phase this turn.
        this.getSpellAbility().addEffect(new SkipCombatStepEffect(Duration.EndOfTurn).setText("Target player skips their next combat phase this turn"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }
    
    private MomentOfSilence(final MomentOfSilence card) {
        super(card);
    }
    
    @Override
    public MomentOfSilence copy() {
        return new MomentOfSilence(this);
    }
}
