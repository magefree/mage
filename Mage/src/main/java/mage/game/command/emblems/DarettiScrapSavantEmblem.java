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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class DarettiScrapSavantEmblem extends Emblem {
    // You get an emblem with "Whenever an artifact is put into your graveyard from the battlefield, return that card to the battlefield at the beginning of the next end step."

    public DarettiScrapSavantEmblem() {
        setName("Emblem Daretti");
        this.setExpansionSetCodeForImage("C14");


        this.getAbilities().add(new DarettiScrapSavantTriggeredAbility());
    }
}

class DarettiScrapSavantTriggeredAbility extends TriggeredAbilityImpl {

    DarettiScrapSavantTriggeredAbility() {
        super(Zone.COMMAND, new DarettiScrapSavantEffect(), false);
    }

    DarettiScrapSavantTriggeredAbility(final DarettiScrapSavantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DarettiScrapSavantTriggeredAbility copy() {
        return new DarettiScrapSavantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() == Zone.BATTLEFIELD
                && zEvent.getTarget().isArtifact()
                && zEvent.getTarget().getOwnerId().equals(this.controllerId)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(zEvent.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact is put into your graveyard from the battlefield, " + super.getRule();
    }
}

class DarettiScrapSavantEffect extends OneShotEffect {

    DarettiScrapSavantEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return that card to the battlefield at the beginning of the next end step";
    }

    DarettiScrapSavantEffect(final DarettiScrapSavantEffect effect) {
        super(effect);
    }

    @Override
    public DarettiScrapSavantEffect copy() {
        return new DarettiScrapSavantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            effect.setText("return that card to the battlefield at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone.COMMAND, effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}
