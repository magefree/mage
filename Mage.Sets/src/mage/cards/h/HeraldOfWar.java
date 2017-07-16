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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noxx
 */
public class HeraldOfWar extends CardImpl {

    public HeraldOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add("Angel");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Herald of War attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));

        // Angel spells and Human spells you cast cost {1} less to cast for each +1/+1 counter on Herald of War.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HeraldOfWarCostReductionEffect()));
    }

    public HeraldOfWar(final HeraldOfWar card) {
        super(card);
    }

    @Override
    public HeraldOfWar copy() {
        return new HeraldOfWar(this);
    }
}

class HeraldOfWarCostReductionEffect extends CostModificationEffectImpl {

    HeraldOfWarCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Angel spells and Human spells you cast cost {1} less to cast for each +1/+1 counter on Herald of War";
    }

    HeraldOfWarCostReductionEffect(HeraldOfWarCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int amount = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            if (amount > 0) {
                CardUtil.adjustCost(spellAbility, amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card sourceCard = game.getCard(abilityToModify.getSourceId());
            if (sourceCard != null && abilityToModify.getControllerId().equals(source.getControllerId()) && (sourceCard.hasSubtype(SubType.ANGEL, game) || sourceCard.hasSubtype(SubType.HUMAN, game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HeraldOfWarCostReductionEffect copy() {
        return new HeraldOfWarCostReductionEffect(this);
    }

}
