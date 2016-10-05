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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public class PossessedPortal extends CardImpl {

    public PossessedPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{8}");

        // If a player would draw a card, that player skips that draw instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PossessedPortalReplacementEffect()));
        
        // At the beginning of each end step, each player sacrifices a permanent unless he or she discards a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new PossessedPortalEffect(), TargetController.ANY, false));
    }

    public PossessedPortal(final PossessedPortal card) {
        super(card);
    }

    @Override
    public PossessedPortal copy() {
        return new PossessedPortal(this);
    }
}

class PossessedPortalReplacementEffect extends ReplacementEffectImpl {

    PossessedPortalReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "If a player would draw a card, that player skips that draw instead";
    }

    PossessedPortalReplacementEffect(final PossessedPortalReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PossessedPortalReplacementEffect copy() {
        return new PossessedPortalReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

class PossessedPortalEffect extends OneShotEffect {
    
    PossessedPortalEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player sacrifices a permanent unless he or she discards a card";
    }
    
    PossessedPortalEffect(final PossessedPortalEffect effect) {
        super(effect);
    }
    
    @Override
    public PossessedPortalEffect copy() {
        return new PossessedPortalEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            Cost discardCost = new DiscardCardCost();
            if (discardCost.canPay(source, source.getSourceId(), playerId, game)
                    && player.chooseUse(Outcome.Discard, "Discard a card? (Otherwise sacrifice a permanent)", source, game)) {
                discardCost.pay(source, game, source.getSourceId(), playerId, true, null);
            }
            else {
                Cost sacrificeCost = new SacrificeTargetCost(new TargetControlledPermanent());
                sacrificeCost.pay(source, game, source.getSourceId(), playerId, true, null);
            }
        }
        return true;
    }
}
