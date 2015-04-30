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
package mage.sets.dragonsoftarkir;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public class SilumgarsScorn extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate("Dragon"));
    }    
    
    public SilumgarsScorn(UUID ownerId) {
        super(ownerId, 78, "Silumgar's Scorn", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}{U}");
        this.expansionSetCode = "DTK";

        // As an additional cost to cast Silumgar's Scorn, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("As an additional cost to cast {this}, you may reveal a Dragon card from your hand"));
        
        // Counter target spell unless its controller pays {1}. If you revealed a Dragon card or controlled a Dragon as you cast Silumgar's Scorn, counter that spell instead.
        this.getSpellAbility().addEffect(new SilumgarsScornCounterEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addWatcher(new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
        
    }

    
    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType().equals(AbilityType.SPELL)) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0,1, filter)));
                }
            }
        }
    }
    
    public SilumgarsScorn(final SilumgarsScorn card) {
        super(card);
    }

    @Override
    public SilumgarsScorn copy() {
        return new SilumgarsScorn(this);
    }
}

class SilumgarsScornCounterEffect extends OneShotEffect {

    public SilumgarsScornCounterEffect() {
        super(Outcome.Detriment);
        staticText = "<br/>Counter target spell unless its controller pays {1}. If you revealed a Dragon card or controlled a Dragon as you cast {this}, counter that spell instead";
    }

    public SilumgarsScornCounterEffect(final SilumgarsScornCounterEffect effect) {
        super(effect);
    }

    @Override
    public SilumgarsScornCounterEffect copy() {
        return new SilumgarsScornCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get("DragonOnTheBattlefieldWhileSpellWasCastWatcher");
                boolean condition = watcher != null && watcher.castWithConditionTrue(source.getId());
                if (!condition) {
                    for (Cost cost: source.getCosts()) {
                        if (cost instanceof RevealTargetFromHandCost) {
                            condition = ((RevealTargetFromHandCost)cost).getNumberRevealedCards() > 0;
                        }
                    }
                }
                if (condition) {
                    return game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                if (!(player.chooseUse(Outcome.Benefit, "Would you like to pay {1} to prevent counter effect?", game) && 
                        new GenericManaCost(1).pay(source, game, spell.getSourceId(), spell.getControllerId(), false))) {
                    return game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
            }
        }
        return true;
    }

}
