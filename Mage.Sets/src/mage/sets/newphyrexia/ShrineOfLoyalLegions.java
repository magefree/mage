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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.MyrToken;

/**
 *
 * @author North
 */
public class ShrineOfLoyalLegions extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ShrineOfLoyalLegions(UUID ownerId) {
        super(ownerId, 155, "Shrine of Loyal Legions", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "NPH";

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), TargetController.YOU, false));
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), filter, false));

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new MyrToken("NPH"), new CountersSourceCount(CounterType.CHARGE)),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public ShrineOfLoyalLegions(final ShrineOfLoyalLegions card) {
        super(card);
    }

    @Override
    public ShrineOfLoyalLegions copy() {
        return new ShrineOfLoyalLegions(this);
    }
}
