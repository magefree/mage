package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;

import java.util.UUID;

/**
 * @author padfoot
 */
public final class WilfredMott extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland permanent card with mana value 3 or less");
    private static final DynamicValue xValue = new CountersSourceCount(CounterType.TIME);

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public WilfredMott(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Look to the Stars -- At the beginning of your upkeep, put a time counter on Wilfred Mott. Then look at the top X cards of your library, where X is the number of time counters on Wilfred Mott. You may put a nonland permanent card with mana value 3 or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.TIME.createInstance())
        ).withFlavorWord("Look to the Stars");
        ability.addEffect(new LookLibraryAndPickControllerEffect(
                xValue, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        ).setText("Then look at the top X cards of your library, where X is the number of time counters on {this}. " +
                "You may put a nonland permanent card with mana value 3 or less from among them onto the battlefield. " +
                "Put the rest on the bottom of your library in a random order."));
        this.addAbility(ability);
    }

    private WilfredMott(final WilfredMott card) {
        super(card);
    }

    @Override
    public WilfredMott copy() {
        return new WilfredMott(this);
    }
}
