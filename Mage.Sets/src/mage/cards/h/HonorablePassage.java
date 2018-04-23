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
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author L_J
 */
public class HonorablePassage extends CardImpl {

    public HonorablePassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // The next time a source of your choice would deal damage to any target this turn, prevent that damage. If damage from a red source is prevented this way, Honorable Passage deals that much damage to the source's controller.
        this.getSpellAbility().addEffect(new HonorablePassageEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public HonorablePassage(final HonorablePassage card) {
        super(card);
    }

    @Override
    public HonorablePassage copy() {
        return new HonorablePassage(this);
    }
}

class HonorablePassageEffect extends PreventNextDamageFromChosenSourceToTargetEffect {
    
    public HonorablePassageEffect() {
        super(Duration.EndOfTurn);
    }
    
    public HonorablePassageEffect(final HonorablePassageEffect effect) {
        super(effect);
    }
    
    @Override
    public HonorablePassageEffect copy() {
        return new HonorablePassageEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        PreventionEffectData preventEffectData = preventDamageAction(event, source, game);
        if (preventEffectData.getPreventedDamage() > 0) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.getColor(game).isRed()) {
                UUID sourceControllerId = game.getControllerId(event.getSourceId());
                if (sourceControllerId != null) {
                    Player sourceController = game.getPlayer(sourceControllerId);
                    if (sourceController != null) {
                        sourceController.damage(damage, source.getSourceId(), game, false, true);
                    }
                }
            }
            this.used = true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "The next time a source of your choice would deal damage to any target this turn, prevent that damage. If damage from a red source is prevented this way, {this} deals that much damage to the source's controller";
    }
}
