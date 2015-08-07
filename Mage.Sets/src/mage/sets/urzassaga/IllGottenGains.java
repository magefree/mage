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
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public class IllGottenGains extends CardImpl {

    public IllGottenGains(UUID ownerId) {
        super(ownerId, 138, "Ill-Gotten Gains", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "USG";

        // Exile Ill-Gotten Gains.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

        // Each player discards his or her hand,
        this.getSpellAbility().addEffect(new DiscardHandAllEffect());

        //then returns up to three cards from his or her graveyard to his or her hand.
        this.getSpellAbility().addEffect(new IllGottenGainsEffect());
    }

    public IllGottenGains(final IllGottenGains card) {
        super(card);
    }

    @Override
    public IllGottenGains copy() {
        return new IllGottenGains(this);
    }
}

class IllGottenGainsEffect extends OneShotEffect {

    IllGottenGainsEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then returns up to three cards from his or her graveyard to his or her hand.";
    }

    IllGottenGainsEffect(final IllGottenGainsEffect effect) {
        super(effect);
    }

    @Override
    public IllGottenGainsEffect copy() {
        return new IllGottenGainsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Target target = new TargetCardInYourGraveyard(0, 3, new FilterCard());
                    if (target.choose(Outcome.ReturnToHand, player.getId(), source.getSourceId(), game)) {
                        controller.moveCards(new CardsImpl(target.getTargets()), null, Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
