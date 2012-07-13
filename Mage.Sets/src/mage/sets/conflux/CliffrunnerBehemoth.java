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
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public class CliffrunnerBehemoth extends CardImpl<CliffrunnerBehemoth> {
    private static final FilterPermanent redPermanentFilter = new FilterPermanent("red");
    private static final FilterPermanent whitePermanentFilter = new FilterPermanent("white");

    static {
        redPermanentFilter.add(new ColorPredicate(ObjectColor.RED));
        whitePermanentFilter.add(new ColorPredicate(ObjectColor.WHITE));
    }


    public CliffrunnerBehemoth(UUID ownerId) {
        super(ownerId, 79, "Cliffrunner Behemoth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "CON";
        this.subtype.add("Rhino");
        this.subtype.add("Beast");

        this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Cliffrunner Behemoth has haste as long as you control a red permanent.
        this.addAbility(
            new SimpleStaticAbility(
                Constants.Zone.BATTLEFIELD,
                new ConditionalContinousEffect(
                    new GainAbilitySourceEffect(HasteAbility.getInstance(), Constants.Duration.WhileOnBattlefield),
                    new ControlsPermanentCondition(redPermanentFilter), "{this} has haste as long as you control a red permanent")));
        // Cliffrunner Behemoth has lifelink as long as you control a white permanent.
        this.addAbility(
            new SimpleStaticAbility(
                Constants.Zone.BATTLEFIELD,
                new ConditionalContinousEffect(
                    new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Constants.Duration.WhileOnBattlefield),
                    new ControlsPermanentCondition(whitePermanentFilter), "{this} has lifelink as long as you control a white permanent")));
    }

    public CliffrunnerBehemoth(final CliffrunnerBehemoth card) {
        super(card);
    }

    @Override
    public CliffrunnerBehemoth copy() {
        return new CliffrunnerBehemoth(this);
    }
}
