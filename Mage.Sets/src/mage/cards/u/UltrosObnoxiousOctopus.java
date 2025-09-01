package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaSpentToCastPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltrosObnoxiousOctopus extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a noncreature spell, if at least eight mana was spent to cast it");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaSpentToCastPredicate(ComparisonType.MORE_THAN, 7));
    }

    public UltrosObnoxiousOctopus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a noncreature spell, if at least four mana was spent to cast it, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new TapTargetEffect(), StaticFilters.FILTER_NONCREATURE_SPELL_FOUR_MANA_SPENT, false
        );
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, if at least eight mana was spent to cast it, put eight +1/+1 counters on Ultros.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(8)), filter, false
        ));
    }

    private UltrosObnoxiousOctopus(final UltrosObnoxiousOctopus card) {
        super(card);
    }

    @Override
    public UltrosObnoxiousOctopus copy() {
        return new UltrosObnoxiousOctopus(this);
    }
}
