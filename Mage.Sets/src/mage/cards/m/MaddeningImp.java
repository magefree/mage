package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class MaddeningImp extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creatures the active player controls");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public MaddeningImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: Non-Wall creatures the active player controls attack this turn if able. At the beginning of the next end step, destroy each of those creatures that didn't attack this turn. Activate this ability only during an opponent's turn and only before combat.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new AttacksIfAbleAllEffect(filter, Duration.EndOfTurn),
                new TapSourceCost(), MaddeningImpTurnCondition.instance
        );
        ability.addEffect(new MaddeningImpCreateDelayedTriggeredAbilityEffect());
        this.addAbility(ability);
    }

    private MaddeningImp(final MaddeningImp card) {
        super(card);
    }

    @Override
    public MaddeningImp copy() {
        return new MaddeningImp(this);
    }
}

enum MaddeningImpTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return OpponentsTurnCondition.instance.apply(game, source) && !game.getTurn().isDeclareAttackersStepStarted();
    }

    @Override
    public String toString() {
        return "during an opponent's turn and only before combat";
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

    private MaddeningImpCreateDelayedTriggeredAbilityEffect(final MaddeningImpCreateDelayedTriggeredAbilityEffect effect) {
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

    private MaddeningImpDelayedDestroyEffect(final MaddeningImpDelayedDestroyEffect effect) {
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
