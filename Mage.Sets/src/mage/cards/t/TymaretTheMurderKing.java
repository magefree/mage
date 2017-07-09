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
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public class TymaretTheMurderKing extends CardImpl {

    public TymaretTheMurderKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Zombie");
        this.subtype.add("Warrior");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}, Sacrifice another creature: Tymaret, the Murder King deals 2 damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // {1}{B}, Sacrifice a creature: Return Tymaret from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent("a creature"), false)));
        this.addAbility(ability);

    }

    public TymaretTheMurderKing(final TymaretTheMurderKing card) {
        super(card);
    }

    @Override
    public TymaretTheMurderKing copy() {
        return new TymaretTheMurderKing(this);
    }
}
