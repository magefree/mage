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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public class MistcutterHydra extends CardImpl {

    private static final FilterObject filter = new FilterObject("from blue");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public MistcutterHydra(UUID ownerId) {
        super(ownerId, 162, "Mistcutter Hydra", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{X}{G}");
        this.expansionSetCode = "THS";
        this.subtype.add("Hydra");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Mistcutter Hydra can't be countered.
        this.addAbility(new CantBeCounteredAbility());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // protection from blue
        this.addAbility(new ProtectionAbility(filter));
        // Mistcutter Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
    }

    public MistcutterHydra(final MistcutterHydra card) {
        super(card);
    }

    @Override
    public MistcutterHydra copy() {
        return new MistcutterHydra(this);
    }
}
