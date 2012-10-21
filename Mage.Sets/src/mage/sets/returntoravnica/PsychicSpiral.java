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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;


/**
 *
 * @author LevelX2
 */
public class PsychicSpiral extends CardImpl<PsychicSpiral> {

    public PsychicSpiral(UUID ownerId) {
        super(ownerId, 47, "Psychic Spiral", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{U}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);

        // Shuffle all cards from your graveyard into your library. Target player puts that many cards from the top of his or her library into his or her graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PsychicSpiralEffect());
    }

    public PsychicSpiral(final PsychicSpiral card) {
        super(card);
    }

    @Override
    public PsychicSpiral copy() {
        return new PsychicSpiral(this);
    }
}

class PsychicSpiralEffect extends OneShotEffect<PsychicSpiralEffect> {

    public PsychicSpiralEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "Shuffle all cards from your graveyard into your library. Target player puts that many cards from the top of his or her library into his or her graveyard";
    }

    public PsychicSpiralEffect(final PsychicSpiralEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int cardsInGraveyard = player.getGraveyard().size();
            player.getLibrary().addAll(player.getGraveyard().getCards(game), game);
            player.getGraveyard().clear();
            player.shuffleLibrary(game);

            if (cardsInGraveyard > 0) {
                Player targetPlayer = game.getPlayer(source.getFirstTarget());
                if (targetPlayer != null) {
                    for (int i = 0; i<cardsInGraveyard ; i++) {
                        if (!targetPlayer.getLibrary().getCardList().isEmpty()) {
                            Card card = targetPlayer.getLibrary().removeFromTop(game);
                            if (card != null) {
                                card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PsychicSpiralEffect copy() {
        return new PsychicSpiralEffect(this);
    }
}