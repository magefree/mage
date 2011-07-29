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
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;

/**
 *
 * @author North
 */
public class EmberstrikeDuo extends CardImpl<EmberstrikeDuo> {

    private static final FilterCard blackFilter = new FilterCard("a black spell");
    private static final FilterCard redFilter = new FilterCard("a red spell");

    static {
        blackFilter.getColor().setBlack(true);
        blackFilter.setUseColor(true);
        redFilter.getColor().setRed(true);
        redFilter.setUseColor(true);
    }

    public EmberstrikeDuo(UUID ownerId) {
        super(ownerId, 185, "Emberstrike Duo", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B/R}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Elemental");
        this.subtype.add("Warrior");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a black spell, Emberstrike Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), blackFilter, false));
        // Whenever you cast a red spell, Emberstrike Duo gains first strike until end of turn.
        this.addAbility(new SpellCastTriggeredAbility(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), redFilter, false));
    }

    public EmberstrikeDuo(final EmberstrikeDuo card) {
        super(card);
    }

    @Override
    public EmberstrikeDuo copy() {
        return new EmberstrikeDuo(this);
    }
}
