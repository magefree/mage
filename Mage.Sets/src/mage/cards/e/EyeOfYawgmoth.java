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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
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
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public class EyeOfYawgmoth extends CardImpl {

    public EyeOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {T}, Sacrifice a creature: Reveal a number of cards from the top of your library equal to the sacrificed creature's power. Put one into your hand and exile the rest.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EyeOfYawgmothEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    public EyeOfYawgmoth(final EyeOfYawgmoth card) {
        super(card);
    }

    @Override
    public EyeOfYawgmoth copy() {
        return new EyeOfYawgmoth(this);
    }
}

class EyeOfYawgmothEffect extends OneShotEffect {

    EyeOfYawgmothEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal a number of cards from the top of your library equal to the sacrificed creature's power. Put one into your hand and exile the rest";
    }

    EyeOfYawgmothEffect(final EyeOfYawgmothEffect effect) {
        super(effect);
    }

    @Override
    public EyeOfYawgmothEffect copy() {
        return new EyeOfYawgmothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int power = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                power = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                break;
            }
        }
        if (power > 0) {
            Cards cards = new CardsImpl();
            int count = Math.min(controller.getLibrary().size(), power);
            for (int i = 0; i < count; i++) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            controller.revealCards(source.getSourceObject(game).getIdName(), cards, game);

            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
            if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    game.informPlayers(source.getSourceObject(game).getIdName() + ": " + controller.getLogName() + " puts " + card.getIdName() + " into their hand");
                }
            }
            for (UUID cardId : cards) {
                Card card = game.getCard(cardId);
                card.moveToExile(null, "", source.getSourceId(), game);
            }
        }
        return true;
    }
}
