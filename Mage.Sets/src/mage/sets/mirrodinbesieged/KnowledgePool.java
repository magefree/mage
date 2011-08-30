/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.mirrodinbesieged;

import mage.target.common.TargetCardInExile;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KnowledgePool extends CardImpl<KnowledgePool> {

	public KnowledgePool(UUID ownerId) {
		super(ownerId, 111, "Knowledge Pool", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
		this.expansionSetCode = "MBS";
		// Imprint - When Knowledge Pool enters the battlefield, each player exiles the top three cards of his or her library
		this.addAbility(new EntersBattlefieldTriggeredAbility(new KnowledgePoolEffect1(), false));
        // Whenever a player casts a spell from his or her hand, that player exiles it. If the player does, he or she may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost.
		this.addAbility(new KnowledgePoolAbility());
	}

	public KnowledgePool(final KnowledgePool card) {
		super(card);
	}

	@Override
	public KnowledgePool copy() {
		return new KnowledgePool(this);
	}

}

class KnowledgePoolEffect1 extends OneShotEffect<KnowledgePoolEffect1> {

    public KnowledgePoolEffect1() {
        super(Outcome.Neutral);
        staticText = "each player exiles the top three cards of his or her library";
    }
    
    public KnowledgePoolEffect1(final KnowledgePoolEffect1 effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
		Player sourcePlayer = game.getPlayer(source.getControllerId());
		for (UUID playerId: sourcePlayer.getInRange()) {
			Player player = game.getPlayer(playerId);
			if (player != null) {
                int amount = Math.min(3, player.getLibrary().size());
                for (int i = 0; i < amount; i++) {
                    player.getLibrary().removeFromTop(game).moveToExile(source.getSourceId(), "Knowledge Pool Exile", source.getId(), game);
                }
            }
        }
        return true;
    }

    @Override
    public KnowledgePoolEffect1 copy() {
        return new KnowledgePoolEffect1(this);
    }
    
}

class KnowledgePoolAbility extends TriggeredAbilityImpl<KnowledgePoolAbility> {

	public KnowledgePoolAbility() {
		super(Zone.BATTLEFIELD, new KnowledgePoolEffect2(), false);
	}

	public KnowledgePoolAbility(final KnowledgePoolAbility ability) {
		super(ability);
	}

	@Override
	public KnowledgePoolAbility copy() {
		return new KnowledgePoolAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.SPELL_CAST && event.getZone() == Zone.HAND) {            
			Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
		}
		return false;
	}

}

class KnowledgePoolEffect2 extends OneShotEffect<KnowledgePoolEffect2> {

    private static FilterNonlandCard filter = new FilterNonlandCard("nonland card exiled with Knowledge Pool");
    
    public KnowledgePoolEffect2() {
        super(Outcome.Neutral);
        staticText = "Whenever a player casts a spell from his or her hand, that player exiles it. If the player does, he or she may cast another nonland card exiled with {this} without paying that card's mana cost";
    }
    
    public KnowledgePoolEffect2(final KnowledgePoolEffect2 effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(source));
        if (spell != null) {
            if (spell.moveToExile(source.getSourceId(), "Knowledge Pool Exile", id, game)) {
                Player player = game.getPlayer(spell.getControllerId());
                if (player != null && player.chooseUse(Outcome.PlayForFree, "Cast another nonland card exiled with Knowledge Pool without paying that card's mana cost?", game)) {
                    TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
                    while (player.choose(Outcome.PlayForFree, game.getExile().getExileZone(source.getSourceId()), target, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null && !card.getId().equals(spell.getSourceId())) {
                            game.getExile().removeCard(card, game);
                            return player.cast(card.getSpellAbility(), game, true);
                        }
                        target.clearChosen();
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public KnowledgePoolEffect2 copy() {
        return new KnowledgePoolEffect2(this);
    }
    
}