package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.PutCounterOnCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class OmarthisGhostfireInitiate extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another colorless creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ColorlessPredicate.instance);
    }

    public OmarthisGhostfireInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.NAGA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Omarthis, Ghostfire Initiate enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
            new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // Whenever you put one or more +1/+1 counters on another colorless creature, you may put a +1/+1 counter on Omarthis.
        this.addAbility(
            new PutCounterOnCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                CounterType.P1P1.createInstance(), filter,
                false, true
            )
        );

        // When Omarthis dies, manifest a number of cards from the top of your library equal to the number of counters on it.
        this.addAbility(new DiesSourceTriggeredAbility(
            new ManifestEffect(new CountersSourceCount(null))
                .setText("manifest a number of cards from the top of your library equal to the number of counters on it."),
            false
        ));
    }

    private OmarthisGhostfireInitiate(final OmarthisGhostfireInitiate card) {
        super(card);
    }

    @Override
    public OmarthisGhostfireInitiate copy() {
        return new OmarthisGhostfireInitiate(this);
    }
}
