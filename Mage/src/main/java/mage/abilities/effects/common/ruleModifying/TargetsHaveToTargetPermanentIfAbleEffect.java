/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.ruleModifying;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;

/**
 * 6/8/2016 If a spell or ability’s targets are changed, or if a copy of a spell
 * or ability is put onto the stack and has new targets chosen, it doesn’t have
 * to target a Flagbearer.
 *
 * @author LevelX2
 */
public class TargetsHaveToTargetPermanentIfAbleEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterPermanent filter;

    public TargetsHaveToTargetPermanentIfAbleEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.filter = filter;
        staticText = "While choosing targets as part of casting a spell or activating an ability, your opponents must choose at least " + this.filter.getMessage() + " on the battlefield if able";

    }

    public TargetsHaveToTargetPermanentIfAbleEffect(final TargetsHaveToTargetPermanentIfAbleEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public TargetsHaveToTargetPermanentIfAbleEffect copy() {
        return new TargetsHaveToTargetPermanentIfAbleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETS_VALID;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You must choose at least " + this.filter.getMessage() + " on the battlefield as target if able (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetingPlayer = game.getPlayer(event.getPlayerId());
        if (controller != null
                && targetingPlayer.isHuman() // TODO: This target handling does only work for non AI players so AI logic
                && controller.hasOpponent(event.getPlayerId(), game)) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject.isCopy()) {
                return false;
            }
            Ability ability = (Ability) getValue("targetAbility");
            if (ability != null) {
                // Get all the allowed permanents on the battlefield in range of the abilities controller
                List<Permanent> allowedPermanents = game.getBattlefield().getActivePermanents(filter, event.getPlayerId(), event.getSourceId(), game);
                if (!allowedPermanents.isEmpty()) {
                    boolean canTargetAllowedPermanent = false;
                    for (UUID modeId : ability.getModes().getSelectedModes()) {
                        ability.getModes().setActiveMode(modeId);
                        for (Target target : ability.getTargets()) {
                            // Check if already targeted
                            for (Permanent allowedPermanent : allowedPermanents) {
                                if (target.getTargets().contains(allowedPermanent.getId())) {
                                    return false;
                                }
                                if (target.canTarget(allowedPermanent.getId(), source, game)) {
                                    canTargetAllowedPermanent = true;
                                }
                            }
                        }
                    }
                    return canTargetAllowedPermanent;
                }
            }
        }
        return false;

    }
}
