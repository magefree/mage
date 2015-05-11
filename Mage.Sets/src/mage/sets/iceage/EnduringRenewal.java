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
package mage.sets.iceage;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public class EnduringRenewal extends CardImpl {

    public EnduringRenewal(UUID ownerId) {
        super(ownerId, 247, "Enduring Renewal", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "ICE";

        // Play with your hand revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithHandRevealedEffect()));
        // If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EnduringRenewalReplacementEffect()));
        // Whenever a creature is put into your graveyard from the battlefield, return it to your hand.
        this.addAbility(new EnduringRenewalTriggeredAbility());
    }

    public EnduringRenewal(final EnduringRenewal card) {
        super(card);
    }

    @Override
    public EnduringRenewal copy() {
        return new EnduringRenewal(this);
    }
}

class EnduringRenewalReplacementEffect extends ReplacementEffectImpl {

    public EnduringRenewalReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card";
    }

    public EnduringRenewalReplacementEffect(final EnduringRenewalReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EnduringRenewalReplacementEffect copy() {
        return new EnduringRenewalReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        } else if (you.getLibrary().size() > 0){
            
            Card top = you.getLibrary().removeFromTop(game);

            Cards cards = new CardsImpl(Zone.PICK);

            cards.add(top);

            you.revealCards("Top card of " + you.getName() + "'s library", cards, game);

            if (top.getCardType().contains(CardType.CREATURE)) {
                top.moveToZone(Zone.GRAVEYARD, top.getId(), game, true);
            } else {
                top.moveToZone(Zone.HAND, top.getId(), game, false);
            }
            
            cards.clear();
            
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}

class PlayWithHandRevealedEffect extends ContinuousEffectImpl {

    public PlayWithHandRevealedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "Play with your hand revealed";
    }

    public PlayWithHandRevealedEffect(final PlayWithHandRevealedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            controller.revealCards(controller.getName(), controller.getHand(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public PlayWithHandRevealedEffect copy() {
        return new PlayWithHandRevealedEffect(this);
    }
}

class EnduringRenewalEffect extends OneShotEffect {

    public EnduringRenewalEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return it to your hand";
    }

    public EnduringRenewalEffect(final EnduringRenewalEffect effect) {
        super(effect);
    }

    @Override
    public EnduringRenewalEffect copy() {
        return new EnduringRenewalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("returningCreature");
        Permanent creature = game.getPermanent(creatureId);
        if (creature != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                creature.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}

class EnduringRenewalTriggeredAbility extends TriggeredAbilityImpl {

    public EnduringRenewalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EnduringRenewalEffect(), false);
    }

    public EnduringRenewalTriggeredAbility(EnduringRenewalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()
                && ((ZoneChangeEvent) event).getPlayerId().equals(this.getControllerId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(CardType.CREATURE)
                    && permanent.getControllerId().equals(this.controllerId)) {
                Effect effect = this.getEffects().get(0);
                effect.setValue("returningCreature", event.getTargetId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature is put into your graveyard from the battlefield, " + super.getRule();
    }

    @Override
    public EnduringRenewalTriggeredAbility copy() {
        return new EnduringRenewalTriggeredAbility(this);
    }
}
