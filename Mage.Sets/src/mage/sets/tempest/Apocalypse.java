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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author markedagain
 */
public class Apocalypse extends CardImpl {

    public Apocalypse(UUID ownerId) {
        super(ownerId, 162, "Apocalypse", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}{R}{R}");
        this.expansionSetCode = "TMP";

        // Exile all permanents. You discard your hand.
        this.getSpellAbility().addEffect(new ApocalypseExileAllPermanentsEffect());
        this.getSpellAbility().addEffect(new ApocalypseDiscardEffect());
    }

    public Apocalypse(final Apocalypse card) {
        super(card);
    }

    @Override
    public Apocalypse copy() {
        return new Apocalypse(this);
    }
}
class ApocalypseDiscardEffect extends OneShotEffect {

    public ApocalypseDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "Discard your hand";
    }

    public ApocalypseDiscardEffect(final ApocalypseDiscardEffect effect) {
        super(effect);
    }

    @Override
    public ApocalypseDiscardEffect copy() {
        return new ApocalypseDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getHand().getCards(game)) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
class ApocalypseExileAllPermanentsEffect extends OneShotEffect {

    public ApocalypseExileAllPermanentsEffect() {
        super(Outcome.Exile);
        staticText = "Exile all permanents";
    }

    public ApocalypseExileAllPermanentsEffect(final ApocalypseExileAllPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public ApocalypseExileAllPermanentsEffect copy() {
        return new ApocalypseExileAllPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                permanent.moveToExile(null, null, source.getSourceId(), game);
        }
        return true;
    }
}