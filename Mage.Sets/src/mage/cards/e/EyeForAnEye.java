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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetSource;

/**
 * 
 * @author L_J
 */
public class EyeForAnEye extends CardImpl {

    public EyeForAnEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{W}");

        // The next time a source of your choice would deal damage to you this turn, instead that source deals that much damage to you and Eye for an Eye deals that much damage to that source's controller.
        this.getSpellAbility().addEffect(new EyeForAnEyeEffect());
    }

    public EyeForAnEye(final EyeForAnEye card) {
        super(card);
    }

    @Override
    public EyeForAnEye copy() {
        return new EyeForAnEye(this);
    }
}

class EyeForAnEyeEffect extends ReplacementEffectImpl {

    private final TargetSource damageSource;

    public EyeForAnEyeEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "The next time a source of your choice would deal damage to you this turn, instead that source deals that much damage to you and {this} deals that much damage to that source's controller";
        this.damageSource = new TargetSource();
    }

    public EyeForAnEyeEffect(final EyeForAnEyeEffect effect) {
        super(effect);
        this.damageSource = effect.damageSource.copy();
    }

    @Override
    public EyeForAnEyeEffect copy() {
        return new EyeForAnEyeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.damageSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
        super.init(source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            controller.damage(damageEvent.getAmount(), damageEvent.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
            UUID sourceControllerId = game.getControllerId(damageEvent.getSourceId());
            if (sourceControllerId != null) {
                Player sourceController = game.getPlayer(sourceControllerId);
                if (sourceController != null) {
                    sourceController.damage(damageEvent.getAmount(), source.getSourceId(), game, false, true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            if (controller.getId() == damageEvent.getTargetId() && damageEvent.getSourceId().equals(damageSource.getFirstTarget())) {
                this.discard();
                return true;
            }
        }
        return false;
    }
}
