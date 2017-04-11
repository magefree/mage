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
package mage.cards.l;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class LegacyOfTheBeloved extends CardImpl {

    public LegacyOfTheBeloved(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");

        // As an additional cost to cast Legacy of the Beloved, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));

        // Search you library for up to two creatures cards that each have a lower converted mana cost that sacrificied creature's converted mana cost, reveal them and put them onto the battlefield, then shuffle you library.
        this.getSpellAbility().addEffect(new LegacyOfTheBelovedEffect());
    }

    public LegacyOfTheBeloved(final LegacyOfTheBeloved card) {
        super(card);
    }

    @Override
    public LegacyOfTheBeloved copy() {
        return new LegacyOfTheBeloved(this);
    }
}

class LegacyOfTheBelovedEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("up to two creatures cards that each have a lower converted mana cost that sacrificied creature's converted mana cost");

    public LegacyOfTheBelovedEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search you library for up to two creatures cards that each have a lower converted mana cost that sacrificied creature's converted mana cost, reveal them and put them onto the battlefield, then shuffle you library";
    }

    public LegacyOfTheBelovedEffect(final LegacyOfTheBelovedEffect effect) {
        super(effect);
    }

    @Override
    public LegacyOfTheBelovedEffect copy() {
        return new LegacyOfTheBelovedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null) {
            for (Object cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    Permanent p = (Permanent) game.getLastKnownInformation(((SacrificeTargetCost) cost).getPermanents().get(0).getId(), Zone.BATTLEFIELD);
                    if (p != null) {
                        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, p.getConvertedManaCost()));
                        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, filter);
                        Player player = game.getPlayer(source.getControllerId());
                        if (player != null && player.searchLibrary(target, game)) {
                            player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, false, false, false, null);
                            player.shuffleLibrary(source, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
