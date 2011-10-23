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
package mage.sets.magic2012;

import java.util.ArrayList;
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
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class DoublingChant extends CardImpl<DoublingChant> {

    public DoublingChant(UUID ownerId) {
        super(ownerId, 170, "Doubling Chant", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{G}");
        this.expansionSetCode = "M12";

        this.color.setGreen(true);

        this.getSpellAbility().addEffect(new DoublingChantEffect());
    }

    public DoublingChant(final DoublingChant card) {
        super(card);
    }

    @Override
    public DoublingChant copy() {
        return new DoublingChant(this);
    }
}

class DoublingChantEffect extends OneShotEffect<DoublingChantEffect> {

    public DoublingChantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "For each creature you control, you may search your library for a creature card with the same name as that creature. Put those cards onto the battlefield, then shuffle your library";
    }

    public DoublingChantEffect(final DoublingChantEffect effect) {
        super(effect);
    }

    @Override
    public DoublingChantEffect copy() {
        return new DoublingChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosenCards = new ArrayList<Card>();
        List<String> namesFiltered = new ArrayList<String>();

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId());
        for (Permanent creature : creatures) {
            final String creatureName = creature.getName();
            if (!namesFiltered.contains(creatureName)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Search for ").append(creatureName).append(" in your library?");

                if (player.chooseUse(Outcome.PutCreatureInPlay, sb.toString(), game)) {
                    FilterCreatureCard filter = new FilterCreatureCard("creature card named" + creatureName);
                    filter.getName().add(creatureName);
                    TargetCardInLibrary target = new TargetCardInLibrary(filter);

                    if (player.searchLibrary(target, game)) {
                        Card card = player.getLibrary().remove(target.getFirstTarget(), game);
                        if (card != null) {
                            chosenCards.add(card);
                        }
                    }
                } else {
                    namesFiltered.add(creatureName);
                }
            }
        }

        for (Card card : chosenCards) {
            card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId());
        }
        player.shuffleLibrary(game);
        return true;
    }
}
