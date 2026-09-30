package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterOpponent;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentOrPlayerPredicate;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DanithaSpearOfAgony extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets an opponent or a creature an opponent controls");

    static {
        filter.add(new TargetsPermanentOrPlayerPredicate(
            StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, new FilterOpponent()
        ));
    }

    public DanithaSpearOfAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you cast a spell that targets an opponent or a creature an opponent controls, put a +1/+1 counter on Danitha.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));
    }

    private DanithaSpearOfAgony(final DanithaSpearOfAgony card) {
        super(card);
    }

    @Override
    public DanithaSpearOfAgony copy() {
        return new DanithaSpearOfAgony(this);
    }
}
