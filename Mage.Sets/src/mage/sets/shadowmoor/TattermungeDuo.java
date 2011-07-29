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
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;

/**
 *
 * @author North
 */
public class TattermungeDuo extends CardImpl<TattermungeDuo> {

    private static final FilterCard redFilter = new FilterCard("a red spell");
    private static final FilterCard greenFilter = new FilterCard("a green spell");

    static {
        redFilter.getColor().setRed(true);
        redFilter.setUseColor(true);
        greenFilter.getColor().setGreen(true);
        greenFilter.setUseColor(true);
    }

    public TattermungeDuo(UUID ownerId) {
        super(ownerId, 218, "Tattermunge Duo", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R/G}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a red spell, Tattermunge Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), redFilter, false));
        // Whenever you cast a green spell, Tattermunge Duo gains forestwalk until end of turn.
        this.addAbility(new SpellCastTriggeredAbility(new GainAbilitySourceEffect(new ForestwalkAbility(), Duration.EndOfTurn), greenFilter, false));
    }

    public TattermungeDuo(final TattermungeDuo card) {
        super(card);
    }

    @Override
    public TattermungeDuo copy() {
        return new TattermungeDuo(this);
    }
}
