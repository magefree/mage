/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox (based on Quenchable Fire code by
 * BetaSteward_at_googlemail.com)
 */
public class UnlessPaysDelayedEffect extends OneShotEffect {

    private final Cost cost;
    private final Effect effect;
    private final PhaseStep step;
    private final boolean affectedPlayersTurn;

    public UnlessPaysDelayedEffect(Cost cost, Effect effect, PhaseStep step, boolean affectedPlayersTurn, String text) {
        super(Outcome.Detriment);
        this.cost = cost;
        this.effect = effect;
        this.step = step;
        this.affectedPlayersTurn = affectedPlayersTurn;
        staticText = text + "<br><i>Use the Special button to pay the " + cost.getText()
                + " with a special action before that step.</i>";
    }

    public UnlessPaysDelayedEffect(final UnlessPaysDelayedEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
        this.effect = effect.effect.copy();
        this.step = effect.step;
        this.affectedPlayersTurn = effect.affectedPlayersTurn;
    }

    @Override
    public UnlessPaysDelayedEffect copy() {
        return new UnlessPaysDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {

            //create special action
            UnlessPaysDelayedEffectAction newAction = new UnlessPaysDelayedEffectAction();

            //create delayed triggered ability
            UUID turnPlayer = affectedPlayersTurn ? getTargetPointer().getFirst(game, source) : source.getControllerId();
            effect.setTargetPointer(new FixedTarget(getTargetPointer().getFirst(game, source)));
            UnlessPaysDelayedEffectTriggeredAbility delayedAbility = new UnlessPaysDelayedEffectTriggeredAbility(turnPlayer, step, effect);
            delayedAbility.setSpecialActionId(newAction.getId());
            UUID delayedAbilityId = game.addDelayedTriggeredAbility(delayedAbility, source);

            // update special action
            newAction.addCost(cost);
            Effect removeEffect = new RemoveDelayedTriggeredAbilityEffect(delayedAbilityId);
            newAction.addEffect(removeEffect);
            newAction.addEffect(new RemoveSpecialActionEffect(newAction.getId()));
            newAction.setSourceId(source.getSourceId());
            newAction.setControllerId(getTargetPointer().getFirst(game, source));
            removeEffect.setText(sourceObject.getIdName() + " - Pay " + cost.getText() + " to remove the triggered ability.");
            game.getState().getSpecialActions().add(newAction);
            return true;
        }
        return false;
    }

}

class UnlessPaysDelayedEffectTriggeredAbility extends DelayedTriggeredAbility {

    private UUID specialActionId;
    private final UUID turnPlayer;
    private final PhaseStep step;

    public UnlessPaysDelayedEffectTriggeredAbility(UUID turnPlayer, PhaseStep step, Effect effect) {
        super(effect);
        this.turnPlayer = turnPlayer;
        this.step = step;
    }

    public void setSpecialActionId(UUID specialActionId) {
        this.specialActionId = specialActionId;
    }

    public UnlessPaysDelayedEffectTriggeredAbility(final UnlessPaysDelayedEffectTriggeredAbility ability) {
        super(ability);
        this.turnPlayer = ability.turnPlayer;
        this.step = ability.step;
        this.specialActionId = ability.specialActionId;
    }

    @Override
    public UnlessPaysDelayedEffectTriggeredAbility copy() {
        return new UnlessPaysDelayedEffectTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (step) {
            case UPKEEP:
                return event.getType() == EventType.UPKEEP_STEP_PRE;
            case DRAW:
                return event.getType() == EventType.DRAW_STEP_PRE;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(turnPlayer)) {
            for (SpecialAction action : game.getState().getSpecialActions()) {
                if (action.getId().equals(specialActionId)) {
                    game.getState().getSpecialActions().remove(action);
                    break;
                }
            }
            return true;
        }
        return false;
    }

}

class UnlessPaysDelayedEffectAction extends SpecialAction {

    public UnlessPaysDelayedEffectAction() {
        super();
    }

    public UnlessPaysDelayedEffectAction(final UnlessPaysDelayedEffectAction ability) {
        super(ability);
    }

    @Override
    public UnlessPaysDelayedEffectAction copy() {
        return new UnlessPaysDelayedEffectAction(this);
    }
}
