
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class ViciousBetrayal extends CardImpl {

    public ViciousBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // As an additional cost to cast Vicious Betrayal, sacrifice any number of creatures.
        this.getSpellAbility().addCost(new SacrificeXTargetCost(new FilterControlledCreaturePermanent()));
        // Target creature gets +2/+2 until end of turn for each creature sacrificed this way.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(new GetXValue(), new GetXValue(), Duration.EndOfTurn));
    }

    public ViciousBetrayal(final ViciousBetrayal card) {
        super(card);
    }

    @Override
    public ViciousBetrayal copy() {
        return new ViciousBetrayal(this);
    }
}

class GetXValue implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        for (VariableCost cost: sourceAbility.getCosts().getVariableCosts()) {
            amount += cost.getAmount();
        }
        return 2*amount;
    }

    @Override
    public GetXValue copy() {
        return new GetXValue();
    }

    @Override
    public String toString() {
        return "2";
    }

    @Override
    public String getMessage() {
        return "creature sacrificed this way";
    }
}
