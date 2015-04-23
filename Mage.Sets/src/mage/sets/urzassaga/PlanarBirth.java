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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class PlanarBirth extends CardImpl {

    public PlanarBirth(UUID ownerId) {
        super(ownerId, 31, "Planar Birth", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{W}");
        this.expansionSetCode = "USG";

        // Return all basic land cards from all graveyards to the battlefield tapped under their owners' control.
        this.getSpellAbility().addEffect(new PlanarBirthEffect());
    }

    public PlanarBirth(final PlanarBirth card) {
        super(card);
    }

    @Override
    public PlanarBirth copy() {
        return new PlanarBirth(this);
    }
}

class PlanarBirthEffect extends OneShotEffect {
    
    PlanarBirthEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Return all basic land cards from all graveyards to the battlefield tapped under their owners' control";
    }
    
    PlanarBirthEffect(final PlanarBirthEffect effect) {
        super(effect);
    }
    
    @Override
    public PlanarBirthEffect copy() {
        return new PlanarBirthEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card : player.getGraveyard().getCards(new FilterBasicLandCard(), source.getSourceId(), controller.getId(), game)) {
                        player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId(), true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
