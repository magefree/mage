
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.BearToken;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class CallerOfTheClaw extends CardImpl {

    public CallerOfTheClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Caller of the Claw enters the battlefield, create a 2/2 green Bear creature token for each nontoken creature put into your graveyard from the battlefield this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new BearToken(), CallerOfTheClawDynamicValue.instance)
        ), new CallerOfTheClawWatcher());
    }

    private CallerOfTheClaw(final CallerOfTheClaw card) {
        super(card);
    }

    @Override
    public CallerOfTheClaw copy() {
        return new CallerOfTheClaw(this);
    }
}

class CallerOfTheClawWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap();

    CallerOfTheClawWatcher() {
        super(CallerOfTheClawWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    private CallerOfTheClawWatcher(final CallerOfTheClawWatcher watcher) {
        super(watcher);
        this.playerMap.putAll(watcher.playerMap);
    }

    @Override
    public CallerOfTheClawWatcher copy() {
        return new CallerOfTheClawWatcher(this);
    }

    int getCreaturesCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null || !permanent.isCreature() || permanent instanceof PermanentToken) {
                return;
            }
            playerMap.putIfAbsent(permanent.getOwnerId(), 0);
            playerMap.compute(permanent.getOwnerId(), (key, value) -> value + 1);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }
}

enum CallerOfTheClawDynamicValue implements DynamicValue {
    instance;

    @Override
    public CallerOfTheClawDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "nontoken creature put into your graveyard from the battlefield this turn";
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CallerOfTheClawWatcher watcher = game.getState().getWatcher(CallerOfTheClawWatcher.class);
        if (watcher != null) {
            return watcher.getCreaturesCount(sourceAbility.getControllerId());
        }
        return 0;
    }
}
