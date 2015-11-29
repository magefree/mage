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
package mage.sets.masterseditionii;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;
/**
 *
 * @author LoneFox
 */
public class ForgottenLore extends CardImpl {

    public ForgottenLore(UUID ownerId) {
        super(ownerId, 164, "Forgotten Lore", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{G}");
        this.expansionSetCode = "ME2";

        // Target opponent chooses a card in your graveyard. You may pay {G}. If you do, repeat this process except that opponent can't choose a card already chosen for Forgotten Lore. Then put the last chosen card into your hand.
        this.getSpellAbility().addEffect(new ForgottenLoreEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public ForgottenLore(final ForgottenLore card) {
        super(card);
    }

    @Override
    public ForgottenLore copy() {
        return new ForgottenLore(this);
    }
}

class ForgottenLoreEffect extends OneShotEffect {

    public ForgottenLoreEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent chooses a card in your graveyard. You may pay {G}. If you do, repeat this process except that opponent can't choose a card already chosen for {this}. Then put the last chosen card into your hand.";
    }

    public ForgottenLoreEffect(final ForgottenLoreEffect effect) {
        super(effect);
    }

    @Override
    public ForgottenLoreEffect copy() {
        return new ForgottenLoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if(you != null && opponent != null)
        {
            FilterCard filter = new FilterCard();
            filter.add(new OwnerIdPredicate(you.getId()));
            Cost cost = new ManaCostsImpl("{G}");
            TargetCardInGraveyard chosenCard;
            Card card = null;
            boolean done = false;
            do {
                chosenCard = new TargetCardInGraveyard(filter);
                chosenCard.setNotTarget(true);
                if(chosenCard.canChoose(opponent.getId(), game)) {
                    opponent.chooseTarget(Outcome.ReturnToHand, chosenCard, source, game);
                    card = game.getCard(chosenCard.getFirstTarget());
                    filter.add(Predicates.not(new CardIdPredicate(card.getId())));
                    game.informPlayers("Forgotten Lore: " + opponent.getLogName() + " has chosen " + card.getLogName());
                }
                else {
                    done = true;
                }

                if(!done) {
                    if(cost.canPay(source, source.getSourceId(), you.getId(), game) && you.chooseUse(Outcome.Benefit, "Pay {G} to choose a different card ?", source, game)) {
                        cost.clearPaid();
                        if(!cost.pay(source, game, source.getSourceId(), you.getId(), false)) {
                            done = true;
                        }
                    }
                    else {
                        done = true;
                    }
                }

            } while(!done);

            if(card != null) {
                Cards cardsToHand = new CardsImpl();
                cardsToHand.add(card);
                you.moveCards(cardsToHand, Zone.GRAVEYARD, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
