package mage.cards.v;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
        this.addAbility(new DealsDamageSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // Whenever Vessel of the All-Consuming deals damage to a player, if it has dealt 10 or more damage to that player this turn, they lose the game.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(
                new LoseGameTargetPlayerEffect().setText("they lose the game"), false, true
        ).withInterveningIf(VesselOfTheAllConsumingCondition.instance));
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

enum VesselOfTheAllConsumingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return VesselOfTheAllConsumingWatcher.checkPermanent(game, source);
    }

    @Override
    public String toString() {
        return "it has dealt 10 or more damage to that player this turn";
    }
}

class VesselOfTheAllConsumingWatcher extends Watcher {

    private final Map<Entry<MageObjectReference, UUID>, Integer> morMap = new HashMap<>();

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
            int damage = event.getAmount();
            morMap.compute(new AbstractMap.SimpleImmutableEntry(new MageObjectReference(permanent, game), event.getTargetId()),
                    (u, i) -> i == null ? damage : Integer.sum(i, damage));
        }
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
    }

    static boolean checkPermanent(Game game, Ability source) {
        Map<Entry<MageObjectReference, UUID>, Integer> morMap = game.getState()
                .getWatcher(VesselOfTheAllConsumingWatcher.class)
                .morMap;
        Entry<MageObjectReference, UUID> key = new AbstractMap.SimpleImmutableEntry(
                new MageObjectReference(game.getPermanent(source.getSourceId()), game),
                source.getEffects().get(0).getTargetPointer().getFirst(game, source));
        return morMap.getOrDefault(key, 0) >= 10;
    }
}
