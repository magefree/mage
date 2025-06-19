package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.ZombieBerserkerToken;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiseOfTheDreadMarn extends CardImpl {

    public RiseOfTheDreadMarn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Create X 2/2 black Zombie Berserker creature tokens, where X is the number of nontoken creatures that died this turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new ZombieBerserkerToken(), RiseOfTheDreadMarnValue.instance
        ));
        this.getSpellAbility().addWatcher(new RiseOfTheDreadMarnWatcher());
        this.getSpellAbility().addHint(RiseOfTheDreadMarnValue.getHint());

        // Foretell {B}
        this.addAbility(new ForetellAbility(this, "{B}"));
    }

    private RiseOfTheDreadMarn(final RiseOfTheDreadMarn card) {
        super(card);
    }

    @Override
    public RiseOfTheDreadMarn copy() {
        return new RiseOfTheDreadMarn(this);
    }
}

enum RiseOfTheDreadMarnValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Nontoken creatures that died this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(RiseOfTheDreadMarnWatcher.class)
                .getCreaturesDied();
    }

    @Override
    public RiseOfTheDreadMarnValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of nontoken creatures that died this turn";
    }
}

class RiseOfTheDreadMarnWatcher extends Watcher {

    private int creaturesDied = 0;

    RiseOfTheDreadMarnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()
                && zEvent.getTarget().isCreature(game)
                && !(zEvent.getTarget() instanceof PermanentToken)) {
            creaturesDied += 1;
        }
    }

    @Override
    public void reset() {
        creaturesDied = 0;
        super.reset();
    }

    int getCreaturesDied() {
        return creaturesDied;
    }
}
