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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MindGrind extends CardImpl<MindGrind> {

    public MindGrind(UUID ownerId) {
        super(ownerId, 178, "Mind Grind", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{U}{B}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Each opponent reveals cards from the top of his or her library until he or she reveals X land cards, then puts all cards revealed this way into his or her graveyard. X can't be 0.
        this.getSpellAbility().addEffect(new MindGrindEffect());
        for (VariableCost cost: this.getSpellAbility().getManaCosts().getVariableCosts()) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMinX(1);
                break;
                }
            }
        }

    public MindGrind(final MindGrind card) {
        super(card);
    }

    @Override
    public MindGrind copy() {
        return new MindGrind(this);
    }
}

class MindGrindEffect extends OneShotEffect<MindGrindEffect> {

    public MindGrindEffect() {
        super(Outcome.Discard);
        this.staticText = "Each opponent reveals cards from the top of his or her library until he or she reveals X land cards, then puts all cards revealed this way into his or her graveyard. X can't be 0";
    }

    public MindGrindEffect(final MindGrindEffect effect) {
        super(effect);
    }

    @Override
    public MindGrindEffect copy() {
        return new MindGrindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        Card sourceCard = game.getCard(source.getSourceId());
        if (xValue < 1 || sourceCard == null) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            int landsToReveal = xValue;
            Cards cards = new CardsImpl();
            while(player.getLibrary().size() > 0){
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if(card.getCardType().contains(CardType.LAND)){
                        --landsToReveal;
                        if (landsToReveal < 1) {
                            break;
                        }
                    }
                }
            }
            player.revealCards(new StringBuilder("by ").append(sourceCard.getName()).append(" from ").append(player.getName()).toString(), cards, game);
            for(Card card : cards.getCards(game)){
                if(card != null){
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false);
                }
            }
        }
        return true;
    }
}
