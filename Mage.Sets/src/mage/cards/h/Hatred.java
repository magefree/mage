
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author magenoxx
 */
public final class Hatred extends CardImpl {

    public Hatred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{B}");


        // As an additional cost to cast Hatred, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));

        // Target creature gets +X/+0 until end of turn.
        DynamicValue xValue = GetXValue.instance;
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Hatred(final Hatred card) {
        super(card);
    }

    @Override
    public Hatred copy() {
        return new Hatred(this);
    }
}
