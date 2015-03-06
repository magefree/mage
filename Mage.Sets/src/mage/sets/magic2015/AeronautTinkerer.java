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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public class AeronautTinkerer extends CardImpl {
    
    final static private String rule = "{this} has flying as long as you control an artifact";

    public AeronautTinkerer(UUID ownerId) {
        super(ownerId, 43, "Aeronaut Tinkerer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "M15";
        this.subtype.add("Human");
        this.subtype.add("Artificer");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Aeronaut Tinkerer has flying as long as you control an artifact.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent()), rule)));

    }

    public AeronautTinkerer(final AeronautTinkerer card) {
        super(card);
    }

    @Override
    public AeronautTinkerer copy() {
        return new AeronautTinkerer(this);
    }
}
