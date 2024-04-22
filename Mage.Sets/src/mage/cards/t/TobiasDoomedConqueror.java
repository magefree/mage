package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.ZombieToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TobiasDoomedConqueror extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Nontoken creatures that died under your control", TobiasDoomedConquerorValue.instance
    );

    public TobiasDoomedConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Tobias, Doomed Conqueror dies, create a 2/2 black Zombie creature token for each nontoken creature that died under your control this turn.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(
                new ZombieToken(), TobiasDoomedConquerorValue.instance
        )).addHint(hint), new TobiasDoomedConquerorWatcher());
    }

    private TobiasDoomedConqueror(final TobiasDoomedConqueror card) {
        super(card);
    }

    @Override
    public TobiasDoomedConqueror copy() {
        return new TobiasDoomedConqueror(this);
    }
}

enum TobiasDoomedConquerorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return TobiasDoomedConquerorWatcher.getCount(sourceAbility, game);
    }

    @Override
    public TobiasDoomedConquerorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "nontoken creature that died under your control this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class TobiasDoomedConquerorWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    TobiasDoomedConquerorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()
                || zEvent.getTarget() == null
                || !zEvent.getTarget().isCreature(game)
                || zEvent.getTarget() instanceof PermanentToken) {
            return;
        }
        playerMap.compute(zEvent.getTarget().getControllerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    static int getCount(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(TobiasDoomedConquerorWatcher.class)
                .playerMap
                .getOrDefault(source.getControllerId(), 0);
    }
}
