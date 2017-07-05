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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CardsCycledOrDiscardedThisTurnWatcher;

/**
 *
 * @author spjspj
 */
public class HollowOne extends CardImpl {

    public HollowOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add("Golem");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hollow One costs {2} less to cast for each card you've cycled or discarded this turn.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new HollowOneReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability, new CardsCycledOrDiscardedThisTurnWatcher());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    public HollowOne(final HollowOne card) {
        super(card);
    }

    @Override
    public HollowOne copy() {
        return new HollowOne(this);
    }
}

class HollowOneReductionEffect extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AttackingPredicate());
    }

    public HollowOneReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {2} less to cast for each card you've cycled or discarded this turn";
    }

    protected HollowOneReductionEffect(HollowOneReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        CardsCycledOrDiscardedThisTurnWatcher watcher = (CardsCycledOrDiscardedThisTurnWatcher) game.getState().getWatchers().get(CardsCycledOrDiscardedThisTurnWatcher.class.getSimpleName());
        int reductionAmount = 0;
        if (controller != null && watcher != null) {
            for (Card card : watcher.getCardsCycledOrDiscardedThisTurn(controller.getId()).getCards(game)) {
                if (card.getOwnerId().equals(controller.getId())) {
                    reductionAmount++;
                }
            }
            CardUtil.reduceCost(abilityToModify, reductionAmount * 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public HollowOneReductionEffect copy() {
        return new HollowOneReductionEffect(this);
    }
}
