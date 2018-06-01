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
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.LoseControlOnOtherPlayersControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class WordOfCommand extends CardImpl {

    public WordOfCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");

        // Look at target opponent's hand and choose a card from it. You control that player until Word of Command finishes resolving. The player plays that card if able. While doing so, the player can activate mana abilities only if they're from lands that player controls and only if mana they produce is spent to activate other mana abilities of lands the player controls and/or to play that card. If the chosen card is cast as a spell, you control the player while that spell is resolving.
        this.getSpellAbility().addEffect(new WordOfCommandEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public WordOfCommand(final WordOfCommand card) {
        super(card);
    }

    @Override
    public WordOfCommand copy() {
        return new WordOfCommand(this);
    }
}

class WordOfCommandEffect extends OneShotEffect {

    public WordOfCommandEffect() {
        super(Outcome.GainControl);
        this.staticText = "Look at target opponent's hand and choose a card from it. You control that player until Word of Command finishes resolving. The player plays that card if able. While doing so, the player can activate mana abilities only if they're from lands that player controls and only if mana they produce is spent to activate other mana abilities of lands the player controls and/or to play that card. If the chosen card is cast as a spell, you control the player while that spell is resolving";
    }

    public WordOfCommandEffect(final WordOfCommandEffect effect) {
        super(effect);
    }

    @Override
    public WordOfCommandEffect copy() {
        return new WordOfCommandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Card card = null;
        if (controller != null && targetPlayer != null && sourceObject != null) {

            // Look at target opponent's hand and choose a card from it
            TargetCard targetCard = new TargetCard(Zone.HAND, new FilterCard());
            if (controller.choose(Outcome.Discard, targetPlayer.getHand(), targetCard, game)) {
                card = game.getCard(targetCard.getFirstTarget());
            }

            // You control that player until Word of Command finishes resolving
            controller.controlPlayersTurn(game, targetPlayer.getId());
            while (controller != null && controller.canRespond()) {
                if (controller.chooseUse(Outcome.Benefit, "Resolve " + sourceObject.getLogName() + " now" + (card != null ? " and play " + card.getLogName() : "") + '?', source, game)) {
                    // this is used to give the controller a little space to utilize his player controlling effect (look at face down creatures, hand, etc.)
                    break;
                }
            }

            // The player plays that card if able
            if (card != null) {
                RestrictionEffect effect = new WordOfCommandCantActivateEffect();
                effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
                game.addEffect(effect, source); // While doing so, the player can activate mana abilities only if they're from lands that player controls
                // TODO: and only if mana they produce is spent to activate other mana abilities of lands he or she controls and/or play that card (so you can't tap out the player's lands)
                if ((card.isLand() && (!targetPlayer.canPlayLand() || !game.getActivePlayerId().equals(targetPlayer.getId())))
                        || !targetPlayer.playCard(card, game, false, true, new MageObjectReference(source.getSourceObject(game), game))) {
                    game.informPlayers(targetPlayer.getLogName() + " didn't play " + card.getLogName());
                    // TODO: needs an automatic check for whether the card is castable (so it can't be cancelled if that's the case)
                }

                for (RestrictionEffect eff : game.getContinuousEffects().getRestrictionEffects()) {
                    if (eff instanceof WordOfCommandCantActivateEffect) {
                        eff.discard();
                    }
                }
                game.getContinuousEffects().removeInactiveEffects(game);
                Spell spell = game.getSpell(card.getId());
                if (spell != null) {
                    spell.setCommandedBy(controller.getId()); // If the chosen card is cast as a spell, you control the player while that spell is resolving
                }
            }

            if (sourceObject != null) {
                Effect effect = new LoseControlOnOtherPlayersControllerEffect(controller.getLogName(), targetPlayer.getLogName());
                effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
                // You control the player until Word of Command finishes resolving
                // TODO: using a DelayedTriggeredAbility to end the effect isn't the optimal solution, since effects like Time Stop can stop it from triggering even outside the stack
                DelayedTriggeredAbility ability = new WordOfCommandDelayedTriggeredAbility(effect, source.getSourceId());
                ability.setSourceId(controller.getId());
                ability.setControllerId(controller.getId());
                game.addDelayedTriggeredAbility(ability);
                if (card != null && !card.isLand()) { // this sets up a lose control effect for when the spell finishes resolving
                    ability = new WordOfCommandDelayedTriggeredAbility(effect, card.getId());
                    ability.setSourceId(controller.getId());
                    ability.setControllerId(controller.getId());
                    game.addDelayedTriggeredAbility(ability);
                }
            } else {
                controller.resetOtherTurnsControlled();
                targetPlayer.setGameUnderYourControl(true);
            }
            return true;
        }
        return false;
    }
}

class WordOfCommandCantActivateEffect extends RestrictionEffect {

    public WordOfCommandCantActivateEffect() {
        super(Duration.EndOfTurn);
    }

    public WordOfCommandCantActivateEffect(final WordOfCommandCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public WordOfCommandCantActivateEffect copy() {
        return new WordOfCommandCantActivateEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return !permanent.isLand() && permanent.getControllerId().equals(this.targetPointer.getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }
}

class WordOfCommandDelayedTriggeredAbility extends DelayedTriggeredAbility {
    
    private UUID cardId;

    WordOfCommandDelayedTriggeredAbility(Effect effect, UUID cardId) {
        super(effect, Duration.EndOfStep);
        this.cardId = cardId;
        this.usesStack = false;
    }

    WordOfCommandDelayedTriggeredAbility(final WordOfCommandDelayedTriggeredAbility ability) {
        super(ability);
        this.cardId = ability.cardId;
    }

    @Override
    public WordOfCommandDelayedTriggeredAbility copy() {
        return new WordOfCommandDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.STACK && event.getTargetId().equals(cardId)) {
            return true;
        }
        return false;
    }
}
