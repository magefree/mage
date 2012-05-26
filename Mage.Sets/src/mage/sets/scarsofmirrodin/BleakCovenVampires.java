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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.target.Target;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class BleakCovenVampires extends CardImpl<BleakCovenVampires> {

    private final String effectText = "Metalcraft - When Bleak Coven Vampires enters the battlefield, if you control three or more artifacts, target player loses 4 life and you gain 4 life.";

    public BleakCovenVampires(UUID ownerId) {
        super(ownerId, 55, "Bleak Coven Vampires", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(4), false);
        ability.addEffect(new GainLifeEffect(4));
        Target target = new TargetPlayer();
        target.setRequired(true);
        ability.addTarget(target);

        this.addAbility(new ConditionalTriggeredAbility(ability, MetalcraftCondition.getInstance(), effectText));
    }

    public BleakCovenVampires(final BleakCovenVampires card) {
        super(card);
    }

    @Override
    public BleakCovenVampires copy() {
        return new BleakCovenVampires(this);
    }
}
