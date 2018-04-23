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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author TheElk801
 */
public class LatNamsLegacy extends CardImpl {

    public LatNamsLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Shuffle a card from your hand into your library. If you do, draw two cards at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new LatNamsLegacyEffect());
    }

    public LatNamsLegacy(final LatNamsLegacy card) {
        super(card);
    }

    @Override
    public LatNamsLegacy copy() {
        return new LatNamsLegacy(this);
    }
}

class LatNamsLegacyEffect extends OneShotEffect {

    public LatNamsLegacyEffect() {
        super(Outcome.DrawCard);
        staticText = "Shuffle a card from your hand into your library. If you do, draw two cards at the beginning of the next turn's upkeep";
    }

    public LatNamsLegacyEffect(LatNamsLegacyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (!controller.getHand().isEmpty()) {
            TargetCard target = new TargetCard(Zone.HAND, new FilterCard("card to shuffle into your library"));
            controller.choose(Outcome.Detriment, controller.getHand(), target, game);
            Card card = controller.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                boolean successful = controller.moveCards(card, Zone.LIBRARY, source, game);
                controller.shuffleLibrary(source, game);
                if (successful) {
                    new CreateDelayedTriggeredAbilityEffect(
                            new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                                    new DrawCardSourceControllerEffect(2)
                            ), false
                    ).apply(game, source);
                }
            }
            return true;

        }
        return true;
    }

    @Override
    public LatNamsLegacyEffect copy() {
        return new LatNamsLegacyEffect(this);
    }

}
