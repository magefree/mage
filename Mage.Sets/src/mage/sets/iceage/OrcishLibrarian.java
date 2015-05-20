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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Mitchel Stein
 */
public class OrcishLibrarian extends CardImpl {

    public OrcishLibrarian(UUID ownerId) {
        super(ownerId, 209, "Orcish Librarian", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Orc");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}, {tap}: Look at the top eight cards of your library. Exile four of them at random, then put the rest on top of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OrcishLibrarianEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public OrcishLibrarian(final OrcishLibrarian card) {
        super(card);
    }

    @Override
    public OrcishLibrarian copy() {
        return new OrcishLibrarian(this);
    }
}

class OrcishLibrarianEffect extends OneShotEffect {

    public OrcishLibrarianEffect() {
        super(Outcome.Neutral);
        this.staticText = "Look at the top eight cards of your library. Exile four of them at random, then put the rest on top of your library in any order.";
    }

    public OrcishLibrarianEffect(final OrcishLibrarianEffect effect) {
        super(effect);
    }

    @Override
    public OrcishLibrarianEffect copy() {
        return new OrcishLibrarianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            int cardsCount = Math.min(8, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    game.setZone(card.getId(), Zone.PICK);
                }
            }

            if (cards.size() > 0) {
                for (int i = 0; i < 4; i++)
                {
                    if (cards.size() > 0)
                    {
                        Card card = cards.getRandom(game);
                        player.moveCardToExileWithInfo(card, null, null, source.getId(), game, Zone.LIBRARY, true);
                        cards.remove(card);
                    }
                }
                player.lookAtCards("OrcishLibrarian", cards, game);
                TargetCard target = new TargetCard (Zone.PICK, new FilterCard("card to put on the top of target player's library"));
                while (player.isInGame() && cards.size() > 0) {
                    player.choose(Outcome.Neutral, cards, target, game);
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                    target.clearChosen();
                }
            }
            return true;
        }
        return false;
    }
}