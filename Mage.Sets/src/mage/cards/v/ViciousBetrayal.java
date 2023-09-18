package mage.cards.v;

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

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ViciousBetrayal extends CardImpl {

    public ViciousBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");


        // As an additional cost to cast Vicious Betrayal, sacrifice any number of creatures.
        this.getSpellAbility().addCost(new SacrificeXTargetCost(new FilterControlledCreaturePermanent(), true));
        // Target creature gets +2/+2 until end of turn for each creature sacrificed this way.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(GetXValue.instance, GetXValue.instance, Duration.EndOfTurn));
    }

    private ViciousBetrayal(final ViciousBetrayal card) {
        super(card);
    }

    @Override
    public ViciousBetrayal copy() {
        return new ViciousBetrayal(this);
    }
}

enum GetXValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        for (VariableCost cost : sourceAbility.getCosts().getVariableCosts()) {
            amount += cost.getAmount();
        }
        return 2 * amount;
    }

    @Override
    public GetXValue copy() {
        return GetXValue.instance;
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
