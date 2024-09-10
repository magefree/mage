package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RielleTheEverwise extends CardImpl {

    private static final DynamicValue xValue
            = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);

    public RielleTheEverwise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Rielle, the Everwise gets +1/+0 for each instant and sorcery card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("{this} gets +1/+0 for each instant and sorcery card in your graveyard")));

        // Whenever you discard one or more cards for the first time each turn, draw that many cards.
        this.addAbility(new RielleTheEverwiseTriggeredAbility());
    }

    private RielleTheEverwise(final RielleTheEverwise card) {
        super(card);
    }

    @Override
    public RielleTheEverwise copy() {
        return new RielleTheEverwise(this);
    }
}

class RielleTheEverwiseTriggeredAbility extends TriggeredAbilityImpl {

    RielleTheEverwiseTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addWatcher(new RielleTheEverwiseWatcher());
    }

    private RielleTheEverwiseTriggeredAbility(final RielleTheEverwiseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        RielleTheEverwiseWatcher watcher = game.getState().getWatcher(RielleTheEverwiseWatcher.class);
        if (watcher == null
                || !watcher.checkDiscarded(event.getPlayerId())
                || !event.getPlayerId().equals(getControllerId())
                || event.getAmount() == 0) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new DrawCardSourceControllerEffect(event.getAmount()));
        return true;
    }

    @Override
    public RielleTheEverwiseTriggeredAbility copy() {
        return new RielleTheEverwiseTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you discard one or more cards for the first time each turn, draw that many cards.";
    }
}

class RielleTheEverwiseWatcher extends Watcher {

    private final Map<UUID, Integer> discardedThisTurn = new HashMap<>();

    RielleTheEverwiseWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARDS
                && event.getAmount() > 0) {
            discardedThisTurn.merge(event.getPlayerId(), 1, Integer::sum);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.discardedThisTurn.clear();
    }

    boolean checkDiscarded(UUID playerId) {
        return discardedThisTurn.getOrDefault(playerId, 0) < 2;
    }
}
