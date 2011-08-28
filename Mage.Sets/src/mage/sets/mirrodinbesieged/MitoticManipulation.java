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
package mage.sets.mirrodinbesieged;

import java.util.HashSet;
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
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class MitoticManipulation extends CardImpl<MitoticManipulation> {

    public MitoticManipulation(UUID ownerId) {
        super(ownerId, 27, "Mitotic Manipulation", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");
        this.expansionSetCode = "MBS";

        this.color.setBlue(true);

        this.getSpellAbility().addEffect(new MitoticManipulationEffect());
    }

    public MitoticManipulation(final MitoticManipulation card) {
        super(card);
    }

    @Override
    public MitoticManipulation copy() {
        return new MitoticManipulation(this);
    }
}

class MitoticManipulationEffect extends OneShotEffect<MitoticManipulationEffect> {

    public MitoticManipulationEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Look at the top seven cards of your library. You may put one of those cards onto the battlefield if it has the same name as a permanent. Put the rest on the bottom of your library in any order";
    }

    public MitoticManipulationEffect(final MitoticManipulationEffect effect) {
        super(effect);
    }

    @Override
    public MitoticManipulationEffect copy() {
        return new MitoticManipulationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game);
        HashSet<String> permanentNames = new HashSet<String>();
        for (Permanent permanent : permanents) {
            permanentNames.add(permanent.getName());
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        Cards cardsFound = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 7);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
                if (permanentNames.contains(card.getName())) {
                    cardsFound.add(card);
                }
            }
        }
        player.lookAtCards("Mitotic Manipulation", cards, game);

        if (!cardsFound.isEmpty() && player.chooseUse(Outcome.PutCardInPlay, "Do you wish to put a card on the battlefield?", game)) {
            FilterCard filter = new FilterCard("card to put onto the battlefield");
            filter.getName().add(permanentNames);
            TargetCard target = new TargetCard(Zone.PICK, filter);

            if (player.choose(Outcome.PutCardInPlay, cardsFound, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId());
                }
            }
        }

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}
