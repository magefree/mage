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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class ShireiShizosCaretaker extends CardImpl {

    public ShireiShizosCaretaker(UUID ownerId) {
        super(ownerId, 81, "Shirei, Shizo's Caretaker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature with power 1 or less is put into your graveyard from the battlefield, you may return that card to the battlefield at the beginning of the next end step if Shirei, Shizo's Caretaker is still on the battlefield.
        this.addAbility(new ShireiShizosCaretakerTriggeredAbility(this.getId()));
    }

    public ShireiShizosCaretaker(final ShireiShizosCaretaker card) {
        super(card);
    }

    @Override
    public ShireiShizosCaretaker copy() {
        return new ShireiShizosCaretaker(this);
    }
}

class ShireiShizosCaretakerTriggeredAbility extends TriggeredAbilityImpl {
        
    ShireiShizosCaretakerTriggeredAbility(UUID shireiId) {
        super(Zone.BATTLEFIELD, new ShireiShizosCaretakerEffect(shireiId), false);
    }
    
    ShireiShizosCaretakerTriggeredAbility(final ShireiShizosCaretakerTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public ShireiShizosCaretakerTriggeredAbility copy() {
        return new ShireiShizosCaretakerTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent LKIpermanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
            Card card = game.getCard(zEvent.getTargetId());

            if (card != null && LKIpermanent != null &&
                    card.getOwnerId().equals(this.controllerId) && 
                    zEvent.getToZone() == Zone.GRAVEYARD &&
                    zEvent.getFromZone() == Zone.BATTLEFIELD &&
                    card.getCardType().contains(CardType.CREATURE) &&
                    LKIpermanent.getPower().getValue() <= 1) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(zEvent.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a creature with power 1 or less is put into your graveyard from the battlefield, you may return that card to the battlefield at the beginning of the next end step if Shirei, Shizo's Caretaker is still on the battlefield.";
    }
}

class ShireiShizosCaretakerEffect extends OneShotEffect {
    
    protected final UUID shireiId;
    
    ShireiShizosCaretakerEffect(UUID shireiId) {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may return that card to the battlefield at the beginning of the next end step if {this} is still on the battlefield.";
        this.shireiId = shireiId;
    }
    
    ShireiShizosCaretakerEffect(final ShireiShizosCaretakerEffect effect) {
        super(effect);
        this.shireiId = effect.shireiId;
    }
    
    @Override
    public ShireiShizosCaretakerEffect copy() {
        return new ShireiShizosCaretakerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ShireiShizosCaretakerReturnEffect(shireiId);
            effect.setText("return that card to the battlefield if {this} is still on the battlefield");
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game), game);
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}

class ShireiShizosCaretakerReturnEffect extends ReturnToBattlefieldUnderYourControlTargetEffect {
    
    protected final UUID shireiId;
    
    ShireiShizosCaretakerReturnEffect(UUID shireiId) {
        this.shireiId = shireiId;
    }
    
    ShireiShizosCaretakerReturnEffect(final ShireiShizosCaretakerReturnEffect effect) {
        super(effect);
        this.shireiId = effect.shireiId;
    }
    
    @Override
    public ShireiShizosCaretakerReturnEffect copy() {
        return new ShireiShizosCaretakerReturnEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().containsPermanent(shireiId)) {
            return super.apply(game, source);
        }
        return false;
    }
}
