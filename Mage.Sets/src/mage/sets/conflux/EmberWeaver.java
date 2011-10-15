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
package mage.sets.conflux;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;

/**
 * @author Loki
 */
public class EmberWeaver extends CardImpl<EmberWeaver> {
    private static final FilterPermanent redPermanentFilter = new FilterPermanent("red");

    static {
        redPermanentFilter.setUseColor(true);
        redPermanentFilter.getColor().setRed(true);
    }

    public EmberWeaver(UUID ownerId) {
        super(ownerId, 81, "Ember Weaver", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "CON";
        this.subtype.add("Spider");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());
        // As long as you control a red permanent, Ember Weaver gets +1/+0 and has first strike.
        this.addAbility(
                new SimpleStaticAbility(
                        Constants.Zone.BATTLEFIELD,
                        new ConditionalContinousEffect(
                                new BoostSourceEffect(1, 0, Constants.Duration.WhileOnBattlefield),
                                new ControlsPermanentCondition(redPermanentFilter), "{this} gets +1/+0 as long as you control a red permanent")));
        this.addAbility(
                new SimpleStaticAbility(
                        Constants.Zone.BATTLEFIELD,
                        new ConditionalContinousEffect(
                                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Constants.Duration.WhileOnBattlefield),
                                new ControlsPermanentCondition(redPermanentFilter), "{this} has first strike as long as you control a red permanent")));

    }

    public EmberWeaver(final EmberWeaver card) {
        super(card);
    }

    @Override
    public EmberWeaver copy() {
        return new EmberWeaver(this);
    }
}
