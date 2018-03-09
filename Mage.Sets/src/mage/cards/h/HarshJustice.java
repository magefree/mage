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
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 *
 * @author LevelX2 & L_J
 */
public class HarshJustice extends CardImpl {

    public HarshJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Cast Harsh Justice only during the declare attackers step and only if you've been attacked this step.
        Ability ability = new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, PhaseStep.DECLARE_ATTACKERS, AttackedThisStepCondition.instance,
                "Cast {this} only during the declare attackers step and only if you've been attacked this step."
        );
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);

        // This turn, whenever an attacking creature deals combat damage to you, it deals that much damage to its controller.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new HarshJusticeTriggeredAbility()));
    }

    public HarshJustice(final HarshJustice card) {
        super(card);
    }

    @Override
    public HarshJustice copy() {
        return new HarshJustice(this);
    }
}

class HarshJusticeTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature");

    static {
        filter.add(new AttackingPredicate());
    }

    public HarshJusticeTriggeredAbility() {
        super(new HarshJusticeEffect(), Duration.EndOfTurn, false);
    }

    public HarshJusticeTriggeredAbility(final HarshJusticeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HarshJusticeTriggeredAbility copy() {
        return new HarshJusticeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent damagePermanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
        if (controller != null && damagePermanent != null) {
            if (damageEvent.isCombatDamage() && controller.getId().equals(damageEvent.getTargetId()) && filter.match(damagePermanent, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", damageEvent.getAmount());
                    effect.setValue("sourceId", damageEvent.getSourceId());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "This turn, whenever an attacking creature deals combat damage to you, " + super.getRule();
    }
}

class HarshJusticeEffect extends OneShotEffect {

    public HarshJusticeEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to its controller";
    }

    public HarshJusticeEffect(final HarshJusticeEffect effect) {
        super(effect);
    }

    @Override
    public HarshJusticeEffect copy() {
        return new HarshJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (Integer) this.getValue("damage");
        UUID sourceId = (UUID) this.getValue("sourceId");
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && damage > 0 && sourceId != null) {
            Permanent targetObject = game.getPermanentOrLKIBattlefield(sourceId);
            if (targetObject != null) {
                Player controller = game.getPlayer(targetObject.getControllerId());
                if (controller != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + targetObject.getLogName() + " deals " + damage + " damage to " + controller.getLogName());
                    controller.damage(damage, sourceId, game, false, true);
                    return true;
                }
            }
        }
        return false;
    }
}
