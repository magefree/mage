package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HawkeyeBowslinger extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets a creature");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    public HawkeyeBowslinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a spell that targets a creature, put a +1/+1 counter on Hawkeye. Scry 1.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        );
        ability.addEffect(new ScryEffect(1));
        this.addAbility(ability);
    }

    private HawkeyeBowslinger(final HawkeyeBowslinger card) {
        super(card);
    }

    @Override
    public HawkeyeBowslinger copy() {
        return new HawkeyeBowslinger(this);
    }
}
