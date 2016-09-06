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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class StrategicPlanning extends CardImpl {

    public StrategicPlanning(UUID ownerId) {
        super(ownerId, 59, "Strategic Planning", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "C13";


        // Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new StrategicPlanningEffect());
    }

    public StrategicPlanning(final StrategicPlanning card) {
        super(card);
    }

    @Override
    public StrategicPlanning copy() {
        return new StrategicPlanning(this);
    }
}

class StrategicPlanningEffect extends OneShotEffect {

    public StrategicPlanningEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard";
    }

    public StrategicPlanningEffect(final StrategicPlanningEffect effect) {
        super(effect);
    }

    @Override
    public StrategicPlanningEffect copy() {
        return new StrategicPlanningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (cards.size() > 0) {
                controller.lookAtCards("Strategic Planning", cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put in your hand"));
                if (controller.choose(Outcome.Benefit, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        cards.remove(card);
                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
