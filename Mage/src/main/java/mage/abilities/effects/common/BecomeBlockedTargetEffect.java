package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class BecomeBlockedTargetEffect extends OneShotEffect {

    public BecomeBlockedTargetEffect() {
        super(Outcome.Benefit);
    }

    private BecomeBlockedTargetEffect(final BecomeBlockedTargetEffect effect) {
        super(effect);
    }

    @Override
    public BecomeBlockedTargetEffect copy() {
        return new BecomeBlockedTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<MageObjectReference> morSet = new HashSet<>();
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            CombatGroup combatGroup = game.getCombat().findGroup(permanent.getId());
            if (combatGroup == null) {
                continue;
            }
            boolean alreadyBlocked = combatGroup.getBlocked();
            combatGroup.setBlocked(true, game);
            if (alreadyBlocked) {
                continue;
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, permanent.getId(), source, null));
            morSet.add(new MageObjectReference(permanent, game));
        }
        String key = UUID.randomUUID().toString();
        game.getState().setValue("becameBlocked_" + key, morSet);
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.BATCH_BLOCK_NONCOMBAT,
                source.getSourceId(), source,
                source.getControllerId(), key, 0)
        );
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().isEmpty()) {
            return "that creature becomes blocked";
        }
        Target target = mode.getTargets().get(0);
        if (target.getNumberOfTargets() == 1) {
            String targetName = target.getTargetName();
            sb.append("target ").append(targetName).append(" becomes blocked");
            return sb.toString();
        }
        if (target.getMaxNumberOfTargets() != target.getMinNumberOfTargets()) {
            sb.append("up to ");
        }
        sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
        sb.append(" target ").append(target.getTargetName()).append(" become blocked");
        return sb.toString();
    }
}
