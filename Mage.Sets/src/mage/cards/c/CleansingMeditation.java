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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class CleansingMeditation extends CardImpl {

    public CleansingMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}");
        // Destroy all enchantments.
        // Threshold - If seven or more cards are in your graveyard, instead destroy all enchantments, then return all cards in your graveyard destroyed this way to the battlefield
        this.getSpellAbility().addEffect(new CleansingMeditationEffect());
    }

    public CleansingMeditation(final CleansingMeditation card) {
        super(card);
    }

    @Override
    public CleansingMeditation copy() {
        return new CleansingMeditation(this);
    }
}

class CleansingMeditationEffect extends OneShotEffect {

    public CleansingMeditationEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all enchantments.<br>Threshold - If seven or more cards are in your graveyard, instead destroy all enchantments, then return all cards in your graveyard destroyed this way to the battlefield.";
    }

    public CleansingMeditationEffect(final CleansingMeditationEffect effect) {
        super(effect);
    }

    @Override
    public CleansingMeditationEffect copy() {
        return new CleansingMeditationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cardsToBattlefield = new CardsImpl();

        // Threshold?
        boolean threshold = false;
        DynamicValue c = new CardsInControllerGraveyardCount();
        int numCards = c.calculate(game, source, this);
        if (numCards >= 7) {
            threshold = true;
        }

        Player controller = game.getPlayer(source.getControllerId());

        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterEnchantmentPermanent(), source.getControllerId(), source.getSourceId(), game)) {
            if (permanent != null && permanent.destroy(source.getSourceId(), game, false)) {
                if (threshold && controller != null && permanent.getOwnerId().equals(controller.getId())) {
                    cardsToBattlefield.add(permanent);
                }
            }
        }

        if (threshold && controller != null) {
            controller.moveCards(cardsToBattlefield, Zone.BATTLEFIELD, source, game);
        }

        return true;
    }
}
