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
package mage.sets.judgment;

import java.util.UUID;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LoneFox
 */
public class SoulcatchersAerie extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Bird");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Bird creatures");

    static {
        filter.add(new SubtypePredicate("Bird"));
        filter2.add(new SubtypePredicate("Bird"));
    }

    public SoulcatchersAerie(UUID ownerId) {
        super(ownerId, 25, "Soulcatchers' Aerie", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "JUD";

        // Whenever a Bird is put into your graveyard from the battlefield, put a feather counter on Soulcatchers' Aerie.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new AddCountersSourceEffect(CounterType.FEATHER.createInstance()),
            false, filter, false, true));
        // Bird creatures get +1/+1 for each feather counter on Soulcatchers' Aerie.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(new CountersSourceCount(CounterType.FEATHER),
            new CountersSourceCount(CounterType.FEATHER), Duration.WhileOnBattlefield, filter2, false,
            "Bird creatures get +1/+1 for each feather counter on {this}.")));
    }

    public SoulcatchersAerie(final SoulcatchersAerie card) {
        super(card);
    }

    @Override
    public SoulcatchersAerie copy() {
        return new SoulcatchersAerie(this);
    }
}
