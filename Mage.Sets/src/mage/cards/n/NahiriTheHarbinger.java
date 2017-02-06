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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class NahiriTheHarbinger extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchantment, tapped artifact, or tapped creature");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ENCHANTMENT),
                (Predicates.and(new CardTypePredicate(CardType.ARTIFACT),
                    new TappedPredicate())),
                (Predicates.and(new CardTypePredicate(CardType.CREATURE),
                    new TappedPredicate()))));
    }

    public NahiriTheHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{R}{W}");
        this.subtype.add("Nahiri");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +2: You may discard a card. If you do, draw a card.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), 2));

        // -2: Exile target enchantment, tapped artifact, or tapped creature.
        LoyaltyAbility ability = new LoyaltyAbility(new ExileTargetEffect(), -2);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -8: Search your library for an artifact or creature card, put it onto the battlefield, then shuffle your library. It gains haste.
        // Return it to your hand at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(new NahiriTheHarbingerEffect(), -8));
    }

    public NahiriTheHarbinger(final NahiriTheHarbinger card) {
        super(card);
    }

    @Override
    public NahiriTheHarbinger copy() {
        return new NahiriTheHarbinger(this);
    }
}

class NahiriTheHarbingerEffect extends SearchEffect {

    private static final FilterCard filterCard = new FilterCard("artifact or creature card");

    static {
        filterCard.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }

    NahiriTheHarbingerEffect() {
        super(new TargetCardInLibrary(0, 1, filterCard), Outcome.PutCardInPlay);
        this.staticText = "Search your library for an artifact or creature card, put it onto the battlefield, then shuffle your library. It gains haste. Return it to your hand at the beginning of the next end step";
    }

    NahiriTheHarbingerEffect(final NahiriTheHarbingerEffect effect) {
        super(effect);
    }

    @Override
    public NahiriTheHarbingerEffect copy() {
        return new NahiriTheHarbingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                        effect.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                        game.addEffect(effect, source);
                        Effect effect2 = new ReturnToHandTargetEffect();
                        effect2.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect2);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
