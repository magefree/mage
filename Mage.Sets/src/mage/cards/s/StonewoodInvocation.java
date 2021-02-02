
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class StonewoodInvocation extends CardImpl {

    public StonewoodInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // Split second
        this.addAbility(new SplitSecondAbility());
        
        // Target creature gets +5/+5 and gains shroud until end of turn.
        Effect effect = new BoostTargetEffect(5, 5, Duration.EndOfTurn);
        effect.setText("Target creature gets +5/+5");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(ShroudAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains shroud until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StonewoodInvocation(final StonewoodInvocation card) {
        super(card);
    }

    @Override
    public StonewoodInvocation copy() {
        return new StonewoodInvocation(this);
    }
}
