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
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class Necropotence extends CardImpl {

    public Necropotence(UUID ownerId) {
        super(ownerId, 42, "Necropotence", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");
        this.expansionSetCode = "ICE";

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));
        // Whenever you discard a card, exile that card from your graveyard.
        Effect effect = new ExileTargetEffect(null, "", Zone.GRAVEYARD);
        effect.setText("exile that card from your graveyard");
        this.addAbility(new NecropotenceTriggeredAbility(effect));
        // Pay 1 life: Exile the top card of your library face down. Put that card into your hand at the beginning of your next end step.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new NecropotenceEffect(), new PayLifeCost(1)));

    }

    public Necropotence(final Necropotence card) {
        super(card);
    }

    @Override
    public Necropotence copy() {
        return new Necropotence(this);
    }
}

class NecropotenceTriggeredAbility extends TriggeredAbilityImpl {

    NecropotenceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    NecropotenceTriggeredAbility(final NecropotenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NecropotenceTriggeredAbility copy() {
        return new NecropotenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getControllerId().equals(event.getPlayerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you discards a card, " + super.getRule();
    }
}

class NecropotenceEffect extends OneShotEffect {

    public NecropotenceEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top card of your library face down. Put that card into your hand at the beginning of your next end step";
    }

    public NecropotenceEffect(final NecropotenceEffect effect) {
        super(effect);
    }

    @Override
    public NecropotenceEffect copy() {
        return new NecropotenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, false)) {
                    card.setFaceDown(true, game);
                    Effect returnToHandEffect = new ReturnToHandTargetEffect(false);
                    returnToHandEffect.setText("put that face down card into your hand");
                    returnToHandEffect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnToHandEffect, TargetController.YOU);
                    delayedAbility.setSourceId(source.getSourceId());
                    delayedAbility.setControllerId(source.getControllerId());
                    delayedAbility.setSourceObject(source.getSourceObject(game), game);
                    game.addDelayedTriggeredAbility(delayedAbility);
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
