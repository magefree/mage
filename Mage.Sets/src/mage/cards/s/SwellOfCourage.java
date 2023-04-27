
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class SwellOfCourage extends CardImpl {

    public SwellOfCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}{W}");


        // Creatures you control get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2,2, Duration.EndOfTurn));
        // Reinforce X-{X}{W}{W}
        this.addAbility(new ReinforceAbility(ManacostVariableValue.REGULAR, new ManaCostsImpl<>("{X}{W}{W}")));
    }

    private SwellOfCourage(final SwellOfCourage card) {
        super(card);
    }

    @Override
    public SwellOfCourage copy() {
        return new SwellOfCourage(this);
    }
}
