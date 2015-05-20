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
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class StuntedGrowth extends CardImpl {

    public StuntedGrowth(UUID ownerId) {
        super(ownerId, 154, "Stunted Growth", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "ICE";


        // Target player chooses three cards from his or her hand and puts them on top of his or her library in any order.
        this.getSpellAbility().addEffect(new StuntedGrowthEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public StuntedGrowth(final StuntedGrowth card) {
        super(card);
    }

    @Override
    public StuntedGrowth copy() {
        return new StuntedGrowth(this);
    }
}

class StuntedGrowthEffect extends OneShotEffect {

    public StuntedGrowthEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player chooses three cards from his or her hand and puts them on top of his or her library in any order";
    }

    public StuntedGrowthEffect(final StuntedGrowthEffect effect) {
        super(effect);
    }

    @Override
    public StuntedGrowthEffect copy() {
        return new StuntedGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            int possibleNumber = Math.min(3, targetPlayer.getHand().size());
            if (possibleNumber > 0) {
                Target target = new TargetCardInHand(possibleNumber, new FilterCard("cards from your hand"));
                targetPlayer.choose(outcome, target, source.getSourceId(), game);
                Cards cards = new CardsImpl();
                for (UUID cardId: target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                targetPlayer.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
