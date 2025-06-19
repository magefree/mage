package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.StarfishToken;
import mage.util.CardUtil;
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
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{U}")));

        // At the beginning of each end step, if Spiny Starfish regenerated this turn, create a 0/1 blue Starfish creature token for each time it regenerated this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new StarfishToken(), SpinyStarfishValue.instance), false
        ).withInterveningIf(SpinyStarfishCondition.instance), new SpinyStarfishWatcher());
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
        return SpinyStarfishWatcher.regeneratedCount(game, source) > 0;
    }

    @Override
    public String toString() {
        return "{this} regenerated this turn";
    }
}

enum SpinyStarfishValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return SpinyStarfishWatcher.regeneratedCount(game, sourceAbility);
    }

    @Override
    public SpinyStarfishValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "time it regenerated this turn";
    }
}

class SpinyStarfishWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> regeneratedCount = new HashMap<>();

    public SpinyStarfishWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.REGENERATED) {
            regeneratedCount.compute(new MageObjectReference(event.getTargetId(), game), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        regeneratedCount.clear();
    }

    static int regeneratedCount(Game game, Ability source) {
        return game.getState()
                .getWatcher(SpinyStarfishWatcher.class)
                .regeneratedCount
                .getOrDefault(new MageObjectReference(source.getSourcePermanentOrLKI(game), game), 0);
    }
}
