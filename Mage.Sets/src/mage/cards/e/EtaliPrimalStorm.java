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
package mage.cards.e;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author ciaccona007 & L_J
 */
public class EtaliPrimalStorm extends CardImpl {

    public EtaliPrimalStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Etali, Primal Storm attacks, exile the top card of each player's library, then you may cast any number of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new EtaliPrimalStormEffect(), false));

    }

    public EtaliPrimalStorm(final EtaliPrimalStorm card) {
        super(card);
    }

    @Override
    public EtaliPrimalStorm copy() {
        return new EtaliPrimalStorm(this);
    }
}

class EtaliPrimalStormEffect extends OneShotEffect {

    private final static FilterCard filter = new FilterCard("nonland cards");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public EtaliPrimalStormEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of each player's library, then you may cast any number of nonland cards exiled this way without paying their mana costs";
    }

    public EtaliPrimalStormEffect(final EtaliPrimalStormEffect effect) {
        super(effect);
    }

    @Override
    public EtaliPrimalStormEffect copy() {
        return new EtaliPrimalStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            // move cards from library to exile
            Set<Card> currentExiledCards = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (!player.getLibrary().getTopCards(game, 1).isEmpty()) {
                        Card topCard = player.getLibrary().getTopCards(game, 1).iterator().next();
                        if (filter.match(topCard, source.getSourceId(), source.getControllerId(), game)) {
                            currentExiledCards.add(topCard);
                        }
                        controller.moveCardsToExile(topCard, source, game, true, source.getSourceId(), sourceObject.getIdName());
                    }
                }
            }
            // cast the possible cards without paying the mana
            Cards cardsToCast = new CardsImpl();
            cardsToCast.addAll(currentExiledCards);
            boolean alreadyCast = false;
            while (!cardsToCast.isEmpty()) {
                if (!controller.chooseUse(Outcome.PlayForFree, "Cast a" + (alreadyCast ? "nother" : "" ) + " card exiled with " + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                    break;
                }
                TargetCard targetCard = new TargetCard(1, Zone.EXILED, new FilterCard("nonland card to cast for free"));
                if (controller.choose(Outcome.PlayForFree, cardsToCast, targetCard, game)) {
                    alreadyCast = true;
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
