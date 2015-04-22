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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */

public class UginTheSpiritDragon extends CardImpl {

    public UginTheSpiritDragon(UUID ownerId) {
        super(ownerId, 1, "Ugin, the Spirit Dragon", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{8}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Ugin");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(7)), false));

        // +2: Ugin, the Spirit Dragon deals 3 damage to target creature or player.
        LoyaltyAbility ability = new LoyaltyAbility(new DamageTargetEffect(3), 2);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

        // -X: Exile each permanent with converted mana cost X or less that's one or more colors.
        this.addAbility(new LoyaltyAbility(new UginTheSpiritDragonEffect2()));

        // -10: You gain 7 life, draw 7 cards, then put up to seven permanent cards from your hand onto the battlefield.
        this.addAbility(new LoyaltyAbility(new UginTheSpiritDragonEffect3(), -10));

    }

    public UginTheSpiritDragon(final UginTheSpiritDragon card) {
        super(card);
    }

    @Override
    public UginTheSpiritDragon copy() {
        return new UginTheSpiritDragon(this);
    }

}

class UginTheSpiritDragonEffect2 extends OneShotEffect {

    public UginTheSpiritDragonEffect2() {
        super(Outcome.Exile);
        this.staticText = "exile each permanent with converted mana cost X or less that's one or more colors";
    }

    public UginTheSpiritDragonEffect2(final UginTheSpiritDragonEffect2 effect) {
        super(effect);
    }

    @Override
    public UginTheSpiritDragonEffect2 copy() {
        return new UginTheSpiritDragonEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                cmc = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }

        FilterPermanent filter = new FilterPermanent("permanent with converted mana cost X or less that's one or more colors");
        filter.add(new ConvertedManaCostPredicate(ComparisonType.LessThan, cmc + 1));
        filter.add(Predicates.not(new ColorlessPredicate()));
        for(Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
        }
        return true;
    }
}

class UginTheSpiritDragonEffect3 extends OneShotEffect {

    public UginTheSpiritDragonEffect3() {
        super(Outcome.Benefit);
        this.staticText = "You gain 7 life, draw 7 cards, then put up to seven permanent cards from your hand onto the battlefield";
    }

    public UginTheSpiritDragonEffect3(final UginTheSpiritDragonEffect3 effect) {
        super(effect);
    }

    @Override
    public UginTheSpiritDragonEffect3 copy() {
        return new UginTheSpiritDragonEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(7, game);
            controller.drawCards(7, game);
            TargetCardInHand target = new TargetCardInHand(0, 7, new FilterPermanentCard("permanent cards"));
            if (controller.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                for (UUID targetId: target.getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        controller.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getControllerId());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
