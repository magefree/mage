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
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth

 */
public class HeartlashCinder extends CardImpl<HeartlashCinder> {

    public HeartlashCinder(UUID ownerId) {
        super(ownerId, 56, "Heartlash Cinder", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Chroma - When Heartlash Cinder enters the battlefield, it gets +X/+0 until end of turn, where X is the number of red mana symbols in the mana costs of permanents you control.
        ContinuousEffect effect = new BoostSourceEffect(new ChromaHeartlashCinderCount(), new StaticValue(0), Duration.EndOfTurn);
        effect.setText("<i>Chroma</i> - When Heartlash Cinder enters the battlefield, it gets +X/+0 until end of turn, where X is the number of red mana symbols in the mana costs of permanents you control.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false, true));
        
    }

    public HeartlashCinder(final HeartlashCinder card) {
        super(card);
    }

    @Override
    public HeartlashCinder copy() {
        return new HeartlashCinder(this);
    }
}

class ChromaHeartlashCinderCount implements DynamicValue {

    private int chroma;

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        chroma = 0;
        for (Card card : game.getBattlefield().getAllActivePermanents(new FilterControlledPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += card.getManaCost().getMana().getRed();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaOutrageShamanCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}