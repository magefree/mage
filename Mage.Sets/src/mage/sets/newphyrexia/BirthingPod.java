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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public class BirthingPod extends CardImpl<BirthingPod> {

    public BirthingPod(UUID ownerId) {
        super(ownerId, 104, "Birthing Pod", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}{GP}");
        this.expansionSetCode = "NPH";

        this.color.setGreen(true);

        // {1}{GP}, {tap}, Sacrifice a creature: Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost, put that card onto the battlefield, then shuffle your library. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Constants.Zone.BATTLEFIELD, new BirthingPodEffect(), new ManaCostsImpl("{1}{GP}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        this.addAbility(ability);
    }

    public BirthingPod(final BirthingPod card) {
        super(card);
    }

    @Override
    public BirthingPod copy() {
        return new BirthingPod(this);
    }
}

class BirthingPodEffect extends OneShotEffect<BirthingPodEffect> {
    BirthingPodEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost, put that card onto the battlefield, then shuffle your library";
    }

    BirthingPodEffect(final BirthingPodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sacrificedPermanent = null;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                if (sacrificeCost.getPermanents().size() > 0) {
                    sacrificedPermanent = sacrificeCost.getPermanents().get(0);
                }
                break;
            }
        }
        Player player = game.getPlayer(source.getControllerId());
        if (sacrificedPermanent != null && player != null) {
            int newConvertedCost = sacrificedPermanent.getManaCost().convertedManaCost() + 1;
            FilterCard filter = new FilterCard("creature card with converted mana cost " + newConvertedCost);
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, newConvertedCost));
            filter.getCardType().add(CardType.CREATURE);
            filter.setScopeCardType(Filter.ComparisonScope.Any);
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (player.searchLibrary(target, game)) {
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        card.putOntoBattlefield(game, Constants.Zone.HAND, source.getId(), source.getControllerId());
                    }
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public BirthingPodEffect copy() {
        return new BirthingPodEffect(this);
    }
}
