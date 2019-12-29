package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtSponge extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public ThoughtSponge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPONGE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Thought Sponge enters the battlefield with a number of +1/+1 counters on it equal to the greatest number of cards an opponent has drawn this turn.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ThoughtSpongeValue.instance, false
        ), "with a number of +1/+1 counters on it equal to " +
                "the greatest number of cards an opponent has drawn this turn"
        ), new CardsDrawnThisTurnWatcher());

        // When Thought Sponge dies, draw cards equal to its power.
        this.addAbility(new DiesTriggeredAbility(
                new DrawCardSourceControllerEffect(xValue).setText("draw cards equal to its power")
        ));
    }

    private ThoughtSponge(final ThoughtSponge card) {
        super(card);
    }

    @Override
    public ThoughtSponge copy() {
        return new ThoughtSponge(this);
    }
}

enum ThoughtSpongeValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return game.getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(watcher::getCardsDrawnThisTurn)
                .max(Integer::compare)
                .get();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}