package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class StubbornBurrowfiend extends CardImpl {

    private static final CardsInControllerGraveyardCount xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final ValueHint hint = new ValueHint("Creature cards in your graveyard", xValue);

    public StubbornBurrowfiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BADGER);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Stubborn Burrowfiend becomes saddled for the first time each turn, mill two cards, then Stubborn Burrowfiend gets +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        Ability ability = new StubbornBurrowfiendTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
                .setText(", then {this} gets +X/+X until end of turn, where X is the number of creature cards in your graveyard"));
        ability.addHint(hint);
        this.addAbility(ability, new StubbornBurrowFiendWatcher());

        // Saddle 2
        this.addAbility(new SaddleAbility(2));

    }

    private StubbornBurrowfiend(final StubbornBurrowfiend card) {
        super(card);
    }

    @Override
    public StubbornBurrowfiend copy() {
        return new StubbornBurrowfiend(this);
    }
}

class StubbornBurrowFiendWatcher extends Watcher {

    // MOR -> number of times saddled this turn.
    // Since Watcher are updated before triggers, we want to find 1 for the trigger.
    private final Map<MageObjectReference, Integer> saddledCount = new HashMap<>();

    StubbornBurrowFiendWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.MOUNT_SADDLED) {
            return;
        }
        saddledCount.compute(new MageObjectReference(event.getTargetId(), game), CardUtil::setOrIncrementValue);
    }

    public int timesSaddledThisTurn(MageObjectReference mor) {
        return saddledCount.getOrDefault(mor, 0);
    }

    @Override
    public void reset() {
        super.reset();
        saddledCount.clear();
    }
}

class StubbornBurrowfiendTriggeredAbility extends TriggeredAbilityImpl {

    StubbornBurrowfiendTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever {this} becomes saddled for the first time each turn, ");
    }

    private StubbornBurrowfiendTriggeredAbility(final StubbornBurrowfiendTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StubbornBurrowfiendTriggeredAbility copy() {
        return new StubbornBurrowfiendTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MOUNT_SADDLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StubbornBurrowFiendWatcher watcher = game.getState().getWatcher(StubbornBurrowFiendWatcher.class);
        return watcher != null
                && event.getSourceId().equals(this.getSourceId())
                && 1 == watcher.timesSaddledThisTurn(new MageObjectReference(event.getTargetId(), game));
    }
}
