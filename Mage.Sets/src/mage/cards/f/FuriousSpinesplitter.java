package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FuriousSpinesplitter extends CardImpl {

    public FuriousSpinesplitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/G}{R/G}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, put a +1/+1 counter on Furious Spinesplitter for each opponent that was dealt damage this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), FuriousSpinesplitterValue.instance
        )), new AmountOfDamageAPlayerReceivedThisTurnWatcher());
    }

    private FuriousSpinesplitter(final FuriousSpinesplitter card) {
        super(card);
    }

    @Override
    public FuriousSpinesplitter copy() {
        return new FuriousSpinesplitter(this);
    }
}

enum FuriousSpinesplitterValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game
                        .getState()
                        .getWatcher(AmountOfDamageAPlayerReceivedThisTurnWatcher.class)
                        ::getAmountOfDamageReceivedThisTurn)
                .mapToInt(x -> Math.min(x, 1))
                .sum();
    }

    @Override
    public FuriousSpinesplitterValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "opponent that was dealt damage this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
