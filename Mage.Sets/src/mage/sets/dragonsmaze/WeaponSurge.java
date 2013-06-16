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

package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public class WeaponSurge extends CardImpl<WeaponSurge> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All creature you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public WeaponSurge(UUID ownerId) {
        super(ownerId, 40, "Weapon Surge", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "DGM";
        this.color.setRed(true);

        // Target creature you control gets +1/+0 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1,0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(true));

        // Overload {1}{R}
        Ability ability = new OverloadAbility(this, new BoostAllEffect(1,0, Duration.EndOfTurn, filter, false), new ManaCostsImpl("{1}{R}"));
        ability.addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter, false));
        this.addAbility(ability);
    }

    public WeaponSurge(final WeaponSurge card) {
        super(card);
    }

    @Override
    public WeaponSurge copy() {
        return new WeaponSurge(this);
    }

}
