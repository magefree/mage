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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author North
 */
public class FarsightMask extends CardImpl<FarsightMask> {

    public FarsightMask(UUID ownerId) {
        super(ownerId, 170, "Farsight Mask", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "MRD";

        // Whenever a source an opponent controls deals damage to you, if Farsight Mask is untapped, you may draw a card.
        this.addAbility(new FarsightMaskTriggeredAbility());
    }

    public FarsightMask(final FarsightMask card) {
        super(card);
    }

    @Override
    public FarsightMask copy() {
        return new FarsightMask(this);
    }
}

class FarsightMaskTriggeredAbility extends TriggeredAbilityImpl<FarsightMaskTriggeredAbility> {

    public FarsightMaskTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ConditionalOneShotEffect(new DrawCardControllerEffect(1), new FarsightMaskCondition(), ""), false);
    }

    public FarsightMaskTriggeredAbility(final FarsightMaskTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FarsightMaskTriggeredAbility copy() {
        return new FarsightMaskTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER)) {
            UUID sourceControllerId = null;
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                sourceControllerId = permanent.getControllerId();
            } else {
                StackObject sourceStackObject = game.getStack().getStackObject(event.getSourceId());
                if (sourceStackObject != null) {
                    sourceControllerId = sourceStackObject.getControllerId();
                }
            }
            if (event.getTargetId().equals(controllerId) && game.getOpponents(controllerId).contains(sourceControllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you, if Farsight Mask is untapped, you may draw a card.";
    }
}

class FarsightMaskCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && !permanent.isTapped()
                && player != null && player.chooseUse(Outcome.DrawCard, "Do you wish to draw a card?", game)) {
            return true;
        }
        return false;
    }
}