package mage.cards.h;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author DarioBarrabinoDev
 */
public final class HerculesOlympianHero extends CardImpl {

    public HerculesOlympianHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Hercules attacks, put a +1/+1 counter on him. He gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on him"));
        ability.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
                .setText("He gains indestructible until end of turn"));
        this.addAbility(ability);

        // Whenever Hercules is dealt damage for the first time each turn, put that many +1/+1 counters on him.
        this.addAbility(new HerculesOlympianHeroTriggeredAbility());
    }

    private HerculesOlympianHero(final HerculesOlympianHero card) {
        super(card);
    }

    @Override
    public HerculesOlympianHero copy() {
        return new HerculesOlympianHero(this);
    }
}

class HerculesOlympianHeroTriggeredAbility extends DealtDamageToSourceTriggeredAbility {

    HerculesOlympianHeroTriggeredAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), SavedDamageValue.MANY)
                .setText("put that many +1/+1 counters on him"), false);
        setTriggerPhrase("Whenever {this} is dealt damage for the first time each turn, ");
        addWatcher(new HerculesOlympianHeroWatcher());
    }

    private HerculesOlympianHeroTriggeredAbility(final HerculesOlympianHeroTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HerculesOlympianHeroTriggeredAbility copy() {
        return new HerculesOlympianHeroTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        HerculesOlympianHeroWatcher watcher = game.getState().getWatcher(HerculesOlympianHeroWatcher.class);
        return watcher != null
                && watcher.checkEvent(getSourceId(), event, game)
                && super.checkTrigger(event, game);
    }
}

class HerculesOlympianHeroWatcher extends Watcher {

    // which permanent was dealt damage by which event first this turn
    private final Map<MageObjectReference, UUID> damagedMap = new HashMap<>();

    HerculesOlympianHeroWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT
                || event.getAmount() < 1) {
            return;
        }
        damagedMap.putIfAbsent(new MageObjectReference(event.getTargetId(), game), event.getId());
    }

    @Override
    public void reset() {
        super.reset();
        damagedMap.clear();
    }

    boolean checkEvent(UUID permanentId, GameEvent event, Game game) {
        return event.getId().equals(damagedMap.getOrDefault(new MageObjectReference(permanentId, game), null));
    }
}
