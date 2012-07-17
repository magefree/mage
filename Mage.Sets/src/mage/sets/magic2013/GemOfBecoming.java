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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class GemOfBecoming extends CardImpl<GemOfBecoming> {

    public GemOfBecoming(UUID ownerId) {
        super(ownerId, 205, "Gem of Becoming", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "M13";

        // {3}, {tap}, Sacrifice Gem of Becoming: Search your library for an Island card, a Swamp card, and a Mountain card.
        // Reveal those cards and put them into your hand. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GemOfBecomingEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public GemOfBecoming(final GemOfBecoming card) {
        super(card);
    }

    @Override
    public GemOfBecoming copy() {
        return new GemOfBecoming(this);
    }
}

class GemOfBecomingEffect extends OneShotEffect<GemOfBecomingEffect> {

    public GemOfBecomingEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for an Island card, a Swamp card, and a Mountain card. Reveal those cards and put them into your hand. Then shuffle your library";
    }

    public GemOfBecomingEffect(final GemOfBecomingEffect effect) {
        super(effect);
    }

    @Override
    public GemOfBecomingEffect copy() {
        return new GemOfBecomingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl();

        searchLand(player, source, game, cards, "Island");
        searchLand(player, source, game, cards, "Swamp");
        searchLand(player, source, game, cards, "Mountain");

        player.revealCards("Gem of Becoming", cards, game);
        player.shuffleLibrary(game);

        return false;
    }

    private void searchLand(Player player, Ability source, Game game, Cards cards, String subtype) {
        FilterLandCard filter = new FilterLandCard(subtype);
        filter.add(new SubtypePredicate(subtype));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            Card card = player.getLibrary().remove(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToZone(Zone.HAND, source.getId(), game, false);
                cards.add(card);
            }
        }
    }
}
