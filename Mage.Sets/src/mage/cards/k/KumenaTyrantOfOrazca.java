
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class KumenaTyrantOfOrazca extends CardImpl {

    private static final FilterControlledPermanent filterAnotherNotTapped = new FilterControlledPermanent("another untapped Merfolk you control");
    private static final FilterControlledPermanent filterNotTapped = new FilterControlledPermanent("untapped Merfolk you control");
    private static final FilterControlledPermanent filterAll = new FilterControlledPermanent("Merfolk you control");

    static {
        filterAnotherNotTapped.add(AnotherPredicate.instance);
        filterAnotherNotTapped.add(SubType.MERFOLK.getPredicate());
        filterAnotherNotTapped.add(TappedPredicate.UNTAPPED);

        filterNotTapped.add(SubType.MERFOLK.getPredicate());
        filterNotTapped.add(TappedPredicate.UNTAPPED);

        filterAll.add(SubType.MERFOLK.getPredicate());
    }

    public KumenaTyrantOfOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Tap another untapped Merfolk you control: Kumena, Tyrant of Orzca can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(1, 1, filterAnotherNotTapped, true))));

        // Tap three untapped Merfolk you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(new TargetControlledPermanent(3, 3, filterNotTapped, true))));

        // Tap five untapped Merfolk you control: Put a +1/+1 counter on each Merfolk you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filterAll),
                new TapTargetCost(new TargetControlledPermanent(5, 5, filterNotTapped, true))));

    }

    private KumenaTyrantOfOrazca(final KumenaTyrantOfOrazca card) {
        super(card);
    }

    @Override
    public KumenaTyrantOfOrazca copy() {
        return new KumenaTyrantOfOrazca(this);
    }
}
