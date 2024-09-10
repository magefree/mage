package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class MotivatedPony extends CardImpl {

    public MotivatedPony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Motivated Pony attacks, attacking creatures get +1/+1 until end of turn. If a Food entered the battlefield under your control this turn, untap those creatures and they get an additional +2/+2 until end of turn.
        TriggeredAbility trigger = new AttacksTriggeredAbility(new MotivatedPonyEffect());
        trigger.addWatcher(new MotivatedPonyWatcher());
        this.addAbility(trigger);
    }

    private MotivatedPony(final MotivatedPony card) {
        super(card);
    }

    @Override
    public MotivatedPony copy() {
        return new MotivatedPony(this);
    }
}

class MotivatedPonyWatcher extends Watcher {

    // players that had a Food enter this turn.
    private final Set<UUID> players = new HashSet<>();

    MotivatedPonyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && ((EntersTheBattlefieldEvent) event).getTarget().getSubtype(game).contains(SubType.FOOD)) {
            players.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public boolean checkPlayer(UUID playerId) {
        return players.contains(playerId);
    }

}

class MotivatedPonyEffect extends OneShotEffect {

    MotivatedPonyEffect() {
        super(Outcome.BoostCreature);
        staticText = "attacking creatures get +1/+1 until end of turn. If a Food entered the battlefield under "
                + "your control this turn, untap those creatures and they get an additional +2/+2 until end of turn.";
    }

    private MotivatedPonyEffect(final MotivatedPonyEffect ability) {
        super(ability);
    }

    @Override
    public MotivatedPonyEffect copy() {
        return new MotivatedPonyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MotivatedPonyWatcher watcher = game.getState().getWatcher(MotivatedPonyWatcher.class);
        if (controller == null || watcher == null) {
            return false;
        }

        boolean additionalEffect = watcher.checkPlayer(controller.getId());
        int valueBoost = additionalEffect ? 1 + 2 : 1;

        game.addEffect(new BoostAllEffect(
                valueBoost,
                valueBoost,
                Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES,
                false
        ), source);

        if (additionalEffect) {
            new UntapAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES).apply(game, source);
        }

        return true;
    }

}