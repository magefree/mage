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
package mage.sets.zendikar;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class HagraDiabolist extends CardImpl<HagraDiabolist> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Allies you control");

    static {
        filter.getSubtype().add("Ally");
		filter.setScopeSubtype(Filter.ComparisonScope.Any);
		filter.setTargetController(Constants.TargetController.YOU);
    }

    public HagraDiabolist(UUID ownerId) {
        super(ownerId, 95, "Hagra Diabolist", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Ogre");
        this.subtype.add("Shaman");
        this.subtype.add("Ally");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter)), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public HagraDiabolist(final HagraDiabolist card) {
        super(card);
    }

    @Override
    public HagraDiabolist copy() {
        return new HagraDiabolist(this);
    }
}
