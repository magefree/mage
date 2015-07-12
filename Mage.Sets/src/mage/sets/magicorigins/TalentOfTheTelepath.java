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
package mage.sets.magicorigins;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
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
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class TalentOfTheTelepath extends CardImpl {

    public TalentOfTheTelepath(UUID ownerId) {
        super(ownerId, 78, "Talent of the Telepath", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        this.expansionSetCode = "ORI";

        // Target opponent reveals the top seven cards of his or her library. You may cast an instant or sorcery card from among them without paying its mana cost. Then that player puts the rest into his or her graveyard.
        // <i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, you may cast up to two revealed instant and/or sorcery cards instead of one.
        getSpellAbility().addEffect(new TalentOfTheTelepathEffect());
        getSpellAbility().addTarget(new TargetOpponent());

    }

    public TalentOfTheTelepath(final TalentOfTheTelepath card) {
        super(card);
    }

    @Override
    public TalentOfTheTelepath copy() {
        return new TalentOfTheTelepath(this);
    }
}

class TalentOfTheTelepathEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public TalentOfTheTelepathEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target opponent reveals the top seven cards of his or her library. You may cast an instant or sorcery card from among them without paying its mana cost. Then that player puts the rest into his or her graveyard. "
                + "<BR><i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, you may cast up to two revealed instant and/or sorcery cards instead of one.";
    }

    public TalentOfTheTelepathEffect(final TalentOfTheTelepathEffect effect) {
        super(effect);
    }

    @Override
    public TalentOfTheTelepathEffect copy() {
        return new TalentOfTheTelepathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cardsToCast = new CardsImpl();
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (targetOpponent != null && sourceObject != null) {
            List<Card> allCards = targetOpponent.getLibrary().getTopCards(game, 7);
            Cards cards = new CardsImpl(Zone.LIBRARY, allCards);
            targetOpponent.revealCards(sourceObject.getIdName() + " - " + targetOpponent.getName() + "'s top library cards", cards, game);
            for (Card card : allCards) {
                if (filter.match(card, game)) {
                    cardsToCast.add(card);
                }
            }
            // cast an instant or sorcery for free
            if (cardsToCast.size() > 0) {
                int numberOfSpells = 1;
                if (SpellMasteryCondition.getInstance().apply(game, source)) {
                    numberOfSpells++;
                }
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {

                    TargetCard target = new TargetCard(Zone.LIBRARY, filter); // zone should be ignored here
                    target.setNotTarget(true);
                    while (numberOfSpells > 0
                            && cardsToCast.size() > 0
                            && controller.chooseUse(outcome, "Cast an instant or sorcery card from among them for free?", source, game)
                            && controller.choose(outcome, cardsToCast, target, game)) {
                        Card card = cardsToCast.get(target.getFirstTarget(), game);
                        if (card != null) {
                            controller.cast(card.getSpellAbility(), game, true);
                            numberOfSpells--;
                            cardsToCast.remove(card);
                            allCards.remove(card);
                        }
                        if (!controller.isInGame()) {
                            return false;
                        }
                        target.clearChosen();
                    }
                }
            }

            targetOpponent.moveCards(allCards, Zone.LIBRARY, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }
}
