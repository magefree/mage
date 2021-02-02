package mage.cards.n;

import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Necrologia extends CardImpl {

    public Necrologia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Cast Necrologia only during your end step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, PhaseStep.END_TURN, MyTurnCondition.instance,
                "Cast this spell only during your end step"));

        // As an additional cost to cast to Necrologia, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));

        // Draw X cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(GetXValue.instance));
    }

    private Necrologia(final Necrologia card) {
        super(card);
    }

    @Override
    public Necrologia copy() {
        return new Necrologia(this);
    }
}
