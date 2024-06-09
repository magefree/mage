package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheThirteenthDoctor extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell from anywhere other than your hand");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("each creature you control with a counter on it");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
        filter2.add(CounterAnyPredicate.instance);
    }

    public TheThirteenthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, put a +1/+1 counter on target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filter, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.PARADOX));

        // Team TARDIS -- At the beginning of your end step, untap each creature you control with a counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new UntapAllEffect(filter2), TargetController.YOU, false
        ).withFlavorWord("Team TARDIS"));

    }

    private TheThirteenthDoctor(final TheThirteenthDoctor card) {
        super(card);
    }

    @Override
    public TheThirteenthDoctor copy() {
        return new TheThirteenthDoctor(this);
    }
}
