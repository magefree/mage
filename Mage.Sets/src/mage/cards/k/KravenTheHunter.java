package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class KravenTheHunter extends CardImpl {
    private static final FilterPermanent filter = new FilterCreaturePermanent("creature an opponent controls with the greatest power among creatures that player controls");

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }
    public KravenTheHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature an opponent controls with the greatest power among creatures that player controls dies, draw a card and put a +1/+1 counter on Kraven the Hunter.
        Ability ability = new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), false, filter);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .concatBy("and"));
        this.addAbility(ability);
    }

    private KravenTheHunter(final KravenTheHunter card) {
        super(card);
    }

    @Override
    public KravenTheHunter copy() {
        return new KravenTheHunter(this);
    }
}
