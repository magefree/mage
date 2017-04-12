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
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class LilianaDefiantNecromancer extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("nonlegendary creature with converted mana cost X from your graveyard");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    UUID ability2Id;

    public LilianaDefiantNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.subtype.add("Liliana");
        this.color.setBlack(true);

        this.nightCard = true;

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Each player discards a card.
        this.addAbility(new LoyaltyAbility(new DiscardEachPlayerEffect(1, false), 2));

        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        Ability ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability2Id = ability.getOriginalId();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        //-8: You get an emblem with "Whenever a creature dies, return it to the battlefield under your control at the beginning of the next end step.";
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaDefiantNecromancerEmblem()), -8));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(ability2Id)) {
            int cmc = 0;
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof PayVariableLoyaltyCost) {
                    cmc = ((PayVariableLoyaltyCost) cost).getAmount();
                }
            }
            FilterCard newFilter = filter.copy();
            newFilter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));
            ability.getTargets().clear();
            ability.addTarget(new TargetCardInYourGraveyard(newFilter));
        }
    }

    public LilianaDefiantNecromancer(final LilianaDefiantNecromancer card) {
        super(card);
        this.ability2Id = card.ability2Id;
    }

    @Override
    public LilianaDefiantNecromancer copy() {
        return new LilianaDefiantNecromancer(this);
    }
}

class LilianaDefiantNecromancerEmblem extends Emblem {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    //  You get an emblem with "Whenever a creature you control dies, return it to the battlefield under your control at the beginning of the next end step."
    public LilianaDefiantNecromancerEmblem() {
        this.setName("Emblem Liliana");
        Ability ability = new DiesCreatureTriggeredAbility(Zone.COMMAND, new LilianaDefiantNecromancerEmblemEffect(), false, filter, true);
        this.getAbilities().add(ability);
    }
}

class LilianaDefiantNecromancerEmblemEffect extends OneShotEffect {

    LilianaDefiantNecromancerEmblemEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield under your control at the beginning of the next end step";
    }

    LilianaDefiantNecromancerEmblemEffect(final LilianaDefiantNecromancerEmblemEffect effect) {
        super(effect);
    }

    @Override
    public LilianaDefiantNecromancerEmblemEffect copy() {
        return new LilianaDefiantNecromancerEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            effect.setText("return that card to the battlefield at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone.COMMAND, effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}
