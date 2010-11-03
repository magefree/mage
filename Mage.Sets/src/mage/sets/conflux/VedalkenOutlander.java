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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;

/**
 *
 * @author Loki
 */
public class VedalkenOutlander extends CardImpl<VedalkenOutlander> {
    private static FilterCard filter = new FilterCard("red");

    static {
    	filter.setUseColor(true);
	filter.getColor().setRed(true);
    }

    public VedalkenOutlander(UUID ownerId) {
        super(ownerId, "Vedalken Outlander", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{U}");
        this.expansionSetCode = "CON";
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.subtype.add("Vedalken");
        this.subtype.add("Scout");
        this.power = new MageInt(2);
	this.toughness = new MageInt(2);
        this.addAbility(new ProtectionAbility(filter));
    }

    public VedalkenOutlander(final VedalkenOutlander card) {
        super(card);
    }

    @Override
    public VedalkenOutlander copy() {
        return new VedalkenOutlander(this);
    }

    @Override
    public String getArt() {
        return "118771_typ_reg_sty_010.jpg";
    }
}
