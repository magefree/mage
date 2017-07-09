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
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author ciaccona007
 */
public class HazoretsUndyingFury extends CardImpl {

    public HazoretsUndyingFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        //Shuffle your library, then exile the top four cards.
        //You may cast any number of nonland cards with converted mana
        //cost 5 or less from among them without paying their mana costs.
        getSpellAbility().addEffect(new HazoretsUndyingFuryEffect());

        //Land you control don't untap during your next untap step.
        this.getSpellAbility().addEffect(new DontUntapInControllersUntapStepAllEffect(
                Duration.UntilYourNextTurn, TargetController.YOU, new FilterControlledLandPermanent("Lands you control"))
                .setText("Lands you control don't untap during your next untap phase"));
    }

    public HazoretsUndyingFury(final HazoretsUndyingFury card) {
        super(card);
    }

    @Override
    public HazoretsUndyingFury copy() {
        return new HazoretsUndyingFury(this);
    }
}

class HazoretsUndyingFuryEffect extends OneShotEffect {

    private final static FilterCard filter = new FilterCard("nonland cards with converted mana cost 5 or less");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 6));
    }

    public HazoretsUndyingFuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle your library, then exile the top four cards. You may cast any number of nonland cards with converted mana cost 5 or less from among them without paying their mana costs";
    }

    public HazoretsUndyingFuryEffect(final HazoretsUndyingFuryEffect effect) {
        super(effect);
    }

    @Override
    public HazoretsUndyingFuryEffect copy() {
        return new HazoretsUndyingFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            controller.shuffleLibrary(source, game);
            // move cards from library to exile
            controller.moveCardsToExile(controller.getLibrary().getTopCards(game, 4), source, game, true, source.getSourceId(), sourceObject.getIdName());
            // cast the possible cards without paying the mana
            ExileZone hazoretsUndyingFuryExileZone = game.getExile().getExileZone(source.getSourceId());
            Cards cardsToCast = new CardsImpl();
            if (hazoretsUndyingFuryExileZone == null) {
                return true;
            }
            cardsToCast.addAll(hazoretsUndyingFuryExileZone.getCards(filter, source.getSourceId(), source.getControllerId(), game));
            while (!cardsToCast.isEmpty()) {
                if (!controller.chooseUse(Outcome.PlayForFree, "Cast (another) a card exiled with " + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                    break;
                }
                TargetCard targetCard = new TargetCard(1, Zone.EXILED, new FilterCard("nonland card to cast for free"));
                if (controller.choose(Outcome.PlayForFree, cardsToCast, targetCard, game)) {
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        if (controller.cast(card.getSpellAbility(), game, true)) {
                            cardsToCast.remove(card);
                        } else {
                            game.informPlayer(controller, "You're not able to cast " + card.getIdName() + " or you canceled the casting.");
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
