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
package mage.sets.seventhedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class FinalFortune extends CardImpl {

    public FinalFortune(UUID ownerId) {
        super(ownerId, 182, "Final Fortune", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{R}{R}");
        this.expansionSetCode = "7ED";


        // Take an extra turn after this one. At the beginning of that turn's end step, you lose the game.
        this.getSpellAbility().addEffect(new FinalFortuneEffect());
    }

    public FinalFortune(final FinalFortune card) {
        super(card);
    }

    @Override
    public FinalFortune copy() {
        return new FinalFortune(this);
    }
}

class FinalFortuneEffect extends OneShotEffect {

    public FinalFortuneEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Take an extra turn after this one. At the beginning of that turn's end step, you lose the game.";
    }
    
    public FinalFortuneEffect(final FinalFortuneEffect effect) {
        super(effect);
    }

    @Override
    public FinalFortuneEffect copy() {
        return new FinalFortuneEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        // Take an extra turn after this one
        TurnMod extraTurn = new TurnMod(source.getControllerId(), false);
        game.getState().getTurnMods().add(extraTurn);
        
        FinalFortuneLoseDelayedTriggeredAbility delayedTriggeredAbility = new FinalFortuneLoseDelayedTriggeredAbility();
        delayedTriggeredAbility.setSourceId(source.getSourceId());
        delayedTriggeredAbility.setControllerId(source.getControllerId());
        delayedTriggeredAbility.setConnectedTurnMod(extraTurn.getId());
        game.addDelayedTriggeredAbility(delayedTriggeredAbility);
        
        return true;
    }
    
}

class FinalFortuneLoseDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID connectedTurnMod;

    public FinalFortuneLoseDelayedTriggeredAbility() {
        super(new FinalFortuneLoseEffect(), Duration.EndOfGame);
    }

    public FinalFortuneLoseDelayedTriggeredAbility(final FinalFortuneLoseDelayedTriggeredAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
    }

    @Override
    public FinalFortuneLoseDelayedTriggeredAbility copy() {
        return new FinalFortuneLoseDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
            if (connectedTurnMod != null && connectedTurnMod.equals(game.getState().getTurnId())) {
                return true;
            }
        }
        return false;
    }

    public void setConnectedTurnMod(UUID connectedTurnMod) {
        this.connectedTurnMod = connectedTurnMod;
    }

    @Override
    public String getRule() {
        return "At the beginning of that turn's end step, you lose the game";
    }
    
}

class FinalFortuneLoseEffect extends OneShotEffect {

    public FinalFortuneLoseEffect() {
        super(Outcome.Detriment);
        this.staticText = "You lose the game";
    }
    
    public FinalFortuneLoseEffect(final FinalFortuneLoseEffect effect) {
        super(effect);
    }

    @Override
    public FinalFortuneLoseEffect copy() {
        return new FinalFortuneLoseEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.lost(game);
            return true;
        }
        return false;
    }
    
}
