package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuskoClockmaker extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("permanent you control named Midnight Clock");

    static {
        filter.add(new NamePredicate("Midnight Clock"));
    }

    public RuskoClockmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Rusko, Clockmaker enters, conjure a card named Midnight Clock onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ConjureCardEffect("Midnight Clock", Zone.BATTLEFIELD, 1)
        ));

        // Whenever you cast a noncreature spell, put an hour counter on each permanent you control named Midnight Clock. Each opponent loses 1 life and you gain 1 life.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersAllEffect(CounterType.HOUR.createInstance(), filter),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new LoseLifeOpponentsEffect(1));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
    }

    private RuskoClockmaker(final RuskoClockmaker card) {
        super(card);
    }

    @Override
    public RuskoClockmaker copy() {
        return new RuskoClockmaker(this);
    }
}
