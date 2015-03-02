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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.Token;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author emerald000
 */
public class WingmateRoc extends CardImpl {

    public WingmateRoc(UUID ownerId) {
        super(ownerId, 31, "Wingmate Roc", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Bird");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // <em>Raid</em> - When Wingmate Roc enters the battlefield, if you attacked with a creature this turn, put a 3/4 white Bird creature token with flying onto the battlefield.
        this.addAbility(new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WingmateRocToken())), RaidCondition.getInstance(), 
                 "<i>Raid</i> -  When {this} enters the battlefield, if you attacked with a creature this turn, put a 3/4 white Bird creature token with flying onto the battlefield.", false), 
                new PlayerAttackedWatcher());

        // Whenever Wingmate Roc attacks, you gain 1 life for each attacking creature.
        Effect effect = new GainLifeEffect(new AttackingCreatureCount());
        effect.setText("you gain 1 life for each attacking creature");
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    public WingmateRoc(final WingmateRoc card) {
        super(card);
    }

    @Override
    public WingmateRoc copy() {
        return new WingmateRoc(this);
    }
}

class WingmateRocToken extends Token {

    WingmateRocToken() {
        super("Bird", "3/4 white Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add("Bird");
        color.setWhite(true);
        power = new MageInt(3);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }
}
