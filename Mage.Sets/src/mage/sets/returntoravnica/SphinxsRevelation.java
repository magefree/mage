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

package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;

/**
 *
 * @author LevelX2
 */
public class SphinxsRevelation extends CardImpl<SphinxsRevelation> {

    public SphinxsRevelation (UUID ownerId) {
        super(ownerId, 200, "Sphinx's Revelation", Rarity.MYTHIC, new CardType[]{CardType.INSTANT}, "{X}{W}{U}{U}");
        this.expansionSetCode = "RTR";

        this.color.setWhite(true);
        this.color.setBlue(true);

        // You gain X life and draw X cards.
        ManacostVariableValue manaX = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new GainLifeEffect(manaX));
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(manaX));
    }

    public SphinxsRevelation (final SphinxsRevelation card) {
        super(card);
    }

    @Override
    public SphinxsRevelation copy() {
        return new SphinxsRevelation(this);
    }
}