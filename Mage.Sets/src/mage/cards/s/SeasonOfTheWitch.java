
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DeclareAttackerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author L_J
 */
public final class SeasonOfTheWitch extends CardImpl {

    public SeasonOfTheWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        // At the beginning of your upkeep, sacrifice Season of the Witch unless you pay 2 life.
        Cost cost = new PayLifeCost(2);
        cost.setText("2 life");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(cost), TargetController.YOU, false));

        // At the beginning of the end step, destroy all untapped creatures that didn't attack this turn, except for creatures that couldn't attack.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new SeasonOfTheWitchEffect(), TargetController.ANY, false);
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new CouldAttackThisTurnWatcher());
        this.addAbility(ability);
    }

    private SeasonOfTheWitch(final SeasonOfTheWitch card) {
        super(card);
    }

    @Override
    public SeasonOfTheWitch copy() {
        return new SeasonOfTheWitch(this);
    }
}

class SeasonOfTheWitchEffect extends OneShotEffect {

    SeasonOfTheWitchEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all untapped creatures that didn't attack this turn, except for creatures that couldn't attack";
    }

    private SeasonOfTheWitchEffect(final SeasonOfTheWitchEffect effect) {
        super(effect);
    }

    @Override
    public SeasonOfTheWitchEffect copy() {
        return new SeasonOfTheWitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                // Noncreature cards are safe.
                if (!permanent.isCreature(game)) {
                    continue;
                }
                // Tapped cards are safe.
                if (permanent.isTapped()) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
                if (watcher != null
                        && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    continue;
                }
                // Creatures that couldn't attack are safe.
                CouldAttackThisTurnWatcher watcher2 = game.getState().getWatcher(CouldAttackThisTurnWatcher.class);
                if (watcher2 != null
                        && !watcher2.getCouldAttackThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    continue;
                }
                // Destroy the rest.
                permanent.destroy(source, game, false);
            }
            return true;
        }
        return false;
    }
}

class CouldAttackThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> couldAttackThisTurnCreatures = new HashSet<>();

    public CouldAttackThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE) {
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            if(activePlayer == null){
                return;
            }
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(activePlayer.getId())) {
                if (permanent.isCreature(game)) {
                    for (UUID defender : game.getCombat().getDefenders()) {
                        if (!defender.equals(activePlayer.getId())) {
                            if (permanent.canAttack(defender, game)) {
                                // exclude Propaganda style effects
                                if (!game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                                        new DeclareAttackerEvent(defender, permanent.getId(), permanent.getControllerId()), game)) {
                                    this.couldAttackThisTurnCreatures.add(new MageObjectReference(permanent.getId(), game));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Set<MageObjectReference> getCouldAttackThisTurnCreatures() {
        return this.couldAttackThisTurnCreatures;
    }

    @Override
    public void reset() {
        super.reset();
        this.couldAttackThisTurnCreatures.clear();
    }
}
