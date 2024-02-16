package mage.cards.r;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author DominionSpy
 */
public final class RepulsiveMutation extends CardImpl {

    public RepulsiveMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{U}");

        // Put X +1/+1 counters on target creature you control.
        getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), ManacostVariableValue.REGULAR));
        getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Then counter up to one target spell unless its controller pays mana equal to the greatest power among creatures you control.
        getSpellAbility().addEffect(new CounterUnlessPaysEffect(GreatestPowerAmongControlledCreaturesValue.instance)
                .setTargetPointer(new SecondTargetPointer())
                .setText("Then counter up to one target spell unless its controller pays mana equal to the greatest power among creatures you control."));
        getSpellAbility().addTarget(new TargetSpell(0, 1, StaticFilters.FILTER_SPELL));
    }

    private RepulsiveMutation(final RepulsiveMutation card) {
        super(card);
    }

    @Override
    public RepulsiveMutation copy() {
        return new RepulsiveMutation(this);
    }
}
