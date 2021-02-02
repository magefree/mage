
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ToxicDeluge extends CardImpl {

    public ToxicDeluge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // As an additional cost to cast Toxic Deluge, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));
        // All creatures get -X/-X until end of turn.
        DynamicValue xValue = new SignInversionDynamicValue(GetXValue.instance);
        this.getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn, new FilterCreaturePermanent("All creatures"), false,
                null, true));
    }

    private ToxicDeluge(final ToxicDeluge card) {
        super(card);
    }

    @Override
    public ToxicDeluge copy() {
        return new ToxicDeluge(this);
    }
}
