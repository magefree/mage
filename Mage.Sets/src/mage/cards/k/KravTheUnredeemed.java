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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public class KravTheUnredeemed extends CardImpl {

    public KravTheUnredeemed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Regna, the Redeemer (When this creature enters the battlefield, target player may put Regna into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Regna, the Redeemer", true));

        // {B}, Sacrifice X creatures: Target player draws X cards and gains X life. Put X +1/+1 counters on Krav, the Unredeemed.
        Ability ability = new SimpleActivatedAbility(new KravTheUnredeemedEffect(), new ManaCostsImpl("{B}"));
        ability.addTarget(new TargetPlayer());
        ability.addCost(new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE));
        this.addAbility(ability);
    }

    public KravTheUnredeemed(final KravTheUnredeemed card) {
        super(card);
    }

    @Override
    public KravTheUnredeemed copy() {
        return new KravTheUnredeemed(this);
    }
}

class KravTheUnredeemedEffect extends OneShotEffect {

    KravTheUnredeemedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player draws X cards and gains X life. Put X +1/+1 counters on {this}";
    }

    KravTheUnredeemedEffect(final KravTheUnredeemedEffect effect) {
        super(effect);
    }

    @Override
    public KravTheUnredeemedEffect copy() {
        return new KravTheUnredeemedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = new GetXValue().calculate(game, source, this);
        new DrawCardTargetEffect(xValue).apply(game, source);
        new GainLifeTargetEffect(xValue).apply(game, source);
        new AddCountersSourceEffect(CounterType.P1P1.createInstance(xValue)).apply(game, source);
        return true;
    }
}
