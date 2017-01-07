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
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class SecretSalvage extends CardImpl {

    public SecretSalvage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Exile target nonland card from your graveyard. Search your library for any number of cards with the same name as that card,
        // reveal them, and put them into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new SecretSalvageEffect());
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterNonlandCard("nonland card from your graveyard")));
    }

    public SecretSalvage(final SecretSalvage card) {
        super(card);
    }

    @Override
    public SecretSalvage copy() {
        return new SecretSalvage(this);
    }
}

class SecretSalvageEffect extends OneShotEffect {

    public SecretSalvageEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile target nonland card from your graveyard. Search your library for any number of cards with the same name as that card, "
                + "reveal them, and put them into your hand. Then shuffle your library";
    }

    public SecretSalvageEffect(final SecretSalvageEffect effect) {
        super(effect);
    }

    @Override
    public SecretSalvageEffect copy() {
        return new SecretSalvageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
            if (targetCard != null) {
                controller.moveCards(targetCard, Zone.EXILED, source, game);
                FilterCard nameFilter = new FilterCard();
                nameFilter.add(new NamePredicate(targetCard.getName()));
                TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, nameFilter);
                if (controller.searchLibrary(target, game)) {
                    if (target.getTargets().size() > 0) {
                        Cards cards = new CardsImpl();
                        for (UUID cardId : target.getTargets()) {
                            Card card = controller.getLibrary().remove(cardId, game);
                            if (card != null) {
                                cards.add(card);
                            }
                        }
                        controller.revealCards(sourceObject.getIdName(), cards, game);
                        controller.moveCards(cards, Zone.HAND, source, game);
                    }
                    controller.shuffleLibrary(source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
