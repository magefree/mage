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
package mage.cards.v;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public class VesselOfNascency extends CardImpl {

    public VesselOfNascency(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // {1}{G}, Sacrifice Vessel of Nascency: Reveal the top four cards of your library. You may put an artifact, creature, enchantment, land, or
        // planeswalker card from among them into your hand. Put the rest into your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VesselOfNascencyEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public VesselOfNascency(final VesselOfNascency card) {
        super(card);
    }

    @Override
    public VesselOfNascency copy() {
        return new VesselOfNascency(this);
    }
}

class VesselOfNascencyEffect extends OneShotEffect {

    private static final FilterCard filterPutInHand = new FilterCard("an artifact, creature, enchantment, land, or planeswalker card");

    static {
        filterPutInHand.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public VesselOfNascencyEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. You may put artifact, creature, enchantment, land, or planeswalker card from among "
                + "them into your hand. Put the rest into your graveyard";
    }

    public VesselOfNascencyEffect(final VesselOfNascencyEffect effect) {
        super(effect);
    }

    @Override
    public VesselOfNascencyEffect copy() {
        return new VesselOfNascencyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));
            boolean properCardFound = cards.count(filterPutInHand, source.getControllerId(), source.getSourceId(), game) > 0;
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, filterPutInHand);
                if (properCardFound
                        && controller.chooseUse(outcome, "Put an artifact, creature, enchantment, land, or planeswalker card into your hand?", source, game)
                        && controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCards(card, Zone.HAND, source, game);
                    }

                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
