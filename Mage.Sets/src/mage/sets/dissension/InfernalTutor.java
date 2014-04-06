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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class InfernalTutor extends CardImpl<InfernalTutor> {

    public InfernalTutor(UUID ownerId) {
        super(ownerId, 46, "Infernal Tutor", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "DIS";

        this.color.setBlack(true);

        // Reveal a card from your hand. Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new InfernalTutorEffect());
        // Hellbent - If you have no cards in hand, instead search your library for a card, put it into your hand, then shuffle your library.
        Effect effect = new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterCard()), false, true),
                HellbentCondition.getInstance(),
                "<br/><br/><i>Hellbent</i> - If you have no cards in hand, instead search your library for a card, put it into your hand, then shuffle your library");
        this.getSpellAbility().addEffect(effect);
        
    }

    public InfernalTutor(final InfernalTutor card) {
        super(card);
    }

    @Override
    public InfernalTutor copy() {
        return new InfernalTutor(this);
    }
}

class InfernalTutorEffect extends OneShotEffect<InfernalTutorEffect> {

    public InfernalTutorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal a card from your hand. Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle your library";
    }

    public InfernalTutorEffect(final InfernalTutorEffect effect) {
        super(effect);
    }

    @Override
    public InfernalTutorEffect copy() {
        return new InfernalTutorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null) {
            if (controller.getHand().size() > 0) {
                Card cardToReveal = null;
                if (controller.getHand().size() > 1) {
                    Target target = new TargetCardInHand(new FilterCard());
                    target.setRequired(true);
                    target.setNotTarget(true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToReveal = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardToReveal = controller.getHand().getRandom(game);
                }
                if (cardToReveal != null) {
                    controller.revealCards(sourceCard.getName(), new CardsImpl(cardToReveal), game);
                    FilterCard filterCard = new FilterCard(new StringBuilder("card named ").append(cardToReveal.getName()).toString());
                    return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true, true).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
