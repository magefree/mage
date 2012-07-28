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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class ExplosiveRevelation extends CardImpl<ExplosiveRevelation> {

    public ExplosiveRevelation(UUID ownerId) {
        super(ownerId, 143, "Explosive Revelation", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // Choose target creature or player. Reveal cards from the top of your library until you reveal a nonland card. Explosive Revelation deals damage equal to that card's converted mana cost to that creature or player. Put the nonland card into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new ExplosiveRevelationEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public ExplosiveRevelation(final ExplosiveRevelation card) {
        super(card);
    }

    @Override
    public ExplosiveRevelation copy() {
        return new ExplosiveRevelation(this);
    }
}

class ExplosiveRevelationEffect extends OneShotEffect<ExplosiveRevelationEffect> {

    public ExplosiveRevelationEffect() {
        super(Constants.Outcome.DrawCard);
        this.staticText = "Reveal cards from the top of your library until you reveal a nonland card, {this} deals damage equal to that card's converted mana cost to that creature or player. Put the nonland card into your hand and the rest on the bottom of your library in any order.";
    }

    public ExplosiveRevelationEffect(final ExplosiveRevelationEffect effect) {
        super(effect);
    }

    @Override
    public ExplosiveRevelationEffect copy() {
        return new ExplosiveRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() > 0) {
            CardsImpl cards = new CardsImpl();
            Library library = player.getLibrary();
            Card card = null;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            } while (library.size() > 0 && card != null && card.getCardType().contains(CardType.LAND));
            // reveal cards
            if (!cards.isEmpty()) {
                player.revealCards("Explosive Revelation", cards, game);
            }
            // the nonland card
            int damage = card.getManaCost().convertedManaCost();
            // assign damage to target
            for (UUID targetId: targetPointer.getTargets(game, source)) {
                Permanent targetedCreature = game.getPermanent(targetId);
                if (targetedCreature != null) {
                    targetedCreature.damage(damage, source.getSourceId(), game, true, false);
                }
                else {
                    Player targetedPlayer = game.getPlayer(targetId);
                    if (targetedPlayer != null) {
                        targetedPlayer.damage(damage, source.getSourceId(), game, false, true);
                    }
                }
            }
            // move nonland card to hand
            card.moveToZone(Constants.Zone.HAND, id, game, true);
            // remove nonland card from revealed card list
            cards.remove(card);
            // put the rest of the cards into the bottom of the library in any order
            if (cards.size() != 0) {
                TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put on the bottom of your library"));
                target.setRequired(true);
                while (cards.size() > 1) {
                    player.choose(Constants.Outcome.Neutral, cards, target, game);
                    Card chosenCard = cards.get(target.getFirstTarget(), game);
                    if (chosenCard != null) {
                        cards.remove(card);
                        chosenCard.moveToZone(Constants.Zone.LIBRARY, id, game, false);
                    }
                    target.clearChosen();
                }
                if (cards.size() == 1) {
                    Card chosenCard = cards.get(cards.iterator().next(), game);
                    chosenCard.moveToZone(Constants.Zone.LIBRARY, id, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
