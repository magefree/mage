package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.StarfishToken;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author spike
 */
public final class SpinyStarfish extends CardImpl {

    public SpinyStarfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.STARFISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {U}: Regenerate Spiny Starfish.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{U}")));

        // At the beginning of each end step, if Spiny Starfish regenerated this turn, create a 0/1 blue Starfish creature token for each time it regenerated this turn.
        this.addAbility(
                new ConditionalInterveningIfTriggeredAbility(
                        new BeginningOfEndStepTriggeredAbility(
                                new CreateTokenEffect(
                                        new StarfishToken(),
                                        new SpinyStarfishDynamicValue()),
                                TargetController.ANY,
                                false),
                        SpinyStarfishCondition.instance,
                        "At the beginning of each end step, if {this} regenerated this turn, create a 0/1 blue Starfish creature token for each time it regenerated this turn."),
                new SpinyStarfishWatcher());
    }

    private SpinyStarfish(final SpinyStarfish card) {
        super(card);
    }

    @Override
    public SpinyStarfish copy() {
        return new SpinyStarfish(this);
    }
}

enum SpinyStarfishCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpinyStarfishWatcher watcher = game.getState().getWatcher(SpinyStarfishWatcher.class);
        return watcher != null && watcher.regeneratedCount(source.getSourceId()) != 0;
    }

    @Override
    public String toString() {
        return "if Spiny Starfish regenerated this turn";
    }

}

class SpinyStarfishWatcher extends Watcher {

    // Probably dumb to record all regeneration events, could just record this,
    // but not sure how to know what source this watcher is attached to.
    private final Map<UUID, Integer> regeneratedCount = new HashMap<>();

    public SpinyStarfishWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.REGENERATED) {
            UUID regeneratedId = event.getTargetId();
            Integer count = regeneratedCount.get(regeneratedId);
            if (count == null) {
                count = 0;
            }
            regeneratedCount.put(regeneratedId, ++count);
        }
    }

    @Override
    public void reset() {
        super.reset();
        regeneratedCount.clear();
    }

    public int regeneratedCount(UUID sourceId) {
        return regeneratedCount.getOrDefault(sourceId, 0);
    }

}

class SpinyStarfishDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        SpinyStarfishWatcher watcher = game.getState().getWatcher(
                SpinyStarfishWatcher.class);
        if (watcher != null) {
            return watcher.regeneratedCount(sourceAbility.getSourceId());
        }
        return 0;
    }

    @Override
    public SpinyStarfishDynamicValue copy() {
        return new SpinyStarfishDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "time {this} regenerated this turn";
    }
}
