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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;

/**
 *
 * @author North
 */
public class ThistledownDuo extends CardImpl<ThistledownDuo> {

    private static final FilterSpell whiteFilter = new FilterSpell("a white spell");
    private static final FilterSpell blueFilter = new FilterSpell("a blue spell");

    static {
        whiteFilter.getColor().setWhite(true);
        whiteFilter.setUseColor(true);
        blueFilter.getColor().setBlue(true);
        blueFilter.setUseColor(true);
    }

    public ThistledownDuo(UUID ownerId) {
        super(ownerId, 152, "Thistledown Duo", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W/U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Kithkin");
        this.subtype.add("Soldier");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a white spell, Thistledown Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), whiteFilter, false));
        // Whenever you cast a blue spell, Thistledown Duo gains flying until end of turn.
        this.addAbility(new SpellCastTriggeredAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), blueFilter, false));
    }

    public ThistledownDuo(final ThistledownDuo card) {
        super(card);
    }

    @Override
    public ThistledownDuo copy() {
        return new ThistledownDuo(this);
    }
}
