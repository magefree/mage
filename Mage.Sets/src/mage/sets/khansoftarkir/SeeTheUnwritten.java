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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class SeeTheUnwritten extends CardImpl {

    public SeeTheUnwritten(UUID ownerId) {
        super(ownerId, 149, "See the Unwritten", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");
        this.expansionSetCode = "KTK";

        // Reveal the top eight cards of your library. You may put a creature card from among them onto the battlefield. Put the rest into your graveyard.
        // <i>Ferocious</i> - If you control a creature with power 4 or greater, you may put two creature cards onto the battlefield instead of one.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SeeTheUnwrittenEffect(1),
                new SeeTheUnwrittenEffect(2),
                new InvertCondition(FerociousCondition.getInstance()),
                "Reveal the top eight cards of your library. You may put a creature card from among them onto the battlefield. Put the rest into your graveyard."
                + "<br/><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, you may put two creature cards onto the battlefield instead of one"));
    }

    public SeeTheUnwritten(final SeeTheUnwritten card) {
        super(card);
    }

    @Override
    public SeeTheUnwritten copy() {
        return new SeeTheUnwritten(this);
    }
}

class SeeTheUnwrittenEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card");

    private final int numberOfCardsToPutIntoPlay;

    public SeeTheUnwrittenEffect(int numberOfCardsToPutIntoPlay) {
        super(Outcome.DrawCard);
        this.numberOfCardsToPutIntoPlay = numberOfCardsToPutIntoPlay;
        this.staticText = "Reveal the top eight cards of your library. You may put "
                + (numberOfCardsToPutIntoPlay == 1 ? "a creature card" : "two creature cards")
                + " from among them onto the battlefield. Put the rest into your graveyard";
    }

    public SeeTheUnwrittenEffect(final SeeTheUnwrittenEffect effect) {
        super(effect);
        this.numberOfCardsToPutIntoPlay = effect.numberOfCardsToPutIntoPlay;
    }

    @Override
    public SeeTheUnwrittenEffect copy() {
        return new SeeTheUnwrittenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl(Zone.LIBRARY);

            int creatureCardsFound = 0;
            int count = Math.min(controller.getLibrary().size(), 8);
            for (int i = 0; i < count; i++) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if (filter.match(card, source.getSourceId(), source.getControllerId(), game)) {
                        creatureCardsFound++;
                    }
                }
            }

            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                if (creatureCardsFound > 0 && controller.chooseUse(outcome, "Put creature(s) into play?", source, game)) {
                    int cardsToChoose = Math.min(numberOfCardsToPutIntoPlay, creatureCardsFound);
                    TargetCard target = new TargetCard(cardsToChoose, cardsToChoose, Zone.LIBRARY, filter);
                    if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                        for (UUID creatureId : target.getTargets()) {
                            Card card = game.getCard(creatureId);
                            if (card != null) {
                                cards.remove(card);
                                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            }
                        }

                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }

}
