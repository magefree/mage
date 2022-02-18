package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Mutiny extends CardImpl {

    public Mutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Target creature an opponent controls deals damage equal to its power to another target creature that player controls.
        this.getSpellAbility().addEffect(new MutinyEffect());
        this.getSpellAbility().addTarget(new MutinyFirstTarget(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("another target creature that player controls")));

    }

    private Mutiny(final Mutiny card) {
        super(card);
    }

    @Override
    public Mutiny copy() {
        return new Mutiny(this);
    }
}

class MutinyEffect extends OneShotEffect {

    public MutinyEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature an opponent controls deals damage equal to its power to another target creature that player controls";
    }

    public MutinyEffect(final MutinyEffect effect) {
        super(effect);
    }

    @Override
    public MutinyEffect copy() {
        return new MutinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent firstTarget = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (firstTarget != null) {
            int damage = firstTarget.getPower().getValue();
            Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (damage > 0 && secondTarget != null) {
                secondTarget.damage(damage, firstTarget.getId(), source, game);
            }
        }
        return true;
    }

}

class MutinyFirstTarget extends TargetCreaturePermanent {

    public MutinyFirstTarget(FilterCreaturePermanent filter) {
        super(1, 1, filter, false);
    }

    public MutinyFirstTarget(final MutinyFirstTarget target) {
        super(target);
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game, boolean skipEvent) {
        super.addTarget(id, source, game, skipEvent);
        // Update the second target
        UUID firstController = game.getControllerId(id);
        if (firstController != null && source.getTargets().size() > 1) {
            Player controllingPlayer = game.getPlayer(firstController);
            TargetCreaturePermanent targetCreaturePermanent = (TargetCreaturePermanent) source.getTargets().get(1);
            // Set a new filter to the second target with the needed restrictions
            FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature that player " + controllingPlayer.getName() + " controls");
            filter.add(new ControllerIdPredicate(firstController));
            filter.add(Predicates.not(new PermanentIdPredicate(id)));
            targetCreaturePermanent.replaceFilter(filter);
        }
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            // can only target, if the controller has at least two targetable creatures
            UUID controllingPlayerId = game.getControllerId(id);
            int possibleTargets = 0;
            MageObject sourceObject = game.getObject(source.getId());
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllingPlayerId, game)) {
                if (permanent.canBeTargetedBy(sourceObject, controllerId, game)) {
                    possibleTargets++;
                }
            }
            return possibleTargets > 1;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        if (super.canChoose(sourceId, sourceControllerId, game)) {
            UUID controllingPlayerId = game.getControllerId(sourceId);
            for (UUID playerId : game.getOpponents(controllingPlayerId)) {
                int possibleTargets = 0;
                MageObject sourceObject = game.getObject(sourceId);
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                    if (permanent.canBeTargetedBy(sourceObject, controllingPlayerId, game)) {
                        possibleTargets++;
                    }
                }
                if (possibleTargets > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MutinyFirstTarget copy() {
        return new MutinyFirstTarget(this);
    }
}
