package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.watchers.common.LandfallWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public final class SearingBlaze extends CardImpl {

    public SearingBlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Searing Blaze deals 1 damage to target player and 1 damage to target creature that player controls.
        // Landfall - If you had a land enter the battlefield under your control this turn, Searing Blaze deals 3 damage to that player and 3 damage to that creature instead.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addTarget(new SearingBlazeTarget());
        this.getSpellAbility().addEffect(new SearingBlazeEffect());
        this.getSpellAbility().addWatcher(new LandfallWatcher());
    }

    private SearingBlaze(final SearingBlaze card) {
        super(card);
    }

    @Override
    public SearingBlaze copy() {
        return new SearingBlaze(this);
    }

}

class SearingBlazeEffect extends OneShotEffect {

    public SearingBlazeEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 1 damage to target player or planeswalker and 1 damage to target creature " +
                "that player or that planeswalker's controller controls.<br>" + AbilityWord.LANDFALL.formatWord() +
                "If you had a land enter the battlefield under your control this turn, {this} deals 3 damage " +
                "to that player or planeswalker and 3 damage to that creature instead.";
    }

    public SearingBlazeEffect(final SearingBlazeEffect effect) {
        super(effect);
    }

    @Override
    public SearingBlazeEffect copy() {
        return new SearingBlazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        LandfallWatcher watcher = game.getState().getWatcher(LandfallWatcher.class);
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        int damage = 1;
        if (watcher != null && watcher.landPlayed(source.getControllerId())) {
            damage = 3;
        }
        game.damagePlayerOrPlaneswalker(source.getTargets().get(0).getFirstTarget(), damage, source.getSourceId(), source, game, false, true);
        if (creature != null) {
            creature.damage(damage, source.getSourceId(), source, game, false, true);
        }
        return true;
    }

}

class SearingBlazeTarget extends TargetPermanent {

    public SearingBlazeTarget() {
        super(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false);
    }

    public SearingBlazeTarget(final SearingBlazeTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        UUID firstTarget = player.getId();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.isControlledBy(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(source);
        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            Player player = game.getPlayerOrPlaneswalkerController(playerId);
            if (player != null) {
                for (UUID targetId : availablePossibleTargets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.isControlledBy(player.getId())) {
                        possibleTargets.add(targetId);
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public SearingBlazeTarget copy() {
        return new SearingBlazeTarget(this);
    }
}
