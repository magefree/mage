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
package mage.cards.g;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author TheElk801
 */
public class GishathSunsAvatar extends CardImpl {

    public GishathSunsAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Dinosaur");
        this.subtype.add("Avatar");
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Gishath, Sun's Avatar deals combat damage to a player, reveal that many cards from the top of your library. Put any number of Dinosaur creature cards from among them onto the battlefield and the rest on the bottom of your library in a random order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GishathSunsAvatarEffect(), false, true));
    }

    public GishathSunsAvatar(final GishathSunsAvatar card) {
        super(card);
    }

    @Override
    public GishathSunsAvatar copy() {
        return new GishathSunsAvatar(this);
    }
}

class GishathSunsAvatarEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Dinosaur creature cards");

    static {
        filter.add(new SubtypePredicate(SubType.DINOSAUR));
    }

    GishathSunsAvatarEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal that many cards from the top of your library. Put any number of Dinosaur creature cards from among them onto the battlefield and the rest on the bottom of your library in a random order";
    }

    GishathSunsAvatarEffect(final GishathSunsAvatarEffect effect) {
        super(effect);
    }

    @Override
    public GishathSunsAvatarEffect copy() {
        return new GishathSunsAvatarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        int xValue = (Integer) getValue("damage");
        int numberCards = Math.min(controller.getLibrary().size(), xValue);
        for (int i = 0; i < numberCards; i++) {
            Card card = controller.getLibrary().removeFromTop(game);
            cards.add(card);
        }
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);
            TargetCard target1 = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
            target1.setRequired(false);
            controller.choose(Outcome.PutCardInPlay, cards, target1, game);
            Set<Card> toBattlefield = new LinkedHashSet<>();
            for (UUID cardId : target1.getTargets()) {
                Card card = cards.get(cardId, game);
                if (card != null) {
                    cards.remove(card);
                    toBattlefield.add(card);
                }
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, false, null);
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}
