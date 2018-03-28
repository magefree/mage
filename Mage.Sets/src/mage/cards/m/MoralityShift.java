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
package mage.cards.m;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class MoralityShift extends CardImpl {

    public MoralityShift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");
        

        // Exchange your graveyard and library. Then shuffle your library.
        this.getSpellAbility().addEffect(new MoralityShiftEffect());
        
    }

    public MoralityShift(final MoralityShift card) {
        super(card);
    }

    @Override
    public MoralityShift copy() {
        return new MoralityShift(this);
    }
}

class MoralityShiftEffect extends OneShotEffect {

   MoralityShiftEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Exchange your graveyard and library. Then shuffle your library.";
    }

    MoralityShiftEffect(MoralityShiftEffect effect) {
        super(effect);
    }

    @Override
    public MoralityShiftEffect copy() {
        return new MoralityShiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> copyLibrary = new HashSet<>();
            List<Card> listCopyLibrary = controller.getLibrary().getCards(game);
            listCopyLibrary.forEach((card) -> {
                copyLibrary.add(card);
            });
            Set<Card> copyGraveyard = controller.getGraveyard().getCards(game);
            controller.getLibrary().clear();
            controller.getGraveyard().clear();
            controller.moveCards(copyLibrary, Zone.GRAVEYARD, source, game);
            controller.moveCards(copyGraveyard, Zone.LIBRARY, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
