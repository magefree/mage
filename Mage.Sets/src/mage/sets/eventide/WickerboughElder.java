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
package mage.sets.eventide;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public class WickerboughElder extends CardImpl<WickerboughElder> {

    private final static FilterPermanent filter = new FilterPermanent("artifact or enchantment");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.getCardType().add(CardType.ENCHANTMENT);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public WickerboughElder(UUID ownerId) {
        super(ownerId, 80, "Wickerbough Elder", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Treefolk");
        this.subtype.add("Shaman");
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(1)), false));
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect(), new ColoredManaCost(Constants.ColoredManaSymbol.G));
        ability.addCost(new RemoveCountersSourceCost(CounterType.M1M1.createInstance(1)));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public WickerboughElder(final WickerboughElder card) {
        super(card);
    }

    @Override
    public WickerboughElder copy() {
        return new WickerboughElder(this);
    }
}
