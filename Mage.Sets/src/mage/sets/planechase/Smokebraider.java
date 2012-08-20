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
package mage.sets.planechase;

import java.util.UUID;
import mage.ConditionalMana;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author North
 */
public class Smokebraider extends CardImpl<Smokebraider> {

    public Smokebraider(UUID ownerId) {
        super(ownerId, 66, "Smokebraider", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "HOP";
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add two mana in any combination of colors to your mana pool. Spend this mana only to cast Elemental spells or activate abilities of Elementals.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new SmokebraiderManaBuilder()));
    }

    public Smokebraider(final Smokebraider card) {
        super(card);
    }

    @Override
    public Smokebraider copy() {
        return new Smokebraider(this);
    }
}

class SmokebraiderManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SmokebraiderConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Elemental spells or activate abilities of Elementals";
    }
}

class SmokebraiderConditionalMana extends ConditionalMana {

    public SmokebraiderConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast Elemental spells or activate abilities of Elementals";
        addCondition(new SmokebraiderManaCondition());
    }
}

class SmokebraiderManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && object.hasSubtype("Elemental")) {
            return true;
        }
        return false;
    }
}
