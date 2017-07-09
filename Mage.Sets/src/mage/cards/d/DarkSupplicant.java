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
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class DarkSupplicant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("three Clerics you control");

    static {
        filter.add(new SubtypePredicate(SubType.CLERIC));
    }

    public DarkSupplicant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice three Clerics: Search your graveyard, hand, and/or library for a card named Scion of Darkness and put it onto the battlefield. If you search your library this way, shuffle it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DarkSupplicantEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, filter, true)));
        this.addAbility(ability);
    }

    public DarkSupplicant(final DarkSupplicant card) {
        super(card);
    }

    @Override
    public DarkSupplicant copy() {
        return new DarkSupplicant(this);
    }
}

class DarkSupplicantEffect extends OneShotEffect {

    public DarkSupplicantEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your graveyard, hand, and/or library for a card named Scion of Darkness and put it onto the battlefield. If you search your library this way, shuffle it";
    }

    public DarkSupplicantEffect(final DarkSupplicantEffect effect) {
        super(effect);
    }

    @Override
    public DarkSupplicantEffect copy() {
        return new DarkSupplicantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        FilterCard filter = new FilterCard("card named Scion of Darkness");
        filter.add(new NamePredicate("Scion of Darkness"));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player == null) {
            return false;
        }
        // Library check
        if (player.chooseUse(Outcome.Benefit, "Do you want to search your library for Scion of Darkness?", source, game)) {
            if (player.searchLibrary(target, game)) {
                if (!target.getTargets().isEmpty()) {
                    for (UUID cardId : (List<UUID>) target.getTargets()) {
                        Card card = player.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            if (card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId())) {
                                return true;
                            }
                        }
                    }
                }
            }
            player.shuffleLibrary(source, game);
        }
        // Graveyard check
        if (player.chooseUse(Outcome.Benefit, "Do you want to search your graveyard for Scion of Darkness?", source, game)) {
            Cards graveyard = player.getGraveyard().copy();
            for (UUID card : graveyard) {
                Card checkCard = game.getCard(card);
                if (checkCard.getName().equals("Scion of Darkness")) {
                    checkCard.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId());
                    return true;
                }
            }
        }
        // Hand check
        if (player.chooseUse(Outcome.Benefit, "Do you want to search your hand for Scion of Darkness?", source, game)) {
            Cards hand = player.getHand().copy();
            for (UUID card : hand) {
                Card checkCard = game.getCard(card);
                if (checkCard.getName().equals("Scion of Darkness")) {
                    checkCard.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), source.getControllerId());
                    return true;
                }
            }
        }
        return false;
    }
}
