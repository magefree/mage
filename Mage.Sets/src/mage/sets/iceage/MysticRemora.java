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
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.game.stack.Spell;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;


/**
 *
 * @author TGower
 */
public class MysticRemora extends CardImpl {

    public MysticRemora(UUID ownerId) {
        super(ownerId, 87, "Mystic Remora", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.expansionSetCode = "ICE";

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));
        // Whenever an opponent casts a noncreature spell, you may draw a card unless that player pays {4}.
        this.addAbility(new MysticRemoraTriggeredAbility());
        
    }

    public MysticRemora(final MysticRemora card) {
        super(card);
    }

    @Override
    public MysticRemora copy() {
        return new MysticRemora(this);
    }
}

class MysticRemoraTriggeredAbility extends TriggeredAbilityImpl {
    

    public MysticRemoraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MysticRemoraEffect(), false);
        
    }

    public MysticRemoraTriggeredAbility(final MysticRemoraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MysticRemoraTriggeredAbility copy() {
        return new MysticRemoraTriggeredAbility(this);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }    
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            if (event.getType() == GameEvent.EventType.SPELL_CAST) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && !spell.getCardType().contains(CardType.CREATURE)) {
                    Player controller = game.getPlayer(game.getControllerId(this.controllerId));
                    Player player = game.getPlayer(spell.getControllerId());
                    if (controller != player) {
                        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                return true;
                }
            }
        }
        return false;
    }
        
    @Override
    public String getRule() {
        return "Whenever an opponent casts a noncreature spell, you may draw a card unless that player pays {4}.";
    }
}
    
    class MysticRemoraEffect extends OneShotEffect {

    public MysticRemoraEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may draw a card unless that player pays {4}";
    }

    public MysticRemoraEffect(final MysticRemoraEffect effect) {
        super(effect);
    }
    
    @Override
    public MysticRemoraEffect copy() {
        return new MysticRemoraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            Cost cost = new GenericManaCost(4);
            String message = "Would you like to pay {4} to prevent the opponent to draw a card?";
            if (!(opponent.chooseUse(Outcome.Benefit, message, game) && cost.pay(source, game, source.getSourceId(), opponent.getId(), false))) {
                if(controller.chooseUse(Outcome.DrawCard, "Draw a card (" + sourceObject.getLogName() +")", game)) {
                    controller.drawCards(1, game);
                }
            }
            return true;
        }
        return false;
    }
    
}

    

    
