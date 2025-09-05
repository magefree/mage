package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationEngineer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another target creature or Vehicle you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public FireNationEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Raid -- At the beginning of your end step, if you attacked this turn, put a +1/+1 counter on another target creature or Vehicle you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        ).withInterveningIf(RaidCondition.instance);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private FireNationEngineer(final FireNationEngineer card) {
        super(card);
    }

    @Override
    public FireNationEngineer copy() {
        return new FireNationEngineer(this);
    }
}
