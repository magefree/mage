package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DrawNthOrNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValueCompareToCountersSourceCountPredicate;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class LadyOctopusInspiredInventor extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("an artifact spell from your hand with mana value less than or " +
            "equal to the number of ingenuity counters on {this}");

    static {
        filter.add(new ManaValueCompareToCountersSourceCountPredicate(CounterType.INGENUITY, ComparisonType.OR_LESS));
    }

    public LadyOctopusInspiredInventor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever you draw your first or second card each turn, put an ingenuity counter on Lady Octopus.
        this.addAbility(new DrawNthOrNthCardTriggeredAbility(new AddCountersSourceEffect(CounterType.INGENUITY.createInstance())));

        // {T}: You may cast an artifact spell from your hand with mana value less than or equal to the number of ingenuity counters on Lady Octopus without paying its mana cost.
        this.addAbility(new SimpleActivatedAbility(new CastFromHandForFreeEffect(filter), new TapSourceCost()));
    }

    private LadyOctopusInspiredInventor(final LadyOctopusInspiredInventor card) {
        super(card);
    }

    @Override
    public LadyOctopusInspiredInventor copy() {
        return new LadyOctopusInspiredInventor(this);
    }
}