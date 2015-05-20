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
package mage.sets.timespiral;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.game.turn.UpkeepStep;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class ParadoxHaze extends CardImpl {

    public ParadoxHaze(UUID ownerId) {
        super(ownerId, 71, "Paradox Haze", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Aura");


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.
        this.addAbility(new ParadoxHazeTriggeredAbility());
    }

    public ParadoxHaze(final ParadoxHaze card) {
        super(card);
    }

    @Override
    public ParadoxHaze copy() {
        return new ParadoxHaze(this);
    }
}

class ParadoxHazeTriggeredAbility extends TriggeredAbilityImpl {
    
    protected int lastTriggerTurnNumber;
    
    ParadoxHazeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ParadoxHazeEffect(), false);
    }
    
    ParadoxHazeTriggeredAbility(final ParadoxHazeTriggeredAbility ability) {
        super(ability);
        lastTriggerTurnNumber = ability.lastTriggerTurnNumber;
    }
    
    @Override
    public ParadoxHazeTriggeredAbility copy() {
        return new ParadoxHazeTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            Permanent permanent = game.getPermanent(this.sourceId);
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getAttachedTo());
                if (player != null && game.getActivePlayerId().equals(player.getId()) && lastTriggerTurnNumber != game.getTurnNum()) {
                    lastTriggerTurnNumber = game.getTurnNum();
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.";
    }
}

class ParadoxHazeEffect extends OneShotEffect {
    
    ParadoxHazeEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player gets an additional upkeep step after this step";
    }
    
    ParadoxHazeEffect(final ParadoxHazeEffect effect) {
        super(effect);
    }
    
    @Override
    public ParadoxHazeEffect copy() {
        return new ParadoxHazeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(this.getTargetPointer().getFirst(game, source), new UpkeepStep(), null));
        return true;
    }
}
