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
package mage.sets.limitedalpha;

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
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author KholdFuzion

 */
public class NaturalSelection extends CardImpl<NaturalSelection> {

    public NaturalSelection(UUID ownerId) {
        super(ownerId, 121, "Natural Selection", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "LEA";

        this.color.setGreen(true);

        // Look at the top three cards of target player's library, then put them back in any order. You may have that player shuffle his or her library.
        this.getSpellAbility().addEffect(new NaturalSelectionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));
    }

    public NaturalSelection(final NaturalSelection card) {
        super(card);
    }

    @Override
    public NaturalSelection copy() {
        return new NaturalSelection(this);
    }
}

class  NaturalSelectionEffect extends OneShotEffect< NaturalSelectionEffect> {

    public  NaturalSelectionEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of target player's library, then put them back in any order. You may have that player shuffle his or her library.";
    }

    public  NaturalSelectionEffect(final  NaturalSelectionEffect effect) {
        super(effect);
    }

    @Override
    public  NaturalSelectionEffect copy() {
        return new  NaturalSelectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null || you == null) {
            return false;
        }
        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }

        you.lookAtCards("Natural Selection", cards, game);

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the top of target player's library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            you.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
        }
        if (you.chooseUse(Outcome.Neutral, "You may have that player shuffle his or her library", game)){
            player.shuffleLibrary(game);
        }
        return true;
    }
}