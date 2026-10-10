package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ChiefOfTheWilds extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WOLF, "another Wolf you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final FilterPermanent filter2 = new FilterControlledPermanent("another Wolf or battle you control");

    static {
        filter2.add(AnotherPredicate.instance);
        filter2.add(Predicates.or(SubType.WOLF.getPredicate(), CardType.BATTLE.getPredicate()));
    }

    public ChiefOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever another Wolf you control enters, put two +1/+1 counters on Chief of the Wilds.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), filter
        ));

        // If an ability of another Wolf or battle you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter2)));
    }

    private ChiefOfTheWilds(final ChiefOfTheWilds card) {
        super(card);
    }

    @Override
    public ChiefOfTheWilds copy() {
        return new ChiefOfTheWilds(this);
    }
}
