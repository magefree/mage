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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Zoologist extends CardImpl {

    public Zoologist(UUID ownerId) {
        super(ownerId, 285, "Zoologist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {3}{G}, {tap}: Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZoologistEffect(), new ManaCostsImpl("{3}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public Zoologist(final Zoologist card) {
        super(card);
    }

    @Override
    public Zoologist copy() {
        return new Zoologist(this);
    }
}

class ZoologistEffect extends OneShotEffect {

    public ZoologistEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard";
    }

    public ZoologistEffect(final ZoologistEffect effect) {
        super(effect);
    }

    @Override
    public ZoologistEffect copy() {
        return new ZoologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Zoologist", cards, game);

            if (card != null) {
                if (card.getCardType().contains(CardType.CREATURE)) {
                    player.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                } else {
                    player.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                }
            }
        }
        return false;
    }
}
