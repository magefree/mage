
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.RecoverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Resize extends CardImpl {

    public Resize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        
        // Recover {1}{G}
        this.addAbility(new RecoverAbility(new ManaCostsImpl<>("{1}{G}"), this));
    }

    private Resize(final Resize card) {
        super(card);
    }

    @Override
    public Resize copy() {
        return new Resize(this);
    }
}
