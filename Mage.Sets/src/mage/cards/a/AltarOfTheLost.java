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
package mage.cards.a;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward
 */
public class AltarOfTheLost extends CardImpl {

    public AltarOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Altar of the Lost enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add two mana in any combination of colors to your mana pool. Spend this mana only to cast spells with flashback from a graveyard.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new AltarOfTheLostManaBuilder()));
    }

    public AltarOfTheLost(final AltarOfTheLost card) {
        super(card);
    }

    @Override
    public AltarOfTheLost copy() {
        return new AltarOfTheLost(this);
    }
}

class AltarOfTheLostManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new AltarOfTheLostConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast spells with flashback from a graveyard";
    }
}

class AltarOfTheLostConditionalMana extends ConditionalMana {

    public AltarOfTheLostConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast spells with flashback from a graveyard";
        addCondition(new AltarOfTheLostManaCondition());
    }
}

class AltarOfTheLostManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object instanceof Spell && ((Spell) object).getFromZone().equals(Zone.GRAVEYARD)) {
            for (Ability ability : ((Spell) object).getAbilities(game)) {
                if (ability instanceof FlashbackAbility) {
                    return true;
                }
            }
        }
        return false;
    }
}
