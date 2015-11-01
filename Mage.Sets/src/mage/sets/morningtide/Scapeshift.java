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
package mage.sets.morningtide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class Scapeshift extends CardImpl {

    public Scapeshift(UUID ownerId) {
        super(ownerId, 136, "Scapeshift", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");
        this.expansionSetCode = "MOR";

        // Sacrifice any number of lands. Search your library for that many land cards, put them onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ScapeshiftEffect());
    }

    public Scapeshift(final Scapeshift card) {
        super(card);
    }

    @Override
    public Scapeshift copy() {
        return new Scapeshift(this);
    }
}

class ScapeshiftEffect extends OneShotEffect {

    public ScapeshiftEffect() {
        super(Outcome.Neutral);
        staticText = "Sacrifice any number of lands. Search your library for that many land cards, put them onto the battlefield tapped, then shuffle your library";
    }

    public ScapeshiftEffect(final ScapeshiftEffect effect) {
        super(effect);
    }

    @Override
    public ScapeshiftEffect copy() {
        return new ScapeshiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int amount = 0;
        TargetControlledPermanent sacrificeLand = new TargetControlledPermanent(0, Integer.MAX_VALUE, new FilterControlledLandPermanent("lands you control"), true);
        if (controller.chooseTarget(Outcome.Sacrifice, sacrificeLand, source, game)) {
            for (Object uuid : sacrificeLand.getTargets()) {
                Permanent land = game.getPermanent((UUID) uuid);
                if (land != null) {
                    land.sacrifice(source.getSourceId(), game);
                    amount++;
                }
            }
        }
        TargetCardInLibrary target = new TargetCardInLibrary(amount, new FilterLandCard("lands"));
        if (controller.searchLibrary(target, game)) {
            controller.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                    Zone.BATTLEFIELD, source, game, true, false, false, null);
            controller.shuffleLibrary(game);
            return true;
        }
        controller.shuffleLibrary(game);
        return false;
    }

}
