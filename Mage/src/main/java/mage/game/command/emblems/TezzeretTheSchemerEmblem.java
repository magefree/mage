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
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.command.Emblem;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public class TezzeretTheSchemerEmblem extends Emblem {

    public TezzeretTheSchemerEmblem() {
        this.setName("Emblem Tezzeret");

        Effect effect = new AddCardTypeTargetEffect(CardType.CREATURE, Duration.EndOfGame);
        effect.setText("target artifact you control becomes an artifact creature");
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.COMMAND, effect, TargetController.YOU, false, true);
        effect = new SetPowerToughnessTargetEffect(5, 5, Duration.EndOfGame);
        effect.setText("with base power and toughness 5/5");
        ability.addEffect(effect);
        ability.addTarget(new TargetPermanent(new FilterControlledArtifactPermanent()));
        this.getAbilities().add(ability);
    }
}
