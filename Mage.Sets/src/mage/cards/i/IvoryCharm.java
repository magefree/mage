
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class IvoryCharm extends CardImpl {

    public IvoryCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Choose one - All creatures get -2/-0 until end of turn
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, 0, Duration.EndOfTurn));
        // or tap target creature
        Mode mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or prevent the next 1 damage that would be dealt to any target this turn.
        mode = new Mode(new PreventDamageToTargetEffect(Duration.EndOfTurn, 1));
        mode.addTarget(new TargetAnyTarget());
        this.getSpellAbility().addMode(mode);
    }

    private IvoryCharm(final IvoryCharm card) {
        super(card);
    }

    @Override
    public IvoryCharm copy() {
        return new IvoryCharm(this);
    }
}
