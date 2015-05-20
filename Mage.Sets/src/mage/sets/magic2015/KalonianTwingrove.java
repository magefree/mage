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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import static mage.sets.magic2015.KalonianTwingrove.filterLands;

/**
 *
 * @author LevelX2
 */
public class KalonianTwingrove extends CardImpl {
    
    final static FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(new SubtypePredicate("Forest"));
    }

    public KalonianTwingrove(UUID ownerId) {
        super(ownerId, 182, "Kalonian Twingrove", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.expansionSetCode = "M15";
        this.subtype.add("Treefolk");
        this.subtype.add("Warrior");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kalonian Twingrove's power and toughness are each equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.EndOfGame)));
        // When Kalonian Twingrove enters the battlefield, put a green Treefolk Warrior creature token onto the battlefield with "This creature's power and toughness are each equal to the number of Forests you control."
        Effect effect = new CreateTokenEffect(new KalonianTwingroveTreefolkWarriorToken());
        effect.setText("put a green Treefolk Warrior creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of Forests you control.\"");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect,false));
    }

    public KalonianTwingrove(final KalonianTwingrove card) {
        super(card);
    }

    @Override
    public KalonianTwingrove copy() {
        return new KalonianTwingrove(this);
    }
}

class KalonianTwingroveTreefolkWarriorToken extends Token {

    public KalonianTwingroveTreefolkWarriorToken() {
        super("Treefolk Warrior", "green Treefolk Warrior creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of Forests you control.\"");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Treefolk");
        subtype.add("Warrior");
        power = new MageInt(0);
        toughness = new MageInt(0);
        
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.WhileOnBattlefield)));
    }
}
