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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author Loki
 */
public class BelligerentHatchling extends CardImpl<BelligerentHatchling> {

    private final static FilterSpell filterRedSpell = new FilterSpell("a red spell");
    private final static FilterSpell filterWhiteSpell = new FilterSpell("a white spell");

    static {
        filterRedSpell.add(new ColorPredicate(ObjectColor.RED));
        filterWhiteSpell.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BelligerentHatchling(UUID ownerId) {
        super(ownerId, 134, "Belligerent Hatchling", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R/W}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");
        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4))));
        this.addAbility(new SpellCastTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterRedSpell, false));
        this.addAbility(new SpellCastTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterWhiteSpell, false));
    }

    public BelligerentHatchling(final BelligerentHatchling card) {
        super(card);
    }

    @Override
    public BelligerentHatchling copy() {
        return new BelligerentHatchling(this);
    }
}
