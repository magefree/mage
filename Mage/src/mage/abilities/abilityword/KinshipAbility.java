/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.abilityword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.EffectType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class KinshipAbility extends TriggeredAbilityImpl {
    
    public KinshipAbility(Effect kinshipEffect) {
        super(Zone.BATTLEFIELD, new KinshipBaseEffect(kinshipEffect), true);        
    }
    
    public KinshipAbility(final KinshipAbility ability) {
        super(ability);        
    }

    public void addKinshipEffect(Effect kinshipEffect) {
        for (Effect effect: this.getEffects()) {
            if (effect instanceof KinshipBaseEffect) {
               ((KinshipBaseEffect) effect).addEffect(kinshipEffect);
               break;
            }
        }        
    }
    
    @Override
    public KinshipAbility copy() {
        return new KinshipAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return new StringBuilder("<i>Kinship</i> - At the beginning of your upkeep, ").append(super.getRule()).toString();
    }
    
}

class KinshipBaseEffect extends OneShotEffect {
    
    private final Effects kinshipEffects = new Effects();
    
    public KinshipBaseEffect(Effect kinshipEffect) {
        super(kinshipEffect.getOutcome());
        this.kinshipEffects.add(kinshipEffect);
        this.staticText = "you may look at the top card of your library. If it shares a creature type with {this}, you may reveal it. If you do, ";
    }
    
    public KinshipBaseEffect(final KinshipBaseEffect effect) {
        super(effect);
        this.kinshipEffects.addAll(effect.kinshipEffects);
    }
    
    public void addEffect(Effect kinshipEffect) {
        this.kinshipEffects.add(kinshipEffect);
    }
    
    @Override
    public KinshipBaseEffect copy() {
        return new KinshipBaseEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    Cards cards = new CardsImpl(card);
                    controller.lookAtCards(sourcePermanent.getName(), cards, game);
                    if (CardUtil.shareSubtypes(sourcePermanent, card)) {
                        if (controller.chooseUse(outcome,new StringBuilder("Kinship - Reveal ").append(card.getLogName()).append("?").toString(), source, game)) {
                            controller.revealCards(sourcePermanent.getName(), cards, game);
                            for (Effect effect: kinshipEffects) {
                                effect.setTargetPointer(new FixedTarget(card.getId()));
                                if (effect.getEffectType().equals(EffectType.ONESHOT)) {
                                    effect.apply(game, source);
                                } else {
                                    if (effect instanceof ContinuousEffect) {
                                        game.addEffect((ContinuousEffect)effect, source);
                                    } else {
                                        throw new UnsupportedOperationException("This kind of effect is not supported");
                                    }
                                }
                            }                            
                        }
                    }                    
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return new StringBuilder(super.getText(mode)).append(kinshipEffects.getText(mode)).toString(); 
    }
        
}
