package mage.cards.f;

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
public final class FalsePeace extends CardImpl {
    
    public FalsePeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Target player skips all combat phases of their next turn.
        this.getSpellAbility().addEffect(new SkipCombatStepEffect(Duration.UntilYourNextTurn).setText("Target player skips all combat phases of their next turn."));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }
    
    private FalsePeace(final FalsePeace card) {
        super(card);
    }
    
    @Override
    public FalsePeace copy() {
        return new FalsePeace(this);
    }
}
