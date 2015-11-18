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
package mage.sets.commander2015;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class MizzixsMastery extends CardImpl {

    public MizzixsMastery(UUID ownerId) {
        super(ownerId, 29, "Mizzix's Mastery", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "C15";

        // Exile target card that's an instant or sorcery from your graveyard. For each card exiled this way, copy it, and you may cast the copy without paying its mana cost. Exile Mizzix's Mastery.
        this.getSpellAbility().addEffect(new MizzixsMasteryEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard("card that's an instant or sorcery from your graveyard")));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

        // Overload {5}{R}{R}{R}
        Ability ability = new OverloadAbility(this, new MizzixsMasteryOverloadEffect(), new ManaCostsImpl("{5}{R}{R}{R}"), TimingRule.SORCERY);
        ability.addEffect(ExileSpellEffect.getInstance());
        this.addAbility(ability);
    }

    public MizzixsMastery(final MizzixsMastery card) {
        super(card);
    }

    @Override
    public MizzixsMastery copy() {
        return new MizzixsMastery(this);
    }
}

class MizzixsMasteryEffect extends OneShotEffect {

    public MizzixsMasteryEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile target card that's an instant or sorcery from your graveyard. For each card exiled this way, copy it, and you may cast the copy without paying its mana cost. Exile {this}";
    }

    public MizzixsMasteryEffect(final MizzixsMasteryEffect effect) {
        super(effect);
    }

    @Override
    public MizzixsMasteryEffect copy() {
        return new MizzixsMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                if (controller.moveCards(card, Zone.EXILED, source, game)) {
                    Card cardCopy = game.copyCard(card, source, source.getControllerId());
                    if (cardCopy.getSpellAbility().canChooseTarget(game)
                            && controller.chooseUse(outcome, "Cast copy of " + card.getName() + " without paying its mana cost?", source, game)) {
                        controller.cast(cardCopy.getSpellAbility(), game, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class MizzixsMasteryOverloadEffect extends OneShotEffect {

    public MizzixsMasteryOverloadEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile each card that's an instant or sorcery from your graveyard. For each card exiled this way, copy it, and you may cast the copy without paying its mana cost. Exile {this}";
    }

    public MizzixsMasteryOverloadEffect(final MizzixsMasteryOverloadEffect effect) {
        super(effect);
    }

    @Override
    public MizzixsMasteryOverloadEffect copy() {
        return new MizzixsMasteryOverloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToExile = controller.getGraveyard().getCards(new FilterInstantOrSorceryCard(), source.getId(), source.getControllerId(), game);
            if (!cardsToExile.isEmpty()) {
                if (controller.moveCards(cardsToExile, Zone.EXILED, source, game)) {
                    Cards copiedCards = new CardsImpl();
                    for (Card card : cardsToExile) {
                        copiedCards.add(game.copyCard(card, source, source.getControllerId()));
                    }
                    boolean continueCasting = true;
                    while (continueCasting) {
                        TargetCard targetCard = new TargetCard(0, 1, Zone.EXILED, new FilterCard("copied card to cast without paying its mana cost?"));
                        targetCard.setNotTarget(true);
                        if (controller.choose(outcome, copiedCards, targetCard, game)) {
                            Card selectedCard = game.getCard(targetCard.getFirstTarget());
                            if (selectedCard != null && selectedCard.getSpellAbility().canChooseTarget(game)) {
                                if (controller.cast(selectedCard.getSpellAbility(), game, true)) {
                                    copiedCards.remove(selectedCard);
                                }
                            }
                        }
                        continueCasting = copiedCards.size() > 0
                                && controller.chooseUse(outcome, "Cast one of the copied cards without paying its mana cost?", source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
