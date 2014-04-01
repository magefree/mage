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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class Ophiomancer extends CardImpl<Ophiomancer> {

    public Ophiomancer(UUID ownerId) {
        super(ownerId, 84, "Ophiomancer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "C13";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if you control no Snakes, put a 1/1 black Snake creature token with deathtouch onto the battlefield.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new OphiomancerSnakeToken()), TargetController.ANY, false),
                new ControlsPermanentCondition(new FilterCreaturePermanent("Snake", "no Snakes"), ControlsPermanentCondition.CountType.EQUAL_TO, 0),
                "At the beginning of each upkeep, if you control no Snakes, put a 1/1 black Snake creature token with deathtouch onto the battlefield."));
    }

    public Ophiomancer(final Ophiomancer card) {
        super(card);
    }

    @Override
    public Ophiomancer copy() {
        return new Ophiomancer(this);
    }
}

class OphiomancerSnakeToken extends Token {

    public OphiomancerSnakeToken() {
        super("Snake", "1/1 black Snake creature token with deathtouch");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLACK;
        subtype.add("Snake");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(DeathtouchAbility.getInstance());
    }

}
