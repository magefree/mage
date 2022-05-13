package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViviensStampede extends CardImpl {

    public ViviensStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Each creature you control gains vigilance, trample, and melee until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("each creature you control gains vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", trample"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new MeleeAbility(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", and melee until end of turn"));

        // At the beginning of the next main phase this turn, draw a card for each player who was dealt combat damage this turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfMainPhaseDelayedTriggeredAbility(
                        new DrawCardSourceControllerEffect(ViviensStampedeValue.instance),
                        false, TargetController.ANY,
                        AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN_THIS_TURN
                )
        ).concatBy("<br>"));
        this.getSpellAbility().addWatcher(new ViviensStampedeWatcher());
    }

    private ViviensStampede(final ViviensStampede card) {
        super(card);
    }

    @Override
    public ViviensStampede copy() {
        return new ViviensStampede(this);
    }
}

enum ViviensStampedeValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ViviensStampedeWatcher.getCount(game);
    }

    @Override
    public ViviensStampedeValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "player who was dealt combat damage this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class ViviensStampedeWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    ViviensStampedeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && ((DamagedEvent) event).isCombatDamage()) {
            players.add(event.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    static int getCount(Game game) {
        return game.getState().getWatcher(ViviensStampedeWatcher.class).players.size();
    }
}
