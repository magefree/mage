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
package mage.sets.planeshift;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public class ShiftingSky extends CardImpl {
    
    public ShiftingSky(UUID ownerId) {
        super(ownerId, 32, "Shifting Sky", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "PLS";

        // As Shifting Sky enters the battlefield, choose a color.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ShiftingSkyEffect());
        // All nonland permanents are the chosen color.
        this.addAbility(ability);
    }
    
    public ShiftingSky(final ShiftingSky card) {
        super(card);
    }
    
    @Override
    public ShiftingSky copy() {
        return new ShiftingSky(this);
    }
}

class ShiftingSkyEffect extends OneShotEffect {
    
    public ShiftingSkyEffect() {
        super(Outcome.Neutral);
        staticText = "All nonland permanents are the chosen color";
    }
    
    public ShiftingSkyEffect(final ShiftingSkyEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        
        FilterPermanent filter = new FilterPermanent("All nonland permanents");
        
        filter.add(
                Predicates.not(
                        new CardTypePredicate(CardType.LAND)
                )
        );
        
        if (player != null && permanent != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Outcome.Neutral, colorChoice, game)) {
                game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                ContinuousEffect effect;
                
                for (UUID playerId : player.getInRange()) {
                    Player p = game.getPlayer(playerId);
                    if (p != null) {
                        for (Permanent chosen : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                            effect = new BecomesColorTargetEffect(colorChoice.getColor(), Duration.EndOfTurn, "is " + colorChoice.getChoice());
                            effect.setTargetPointer(new FixedTarget(chosen.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ShiftingSkyEffect copy() {
        return new ShiftingSkyEffect(this);
    }
}
