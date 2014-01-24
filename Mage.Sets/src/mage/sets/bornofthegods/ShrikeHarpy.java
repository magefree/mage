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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class ShrikeHarpy extends CardImpl<ShrikeHarpy> {

    public ShrikeHarpy(UUID ownerId) {
        super(ownerId, 83, "Shrike Harpy", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Harpy");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Tribute 2</i>
        this.addAbility(new TributeAbility(2));
        // When Shrike Harpy enters the battlefield, if tribute wasn't paid, target opponent sacrifices a creature.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(new FilterCreaturePermanent("a creature"), 1, "target opponent"), false);
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(new ConditionalTriggeredAbility(ability, TributeNotPaidCondition.getInstance(),
                "When {this} enters the battlefield, if its tribute wasn't paid, target opponent sacrifices a creature."));
    }

    public ShrikeHarpy(final ShrikeHarpy card) {
        super(card);
    }

    @Override
    public ShrikeHarpy copy() {
        return new ShrikeHarpy(this);
    }
}
