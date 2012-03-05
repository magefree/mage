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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class CaravanVigil extends CardImpl<CaravanVigil> {

    public CaravanVigil(UUID ownerId) {
        super(ownerId, 173, "Caravan Vigil", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        // Morbid - You may put that card onto the battlefield instead of putting it into your hand if a creature died this turn.
        this.getSpellAbility().addEffect(new CaravanVigilEffect());
    }

    public CaravanVigil(final CaravanVigil card) {
        super(card);
    }

    @Override
    public CaravanVigil copy() {
        return new CaravanVigil(this);
    }
}

class CaravanVigilEffect extends OneShotEffect<CaravanVigilEffect> {

    public CaravanVigilEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.\n"
                + "Morbid - You may put that card onto the battlefield instead of putting it into your hand if a creature died this turn";
    }

    public CaravanVigilEffect(final CaravanVigilEffect effect) {
        super(effect);
    }

    @Override
    public CaravanVigilEffect copy() {
        return new CaravanVigilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
            if (player.searchLibrary(target, game)) {
                Cards cards = new CardsImpl();
                Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    cards.add(card);
                    if (MorbidCondition.getInstance().apply(game, source)
                            && player.chooseUse(Outcome.PutLandInPlay, "Do you wish to put the card onto the battlefield instead?", game)) {
                        card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                    } else {
                        card.moveToZone(Zone.HAND, source.getId(), game, false);
                    }
                }
                player.revealCards("Caravan Vigil", cards, game);
            }
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
