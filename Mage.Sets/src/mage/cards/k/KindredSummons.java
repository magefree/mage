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
package mage.cards.k;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
/**
 *
 * @author Saga
 */
public class KindredSummons extends CardImpl {

    public KindredSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{G}{G}");

        // Choose a creature type. Reveal cards from the top of your library until you reveal X creature cards of the chosen type, 
        // where X is the number of creatures you control of that type. Put those cards onto the battlefield, 
        // then shuffle the rest of the revealed cards into your library.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.PutCreatureInPlay));
        this.getSpellAbility().addEffect(new KindredSummonsEffect());
    }

    public KindredSummons(final KindredSummons card) {
        super(card);
    }

    @Override
    public KindredSummons copy() {
        return new KindredSummons(this);
    }
}

class KindredSummonsEffect extends OneShotEffect {

    public KindredSummonsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal cards from the top of your library until you reveal X creature cards of the chosen type, " +
                "where X is the number of creatures you control of that type. Put those cards onto the battlefield, "
                + "then shuffle the rest of the revealed cards into your library";
    }

    public KindredSummonsEffect(final KindredSummonsEffect effect) {
        super(effect);
    }

    @Override
    public KindredSummonsEffect copy() {
        return new KindredSummonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            String creatureType = game.getState().getValue(sourceObject.getId() + "_type").toString();
            FilterControlledCreaturePermanent filterPermanent = new FilterControlledCreaturePermanent("creature you control of the chosen type");
            filterPermanent.add(new SubtypePredicate(SubType.byDescription(creatureType)));
            int numberOfCards = game.getBattlefield().countAll(filterPermanent, source.getControllerId(), game);
            Cards revealed = new CardsImpl();
            Set<Card> chosenSubtypeCreatureCards = new LinkedHashSet<>();
            Cards otherCards = new CardsImpl();
            FilterCreatureCard filterCard = new FilterCreatureCard("creature card of the chosen type");
            filterCard.add(new SubtypePredicate(SubType.byDescription(creatureType)));
            while (chosenSubtypeCreatureCards.size() < numberOfCards && controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().removeFromTop(game);
                revealed.add(card);
                if (card != null && filterCard.match(card, game)) {
                    chosenSubtypeCreatureCards.add(card);
                } else {
                    otherCards.add(card);
                }
            }
            controller.revealCards(sourceObject.getIdName(), revealed, game);
            controller.moveCards(chosenSubtypeCreatureCards, Zone.BATTLEFIELD, source, game, false, false, false, null);
            controller.putCardsOnTopOfLibrary(otherCards, game, source, false);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
