
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class Enrage extends CardImpl {

    public Enrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");


        // Target creature gets +X/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Enrage(final Enrage card) {
        super(card);
    }

    @Override
    public Enrage copy() {
        return new Enrage(this);
    }
}
