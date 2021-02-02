package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.SkipNextDrawStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class Fatigue extends CardImpl {
    
    private static final String rule = "Target player skips their next draw step.";

    public Fatigue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");
        

        // Target player skips their next draw step.
        this.getSpellAbility().addEffect(new SkipNextDrawStepTargetEffect().setText(rule));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
    }

    private Fatigue(final Fatigue card) {
        super(card);
    }

    @Override
    public Fatigue copy() {
        return new Fatigue(this);
    }
}
