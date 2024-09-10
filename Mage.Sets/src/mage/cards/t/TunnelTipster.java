package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 *
 * @author DominionSpy
 */
public final class TunnelTipster extends CardImpl {

    public TunnelTipster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if a face-down creature entered the battlefield under your control this turn, put a +1/+1 counter on Tunnel Tipster.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU,
                TunnelTipsterCondition.instance, false);
        ability.addHint(new ConditionHint(TunnelTipsterCondition.instance,
                "a face-down creature entered the battlefield under your control"));
        this.addAbility(ability, new TunnelTipsterWatcher());
        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private TunnelTipster(final TunnelTipster card) {
        super(card);
    }

    @Override
    public TunnelTipster copy() {
        return new TunnelTipster(this);
    }
}

class TunnelTipsterWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    TunnelTipsterWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD &&
                    zEvent.getTarget().isCreature(game) &&
                    zEvent.getTarget().isFaceDown(game)) {
                players.add(zEvent.getTarget().getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public static boolean faceDownCreatureEnteredForPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(TunnelTipsterWatcher.class)
                .players
                .contains(playerId);
    }
}

enum TunnelTipsterCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return TunnelTipsterWatcher.faceDownCreatureEnteredForPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a face-down creature entered the battlefield under your control this turn";
    }
}
