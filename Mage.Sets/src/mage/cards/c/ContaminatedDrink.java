package mage.cards.c;

import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ContaminatedDrink extends CardImpl {

    public ContaminatedDrink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{B}");

        // Draw X cards, then you get half X rad counters, rounded up.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new AddCountersPlayersEffect(
                CounterType.RAD.createInstance(), new HalfValue(ManacostVariableValue.REGULAR, true), TargetController.YOU
        ).setText(", then you get half X rad counters, rounded up"));
    }

    private ContaminatedDrink(final ContaminatedDrink card) {
        super(card);
    }

    @Override
    public ContaminatedDrink copy() {
        return new ContaminatedDrink(this);
    }
}
