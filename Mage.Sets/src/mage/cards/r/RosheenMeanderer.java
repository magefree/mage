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
package mage.cards.r;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class RosheenMeanderer extends CardImpl {

    public RosheenMeanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R/G}");
        this.supertype.add("Legendary");
        this.subtype.add("Giant");
        this.subtype.add("Shaman");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {T}: Add {C}{C}{C}{C} to your mana pool. Spend this mana only on costs that contain {X}.
        this.addAbility(new RosheenMeandererManaAbility());

    }

    public RosheenMeanderer(final RosheenMeanderer card) {
        super(card);
    }

    @Override
    public RosheenMeanderer copy() {
        return new RosheenMeanderer(this);
    }
}

class RosheenMeandererManaAbility extends BasicManaAbility {

    RosheenMeandererManaAbility() {
        super(new BasicManaEffect(new RosheenMeandererConditionalMana()));
        this.netMana.add(Mana.ColorlessMana(4));
    }

    RosheenMeandererManaAbility(RosheenMeandererManaAbility ability) {
        super(ability);
    }

    @Override
    public RosheenMeandererManaAbility copy() {
        return new RosheenMeandererManaAbility(this);
    }
}

class RosheenMeandererConditionalMana extends ConditionalMana {

    public RosheenMeandererConditionalMana() {
        super(Mana.ColorlessMana(4));
        staticText = "Spend this mana only on costs that contain {X}";
        addCondition(new RosheenMeandererManaCondition());
    }
}

class RosheenMeandererManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (AbilityType.SPELL.equals(source.getAbilityType())) {
            MageObject object = game.getObject(source.getSourceId());
            return object != null
                    && object.getManaCost().getText().contains("X");

        } else {
            return source.getManaCosts().getText().contains("X");
        }
    }
}
