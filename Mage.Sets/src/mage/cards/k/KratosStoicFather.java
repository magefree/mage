package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KratosStoicFather extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOD);
    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public KratosStoicFather(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you attack with one or more Gods and whenever a God dies, you get an experience counter.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU),
                new AttacksWithCreaturesTriggeredAbility(null, 1, filter),
                new DiesCreatureTriggeredAbility(null, false, filter)
        ).setTriggerPhrase("Whenever you attack with one or more Gods and whenever a God dies, "));

        // At the beginning of your end step, put a number of +1/+1 counters on target creature equal to the number of experience counters you have.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), xValue)
                        .setText(" put a number of +1/+1 counters on target creature " +
                                "equal to the number of experience counters you have")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Partner--Father & son
        this.addAbility(PartnerVariantType.FATHER_AND_SON.makeAbility());
    }

    private KratosStoicFather(final KratosStoicFather card) {
        super(card);
    }

    @Override
    public KratosStoicFather copy() {
        return new KratosStoicFather(this);
    }
}
