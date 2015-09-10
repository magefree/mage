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

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 *
 */
public class MomirVigSimicVisionary extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a green creature spell");
    private static final FilterSpell filter2 = new FilterSpell("a blue creature spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(new CardTypePredicate(CardType.CREATURE));
    }

    public MomirVigSimicVisionary(UUID ownerId) {
        super(ownerId, 118, "Momir Vig, Simic Visionary", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");
        this.expansionSetCode = "DIS";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a green creature spell, you may search your library for a creature card and reveal it. If you do, shuffle your library and put that card on top of it.
        Effect effect = new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(new FilterCreatureCard()), true, true);
        effect.setText("you may search your library for a creature card and reveal it. If you do, shuffle your library and put that card on top of it");
        this.addAbility(new SpellCastControllerTriggeredAbility(effect, filter, true));

        // Whenever you cast a blue creature spell, reveal the top card of your library. If it's a creature card, put that card into your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(new MomirVigSimicVisionaryEffect(), filter2, false));

    }

    public MomirVigSimicVisionary(final MomirVigSimicVisionary card) {
        super(card);
    }

    @Override
    public MomirVigSimicVisionary copy() {
        return new MomirVigSimicVisionary(this);
    }
}

class MomirVigSimicVisionaryEffect extends OneShotEffect {

    public MomirVigSimicVisionaryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library. If it's a creature card, put that card into your hand";
    }

    public MomirVigSimicVisionaryEffect(final MomirVigSimicVisionaryEffect effect) {
        super(effect);
    }

    @Override
    public MomirVigSimicVisionaryEffect copy() {
        return new MomirVigSimicVisionaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 1));
        controller.revealCards(sourceObject.getIdName(), cards, game);

        Set<Card> cardsList = cards.getCards(game);
        Cards cardsToHand = new CardsImpl();
        for (Card card : cardsList) {
            if (card.getCardType().contains(CardType.CREATURE)) {
                cardsToHand.add(card);
                cards.remove(card);
            }
        }
        controller.moveCards(cardsToHand, null, Zone.HAND, source, game);
        return true;
    }
}
