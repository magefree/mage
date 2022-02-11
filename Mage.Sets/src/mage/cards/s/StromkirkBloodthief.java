package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StromkirkBloodthief extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VAMPIRE);

    public StromkirkBloodthief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if an opponent lost life this turn, put a +1/+1 counter on target Vampire you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, OpponentsLostLifeCondition.instance, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.addHint(OpponentsLostLifeHint.instance));
    }

    private StromkirkBloodthief(final StromkirkBloodthief card) {
        super(card);
    }

    @Override
    public StromkirkBloodthief copy() {
        return new StromkirkBloodthief(this);
    }
}
