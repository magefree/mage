
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DromarsCharm extends CardImpl {

    public DromarsCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}{B}");


        // Choose one - You gain 5 life; or counter target spell; or target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(5));
        Mode mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetSpell());
        this.getSpellAbility().addMode(mode);
        mode = new Mode(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private DromarsCharm(final DromarsCharm card) {
        super(card);
    }

    @Override
    public DromarsCharm copy() {
        return new DromarsCharm(this);
    }
}
