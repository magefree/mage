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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class OmenMachine extends CardImpl<OmenMachine> {

    public OmenMachine(UUID ownerId) {
        super(ownerId, 148, "Omen Machine", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "NPH";

        // Players can't draw cards.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OmenMachineEffect()));
        
        // At the beginning of each player's draw step, that player exiles the top card of his or her library. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.
        this.addAbility(new OmenMachineAbility());
    }

    public OmenMachine(final OmenMachine card) {
        super(card);
    }

    @Override
    public OmenMachine copy() {
        return new OmenMachine(this);
    }
}

class OmenMachineEffect extends ReplacementEffectImpl<OmenMachineEffect> {

    public OmenMachineEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Players can't draw cards";
    }
    
    public OmenMachineEffect(final OmenMachineEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OmenMachineEffect copy() {
        return new OmenMachineEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DRAW_CARD) {
            return true;
        }
        return false;
    }
    
}

class OmenMachineAbility extends TriggeredAbilityImpl<OmenMachineAbility> {

	public OmenMachineAbility() {
		super(Zone.BATTLEFIELD, new OmenMachineEffect2());
	}

	public OmenMachineAbility(final OmenMachineAbility ability) {
		super(ability);
	}

	@Override
	public OmenMachineAbility copy() {
		return new OmenMachineAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.DRAW_STEP_PRE) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "At the beginning of each player's draw step, that player exiles the top card of his or her library. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.";
	}

}

class OmenMachineEffect2 extends OneShotEffect<OmenMachineEffect2> {

    public OmenMachineEffect2() {
        super(Outcome.PlayForFree);
    }
    
    public OmenMachineEffect2(final OmenMachineEffect2 effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(source));
        if (player != null) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                card.moveToExile(source.getSourceId(), "Omen Machine Exile", source.getId(), game);
                if (card.getCardType().contains(CardType.LAND)) {
                    card.putOntoBattlefield(game, Zone.EXILED, source.getId(), player.getId());
                    game.getExile().removeCard(card, game);
                }
                else {
                    player.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public OmenMachineEffect2 copy() {
        return new OmenMachineEffect2(this);
    }
    
}
