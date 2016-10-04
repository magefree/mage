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
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Blinke
 */
public class SpoilsOfEvil extends CardImpl {
    private static final FilterCard filter = new FilterCard("artifact or creature card in target opponents graveyard");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }
    
    public SpoilsOfEvil(UUID ownerId) {
        super(ownerId, 51, "Spoils of Evil", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "ICE";

        // For each artifact or creature card in target opponent's graveyard, add {C} to your mana pool and you gain 1 life.
        this.getSpellAbility().addEffect(new SpoilsOfEvilEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public SpoilsOfEvil(final SpoilsOfEvil card) {
        super(card);
    }

    @Override
    public SpoilsOfEvil copy() {
        return new SpoilsOfEvil(this);
    }
    
    class SpoilsOfEvilEffect extends OneShotEffect {

        public SpoilsOfEvilEffect() {
            super(Outcome.GainLife);
            this.staticText = "For each artifact or creature card in target opponent's graveyard, add {C} to your mana pool and you gain 1 life.";
        }
        
        public SpoilsOfEvilEffect(final SpoilsOfEvilEffect effect) {
            super(effect);
        }
        
        @Override
        public SpoilsOfEvilEffect copy() {
            return new SpoilsOfEvilEffect(this);
        }
        
        @Override
        public boolean apply(Game game, Ability source) {
            Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
            Player controller = game.getPlayer(source.getControllerId());
            
            if(targetOpponent != null && controller != null) {
                int cardCount = targetOpponent.getGraveyard().count(filter, game);
                controller.gainLife(cardCount, game);
                controller.getManaPool().addMana(Mana.ColorlessMana(cardCount), game, source);
                return true;
            }
            return false;
        }
    }
}
