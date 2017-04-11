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
package mage.cards.u;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class UginTheSpiritDragon extends CardImpl {

    public UginTheSpiritDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{8}");
        this.subtype.add("Ugin");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(7));

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
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, cmc + 1));
        filter.add(Predicates.not(new ColorlessPredicate()));
        Set<Card> permanentsToExile = new HashSet<>();
        permanentsToExile.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game));
        controller.moveCards(permanentsToExile, Zone.EXILED, source, game);
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
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
