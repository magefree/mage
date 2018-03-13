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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.command.Emblem;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author spjspj
 */
public class ArlinnEmbracedByTheMoonEmblem extends Emblem {
    // "Creatures you control have haste and '{T}: This creature deals damage equal to its power to target creature or player.'"

    public ArlinnEmbracedByTheMoonEmblem() {
        this.setName("Emblem Arlinn");
        FilterPermanent filter = new FilterControlledCreaturePermanent("Creatures");
        GainAbilityControlledEffect effect = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfGame, filter);
        effect.setText("Creatures you control have haste");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        Effect effect2 = new DamageTargetEffect(new SourcePermanentPowerCount());
        effect2.setText("This creature deals damage equal to its power to target creature or player");
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new TapSourceCost());
        ability2.addTarget(new TargetCreatureOrPlayer());
        effect = new GainAbilityControlledEffect(ability2, Duration.EndOfGame, filter);
        effect.setText("and '{T}: This creature deals damage equal to its power to target creature or player");
        ability.addEffect(effect);
        this.getAbilities().add(ability);
    }
}
