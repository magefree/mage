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
package mage.sets.starwars;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

/**
 *
 * @author Styxo
 */
public class C3POAndR2D2 extends CardImpl {

    public C3POAndR2D2(UUID ownerId) {
        super(ownerId, 160, "C-3PO and R2D2", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}{U}");
        this.expansionSetCode = "SWS";
        this.supertype.add("Legendary");
        this.subtype.add("Droid");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When C-3PO and R2D2 leaves the battlefield, you gain 4 life and draw a card.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new GainLifeEffect(4), false);
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("and draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Repair 2
        this.addAbility(new RepairAbility(2));
    }

    public C3POAndR2D2(final C3POAndR2D2 card) {
        super(card);
    }

    @Override
    public C3POAndR2D2 copy() {
        return new C3POAndR2D2(this);
    }
}
