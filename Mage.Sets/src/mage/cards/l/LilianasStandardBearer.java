package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianasStandardBearer extends CardImpl {

    public LilianasStandardBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Liliana's Standard Bearer enters the battlefield, draw X cards, where X is the number of creatures that died under your control this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(LilianasStandardBearerCount.instance)
                        .setText("draw X cards, where X is the number of creatures that died under your control this turn")
        ), new LilianasStandardBearerWatcher());
    }

    private LilianasStandardBearer(final LilianasStandardBearer card) {
        super(card);
    }

    @Override
    public LilianasStandardBearer copy() {
        return new LilianasStandardBearer(this);
    }
}

enum LilianasStandardBearerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        LilianasStandardBearerWatcher watcher = game.getState().getWatcher(LilianasStandardBearerWatcher.class);
        return watcher == null ? 0 : watcher.getCount(sourceAbility.getControllerId());
    }

    @Override
    public LilianasStandardBearerCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class LilianasStandardBearerWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    LilianasStandardBearerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && zEvent.getTarget().isCreature(game)) {
            playerMap.compute(zEvent.getTarget().getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    int getCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }
}