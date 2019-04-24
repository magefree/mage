
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SimicCharm extends CardImpl {

    public SimicCharm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{U}");

        //Choose one - Target creature gets +3/+3 until end of turn
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        //permanents you control gain hexproof until end of turn
        Mode mode = new Mode();
        mode.getEffects().add(new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, new FilterControlledPermanent()));
        this.getSpellAbility().addMode(mode);
        //return target creature to its owner's hand.
        Mode mode2 = new Mode();
        mode2.getEffects().add(new ReturnToHandTargetEffect());
        mode2.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode2);
        
    }

    public SimicCharm(final SimicCharm card) {
        super(card);
    }

    @Override
    public SimicCharm  copy() {
        return new SimicCharm(this);
    }
}
