
package mage.cards.i;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class IronWill extends CardImpl {

    public IronWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Target creature gets +0/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(0, 4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private IronWill(final IronWill card) {
        super(card);
    }

    @Override
    public IronWill copy() {
        return new IronWill(this);
    }
}
