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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class LiberatingCombustion extends CardImpl {

    public LiberatingCombustion(UUID ownerId) {
        super(ownerId, 267, "Liberating Combustion", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{R}");
        this.expansionSetCode = "KLD";

        // Liberating Combustion deals 6 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // You may search your library and/or graveyard for a card named Chandra, Pyrogenius, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new LiberatingCombustionEffect());
    }

    public LiberatingCombustion(final LiberatingCombustion card) {
        super(card);
    }

    @Override
    public LiberatingCombustion copy() {
        return new LiberatingCombustion(this);
    }
}

class LiberatingCombustionEffect extends OneShotEffect {

    public LiberatingCombustionEffect() {
        super(Outcome.Benefit);
        staticText = "You may search your library and/or graveyard for a card named Chandra, Pyrogenius, reveal it, and put it into your hand. If you search your library this way, shuffle it";
    }

    public LiberatingCombustionEffect(final LiberatingCombustionEffect effect) {
        super(effect);
    }

    @Override
    public LiberatingCombustionEffect copy() {
        return new LiberatingCombustionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null && controller.chooseUse(outcome, "Search your library and/or graveyard for a card named Chandra, Pyrogenius?", source, game)) {
            //Search your library and graveyard
            Cards allCards = new CardsImpl();
            boolean librarySearched = false;
            if (controller.chooseUse(outcome, "Search also your library?", source, game)) {
                librarySearched = true;
                allCards.addAll(controller.getLibrary().getCardList());
            }
            allCards.addAll(controller.getGraveyard());
            FilterCard filter = new FilterCard("a card named Chandra, Pyrogenius");
            filter.add(new NamePredicate("Chandra, Pyrogenius"));
            TargetCard target = new TargetCard(0, 1, Zone.ALL, new FilterCard());
            if (controller.choose(outcome, allCards, target, game)) {
                Card cardFound = game.getCard(target.getFirstTarget());
                if (cardFound != null) {
                    controller.revealCards(sourceObject.getIdName(), allCards, game);
                    controller.moveCards(cardFound, Zone.HAND, source, game);
                }
            }
            if (librarySearched) {
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
