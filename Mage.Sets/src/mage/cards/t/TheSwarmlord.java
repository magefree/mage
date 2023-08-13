package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CommanderCastCountValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSwarmlord extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(CommanderCastCountValue.instance, 2);
    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public TheSwarmlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Rapid Regeneration -- The Swarmlord enters the battlefield with two +1/+1 counters on it for each time you've cast your commander from the command zone this game.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), xValue, true),
                "with two +1/+1 counters on it for each time you've cast your commander from the command zone this game"
        ).addHint(CommanderCastCountValue.getHint()).withFlavorWord("Rapid Regeneration"));

        // Xenos Cunning -- Whenever a creature you control with a counter on it dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter
        ).withFlavorWord("Xenos Cunning"));
    }

    private TheSwarmlord(final TheSwarmlord card) {
        super(card);
    }

    @Override
    public TheSwarmlord copy() {
        return new TheSwarmlord(this);
    }
}
