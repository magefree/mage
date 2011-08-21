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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author North
 */
public class DruidicSatchel extends CardImpl<DruidicSatchel> {

    public DruidicSatchel(UUID ownerId) {
        super(ownerId, 207, "Druidic Satchel", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "M12";

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DruidicSatchelEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public DruidicSatchel(final DruidicSatchel card) {
        super(card);
    }

    @Override
    public DruidicSatchel copy() {
        return new DruidicSatchel(this);
    }
}

class DruidicSatchelEffect extends OneShotEffect<DruidicSatchelEffect> {

    public DruidicSatchelEffect() {
        super(Outcome.DrawCard);
        staticText = "Reveal the top card of your library. If it's a creature card, put a 1/1 green Saproling creature token onto the battlefield. If it's a land card, put that card onto the battlefield under your control. If it's a noncreature, nonland card, you gain 2 life";
    }

    public DruidicSatchelEffect(final DruidicSatchelEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = player.getLibrary().getFromTop(game);
        if (card != null) {
            if (card.getCardType().contains(CardType.CREATURE)) {
                Token token = new SaprolingToken();
                token.putOntoBattlefield(game, source.getSourceId(), source.getControllerId());
            }
            if (card.getCardType().contains(CardType.LAND)) {
                player.getLibrary().remove(card.getId(), game);
                card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId());
            }
            if (!card.getCardType().contains(CardType.CREATURE) && !card.getCardType().contains(CardType.LAND)) {
                player.gainLife(2, game);
            }

            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Druidic Satchel", cards, game);
            return true;
        }
        return false;
    }

    @Override
    public DruidicSatchelEffect copy() {
        return new DruidicSatchelEffect(this);
    }
}
