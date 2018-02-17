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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth & L_J
 */
public class HellcarverDemon extends CardImpl {

    public HellcarverDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hellcarver Demon deals combat damage to a player, sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HellcarverDemonEffect(), false));
    }

    public HellcarverDemon(final HellcarverDemon card) {
        super(card);
    }

    @Override
    public HellcarverDemon copy() {
        return new HellcarverDemon(this);
    }
}

class HellcarverDemonEffect extends OneShotEffect {

    public HellcarverDemonEffect() {
        super(Outcome.PlayForFree);
        staticText = "sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.";
    }

    public HellcarverDemonEffect(final HellcarverDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
                if (!Objects.equals(permanent, sourceObject)) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
            if (!controller.getHand().isEmpty()) {
                int cardsInHand = controller.getHand().size();
                controller.discard(cardsInHand, false, source, game);
            }
            // move cards from library to exile
            Set<Card> currentExiledCards = new HashSet<>();
            currentExiledCards.addAll(controller.getLibrary().getTopCards(game, 6));
            controller.moveCardsToExile(currentExiledCards, source, game, true, source.getSourceId(), sourceObject.getIdName());
            
            // cast the possible cards without paying the mana
            Cards cardsToCast = new CardsImpl();
            cardsToCast.addAll(currentExiledCards);
            boolean alreadyCast = false;
            while (!cardsToCast.isEmpty()
                    && controller.canRespond()) {
                if (!controller.chooseUse(outcome, "Cast a" + (alreadyCast ? "nother" : "" ) + " card exiled with " + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
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

    @Override
    public HellcarverDemonEffect copy() {
        return new HellcarverDemonEffect(this);
    }
}
