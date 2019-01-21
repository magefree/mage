
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class AsmiraHolyAvenger extends CardImpl {

    public AsmiraHolyAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, put a +1/+1 counter on Asmira, Holy Avenger for each creature put into your graveyard from the battlefield this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(),
                        AsmiraHolyAvengerDynamicValue.instance, true
                ),
                TargetController.ANY, false
        ), new AsmiraHolyAvengerWatcher());
    }

    private AsmiraHolyAvenger(final AsmiraHolyAvenger card) {
        super(card);
    }

    @Override
    public AsmiraHolyAvenger copy() {
        return new AsmiraHolyAvenger(this);
    }
}


class AsmiraHolyAvengerWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap();

    AsmiraHolyAvengerWatcher() {
        super(AsmiraHolyAvengerWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    private AsmiraHolyAvengerWatcher(final AsmiraHolyAvengerWatcher watcher) {
        super(watcher);
        this.playerMap.putAll(watcher.playerMap);
    }

    @Override
    public AsmiraHolyAvengerWatcher copy() {
        return new AsmiraHolyAvengerWatcher(this);
    }

    int getCreaturesCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && permanent.isCreature()) {
                playerMap.putIfAbsent(permanent.getOwnerId(), 0);
                playerMap.compute(permanent.getOwnerId(), (key, value) -> value + 1);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }
}

enum AsmiraHolyAvengerDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AsmiraHolyAvengerWatcher watcher = game.getState().getWatcher(AsmiraHolyAvengerWatcher.class);
        if (watcher != null) {
            return watcher.getCreaturesCount(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public AsmiraHolyAvengerDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "creature put into your graveyard from the battlefield this turn";
    }
}