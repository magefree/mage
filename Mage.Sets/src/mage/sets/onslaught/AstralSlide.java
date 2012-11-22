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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class AstralSlide extends CardImpl<AstralSlide> {

    public AstralSlide(UUID ownerId) {
        super(ownerId, 4, "Astral Slide", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "ONS";

        this.color.setWhite(true);

        // Whenever a player cycles a card, you may exile target creature. If you do, return that card to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new AstralSlideTriggeredAbility());
    }

    public AstralSlide(final AstralSlide card) {
        super(card);
    }

    @Override
    public AstralSlide copy() {
        return new AstralSlide(this);
    }
}


class AstralSlideTriggeredAbility extends TriggeredAbilityImpl<AstralSlideTriggeredAbility> {

    public AstralSlideTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new AstralSlideEffect(), true);
        this.addTarget(new TargetCreaturePermanent());
    }

    public AstralSlideTriggeredAbility(final AstralSlideTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            MageObject object = game.getObject(event.getSourceId());
            if(object != null && object instanceof StackAbility && ((StackAbility)object).getStackAbility() instanceof CyclingAbility){
                return true;    
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player cycles a card, you may exile target creature. If you do, return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    @Override
    public AstralSlideTriggeredAbility copy() {
        return new AstralSlideTriggeredAbility(this);
    }


}

class AstralSlideEffect extends OneShotEffect<AstralSlideEffect> {

    public AstralSlideEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public AstralSlideEffect(final AstralSlideEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Astral Slide Exile", source.getId(), game)) {
                //create delayed triggered ability
                AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD, false));
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                game.addDelayedTriggeredAbility(delayedAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public AstralSlideEffect copy() {
        return new AstralSlideEffect(this);
    }
}