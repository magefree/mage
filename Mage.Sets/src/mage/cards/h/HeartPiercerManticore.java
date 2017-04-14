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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author fireshoes
 */
public class HeartPiercerManticore extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public HeartPiercerManticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add("Manticore");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Heart-Piercer Manticore enters the battlefield, you may sacrifice another creature.
        // When you do, Heart-Piercer Manticore deals damage equal to that creature's power to target creature or player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new DamageTargetEffect(new SacrificeCostCreaturesPower()),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter))), true);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

        // Embalm {5}{R}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl("{5}{R}"), this));

    }

    public HeartPiercerManticore(final HeartPiercerManticore card) {
        super(card);
    }

    @Override
    public HeartPiercerManticore copy() {
        return new HeartPiercerManticore(this);
    }
}
