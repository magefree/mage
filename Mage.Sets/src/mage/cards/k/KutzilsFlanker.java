package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPlayer;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class KutzilsFlanker extends CardImpl {

    public KutzilsFlanker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Kutzil's Flanker enters the battlefield, choose one --
        // * Put a +1/+1 counter on Kutzil's Flanker for each creature that left the battlefield under your control this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), KutzilsFlankerValue.instance, true)
                        .setText("put a +1/+1 counter on {this} for each creature that left the battlefield under your control this turn")
        );
        ability.addHint(new ValueHint("number of creatures that left", KutzilsFlankerValue.instance));
        ability.addWatcher(new KutzilsFlankerWatcher());

        // * You gain 2 life and scry 2.
        ability.addMode(new Mode(
                new GainLifeEffect(2)
        ).addEffect(
                new ScryEffect(2, false)
                        .concatBy("and")
        ));

        // * Exile target player's graveyard.
        ability.addMode(new Mode(
                new ExileGraveyardAllTargetPlayerEffect()
        ).addTarget(new TargetPlayer()));
        this.addAbility(ability);
    }

    private KutzilsFlanker(final KutzilsFlanker card) {
        super(card);
    }

    @Override
    public KutzilsFlanker copy() {
        return new KutzilsFlanker(this);
    }
}

enum KutzilsFlankerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return KutzilsFlankerWatcher.getNumberCreatureLeft(sourceAbility.getControllerId(), game);
    }

    @Override
    public KutzilsFlankerValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}


class KutzilsFlankerWatcher extends Watcher {

    // player -> number of creatures that left the battlefield under that player's control this turn
    private final Map<UUID, Integer> mapCreaturesLeft = new HashMap<>();

    KutzilsFlankerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() != Zone.BATTLEFIELD || !zEvent.getTarget().isCreature(game)) {
            return;
        }
        mapCreaturesLeft.compute(zEvent.getTarget().getControllerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        super.reset();
        mapCreaturesLeft.clear();
    }

    public static int getNumberCreatureLeft(UUID playerId, Game game) {
        KutzilsFlankerWatcher watcher = game.getState().getWatcher(KutzilsFlankerWatcher.class);
        return watcher == null ? 0 : watcher.mapCreaturesLeft.getOrDefault(playerId, 0);
    }
}