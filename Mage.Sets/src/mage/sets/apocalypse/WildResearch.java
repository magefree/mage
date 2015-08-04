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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public class WildResearch extends CardImpl {

    private static final FilterCard filterEnchantment = new FilterCard("enchantment card");
    private static final FilterCard filterInstant = new FilterCard("instant card");

    static {
        filterEnchantment.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterInstant.add(new CardTypePredicate(CardType.INSTANT));
    }

    public WildResearch(UUID ownerId) {
        super(ownerId, 72, "Wild Research", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "APC";

        // {1}{W}: Search your library for an enchantment card and reveal that card. Put it into your hand, then discard a card at random. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WildResearchEffect(filterEnchantment), new ManaCostsImpl<>("{1}{W}")));

        // {1}{U}: Search your library for an instant card and reveal that card. Put it into your hand, then discard a card at random. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WildResearchEffect(filterInstant), new ManaCostsImpl<>("{1}{U}")));

    }

    public WildResearch(final WildResearch card) {
        super(card);
    }

    @Override
    public WildResearch copy() {
        return new WildResearch(this);
    }
}

class WildResearchEffect extends OneShotEffect {

    protected final FilterCard filter;

    WildResearchEffect(FilterCard filter) {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for an " + filter.getMessage() + " and reveal that card. Put it into your hand, then discard a card at random. Then shuffle your library.";
        this.filter = filter;
    }

    WildResearchEffect(final WildResearchEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public WildResearchEffect copy() {
        return new WildResearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    Card card = controller.getLibrary().remove(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, null, Zone.HAND, source, game);
                        Cards cards = new CardsImpl();
                        cards.add(card);
                        controller.revealCards(sourceObject.getIdName(), cards, game, true);
                    }
                }
            }
            controller.discardOne(true, source, game);
            controller.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
