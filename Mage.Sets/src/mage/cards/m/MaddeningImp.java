
package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author L_J
 */
public final class MaddeningImp extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creatures");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
        filter.add(TargetController.ACTIVE.getControllerPredicate());
        filter.setMessage("non-Wall creatures the active player controls");
    }

    public MaddeningImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: Non-Wall creatures the active player controls attack this turn if able. At the beginning of the next end step, destroy each of those creatures that didn't attack this turn. Activate this ability only during an opponent's turn and only before combat.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleAllEffect(filter, Duration.EndOfTurn),
                new TapSourceCost(), new MaddeningImpTurnCondition(),
                "{T}: Non-Wall creatures the active player controls attack this turn if able. "
                + "At the beginning of the next end step, destroy each of those creatures that didn't attack this turn. "
                + "Activate only during an opponent's turn and only before combat.");
        ability.addEffect(new MaddeningImpCreateDelayedTriggeredAbilityEffect());
        this.addAbility(ability, new AttackedThisTurnWatcher());

    }

    private MaddeningImp(final MaddeningImp card) {
        super(card);
    }

    @Override
    public MaddeningImp copy() {
        return new MaddeningImp(this);
    }
}

class MaddeningImpTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        return activePlayer != null && activePlayer.hasOpponent(source.getControllerId(), game) && game.getPhase().getStep().getType().getIndex() < 5;
    }

    @Override
    public String toString() {
        return "";
    }
}

class MaddeningImpCreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public MaddeningImpCreateDelayedTriggeredAbilityEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "At the beginning of the next end step, destroy each of those creatures that didn't attack this turn";
    }

    public MaddeningImpCreateDelayedTriggeredAbilityEffect(final MaddeningImpCreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public MaddeningImpCreateDelayedTriggeredAbilityEffect copy() {
        return new MaddeningImpCreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            Set<MageObjectReference> activeCreatures = new HashSet<>();
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                if (creature != null) {
                    activeCreatures.add(new MageObjectReference(creature, game));
                }
            }
            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility
                    = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new MaddeningImpDelayedDestroyEffect(activeCreatures), TargetController.ANY, new InvertCondition(TargetAttackedThisTurnCondition.instance));
            delayedAbility.getDuration();
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class MaddeningImpDelayedDestroyEffect extends OneShotEffect {

    private Set<MageObjectReference> activeCreatures;

    MaddeningImpDelayedDestroyEffect(Set<MageObjectReference> activeCreatures) {
        super(Outcome.DestroyPermanent);
        this.activeCreatures = activeCreatures;
        this.staticText = "At the beginning of the next end step, destroy each of those creatures that didn't attack this turn";
    }

    MaddeningImpDelayedDestroyEffect(final MaddeningImpDelayedDestroyEffect effect) {
        super(effect);
        this.activeCreatures = effect.activeCreatures;
    }

    @Override
    public MaddeningImpDelayedDestroyEffect copy() {
        return new MaddeningImpDelayedDestroyEffect(this);
    }

    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
                
                MageObjectReference mor = new MageObjectReference(permanent, game);
                // Only affect permanents present when the ability resolved
                if (!activeCreatures.contains(mor)) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
                if (watcher != null && watcher.getAttackedThisTurnCreatures().contains(mor)) {
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
