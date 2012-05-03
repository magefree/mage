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
package mage.sets.avacynrestored;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class PrimalSurge extends CardImpl<PrimalSurge> {

    public PrimalSurge(UUID ownerId) {
        super(ownerId, 189, "Primal Surge", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{8}{G}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // Exile the top card of your library. If it's a permanent card, you may put it onto the battlefield. If you do, repeat this process.
        this.getSpellAbility().addEffect(new PrimalSurgeEffect());
    }

    public PrimalSurge(final PrimalSurge card) {
        super(card);
    }

    @Override
    public PrimalSurge copy() {
        return new PrimalSurge(this);
    }
}

class PrimalSurgeEffect extends OneShotEffect<PrimalSurgeEffect> {

    public PrimalSurgeEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exile the top card of your library. If it's a permanent card, you may put it onto the battlefield. If you do, repeat this process";
    }

    public PrimalSurgeEffect(final PrimalSurgeEffect effect) {
        super(effect);
    }

    @Override
    public PrimalSurgeEffect copy() {
        return new PrimalSurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean repeat;
        do {
            repeat = false;
            if (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToExile(null, "", source.getId(), game);
                    List<CardType> cardType = card.getCardType();
                    if ((cardType.contains(CardType.ARTIFACT) || cardType.contains(CardType.CREATURE)
                            || cardType.contains(CardType.ENCHANTMENT) || cardType.contains(CardType.LAND)
                            || cardType.contains(CardType.PLANESWALKER))
                            && player.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield?", game)) {
                        card.moveToZone(Zone.BATTLEFIELD, source.getId(), game, false);

                        Permanent permanent = game.getPermanent(card.getId());
                        if (permanent == null) {
                            permanent = (Permanent) game.getLastKnownInformation(card.getId(), Zone.BATTLEFIELD);
                        }
                        if (permanent != null) {
                            repeat = true;
                        }
                    }
                }
            }
        } while (repeat);

        return true;
    }
}
