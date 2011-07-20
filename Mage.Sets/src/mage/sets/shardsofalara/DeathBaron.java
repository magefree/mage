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
package mage.sets.shardsofalara;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public class DeathBaron extends CardImpl<DeathBaron> {

    private final static FilterCreaturePermanent filterSkeletons = new FilterCreaturePermanent("Skeleton creatures");
    private final static FilterCreaturePermanent filterZombie = new FilterCreaturePermanent("Zombie creatures");

    static {
        filterSkeletons.getSubtype().add("Skeleton");
        filterSkeletons.setScopeSubtype(Filter.ComparisonScope.Any);
        filterZombie.getSubtype().add("Zombie");
        filterZombie.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public DeathBaron(UUID ownerId) {
        super(ownerId, 70, "Death Baron", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Zombie");
        this.subtype.add("Wizard");
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability firstPart = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filterSkeletons, false));
        firstPart.addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Constants.Duration.WhileOnBattlefield, filterSkeletons, false));
        this.addAbility(firstPart);
        Ability secondPart = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filterZombie, true));
        secondPart.addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Constants.Duration.WhileOnBattlefield, filterZombie, true));
        this.addAbility(secondPart);
    }

    public DeathBaron(final DeathBaron card) {
        super(card);
    }

    @Override
    public DeathBaron copy() {
        return new DeathBaron(this);
    }
}
