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
import mage.MageInt;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth

 */
public class OverbeingOfMyth extends CardImpl<OverbeingOfMyth> {

    public OverbeingOfMyth(UUID ownerId) {
        super(ownerId, 157, "Overbeing of Myth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G/U}{G/U}{G/U}{G/U}{G/U}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Spirit");
        this.subtype.add("Avatar");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Overbeing of Myth's power and toughness are each equal to the number of cards in your hand.
        DynamicValue number = new CardsInControllerHandCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(number, Duration.WhileOnBattlefield)));
        
        // At the beginning of your draw step, draw an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardControllerEffect(1), TargetController.YOU, false));
        
    }

    public OverbeingOfMyth(final OverbeingOfMyth card) {
        super(card);
    }

    @Override
    public OverbeingOfMyth copy() {
        return new OverbeingOfMyth(this);
    }
}
