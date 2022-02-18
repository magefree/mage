package mage.cards.v;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VesselOfTheAllConsuming extends CardImpl {

    public VesselOfTheAllConsuming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Vessel of the All-Consuming deals damage, put a +1/+1 counter on it.
        this.addAbility(new VesselOfTheAllConsumingTriggeredAbility());

        // Whenever Vessel of the All-Consuming deals damage to a player, if it has dealt 10 or more damage to that player this turn, they lose the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DealsDamageToAPlayerTriggeredAbility(
                        new LoseGameTargetPlayerEffect(), false, true
                ), VesselOfTheAllConsumingWatcher::checkPermanent, "Whenever {this} deals damage to a player, " +
                "if it has dealt 10 or more damage to that player this turn, they lose the game."
        ));
    }

    private VesselOfTheAllConsuming(final VesselOfTheAllConsuming card) {
        super(card);
    }

    @Override
    public VesselOfTheAllConsuming copy() {
        return new VesselOfTheAllConsuming(this);
    }

    public static Watcher makeWatcher() {
        return new VesselOfTheAllConsumingWatcher();
    }
}

class VesselOfTheAllConsumingTriggeredAbility extends TriggeredAbilityImpl {

    VesselOfTheAllConsumingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    private VesselOfTheAllConsumingTriggeredAbility(final VesselOfTheAllConsumingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VesselOfTheAllConsumingTriggeredAbility copy() {
        return new VesselOfTheAllConsumingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event instanceof DamagedEvent;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage, put a +1/+1 counter on it.";
    }
}

class VesselOfTheAllConsumingWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();
    private static final Map<UUID, Integer> emptyMap = new HashMap<>();

    VesselOfTheAllConsumingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            morMap.computeIfAbsent(new MageObjectReference(permanent, game), x -> new HashMap<>())
                    .compute(event.getTargetId(), (u, i) -> i == null ? 1 : Integer.sum(i, 1));
        }
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
    }

    static boolean checkPermanent(Game game, Ability source) {
        return game.getState()
                .getWatcher(VesselOfTheAllConsumingWatcher.class)
                .morMap
                .getOrDefault(new MageObjectReference(source), emptyMap)
                .getOrDefault(source.getEffects().get(0).getTargetPointer().getFirst(game, source), 0) >= 10;
    }
}
