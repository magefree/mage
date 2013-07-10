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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class IntoTheWilds extends CardImpl<IntoTheWilds> {

    public IntoTheWilds(UUID ownerId) {
        super(ownerId, 180, "Into the Wilds", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "M14";

        this.color.setGreen(true);

        // At the beginning of your upkeep, look at the top card of your library. If it's a land card, you may put it onto the battlefield. 
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new IntoTheWildsEffect(), TargetController.YOU, false));

    }

    public IntoTheWilds(final IntoTheWilds card) {
        super(card);
    }

    @Override
    public IntoTheWilds copy() {
        return new IntoTheWilds(this);
    }
}

class IntoTheWildsEffect extends OneShotEffect<IntoTheWildsEffect> {

    private final static FilterLandCard filter = new FilterLandCard();

    public IntoTheWildsEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "look at the top card of your library. If it's a land card, you may put it onto the battlefield";
    }

    public IntoTheWildsEffect(final IntoTheWildsEffect effect) {
        super(effect);
    }

    @Override
    public IntoTheWildsEffect copy() {
        return new IntoTheWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Card card = player.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl();
            cards.add(card);
            player.lookAtCards("Into the Wilds", cards, game);
            if (filter.match(card, game)) {
                String message = "Put " + card.getName() + " onto the battlefield?";
                if (player.chooseUse(outcome, message, game)) {
                    return card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId(), false);
                }
            }
        }
        return true;
    }
}
