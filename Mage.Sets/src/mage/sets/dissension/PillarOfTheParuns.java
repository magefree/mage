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
package mage.sets.dissension;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;

/**
 *
 * @author noxx
 */
public class PillarOfTheParuns extends CardImpl {

    public PillarOfTheParuns(UUID ownerId) {
        super(ownerId, 176, "Pillar of the Paruns", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DIS";

        // {T}: Add one mana of any color to your mana pool. Spend this mana only to cast a multicolored spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new PillarOfTheParunsManaBuilder()));
    }

    public PillarOfTheParuns(final PillarOfTheParuns card) {
        super(card);
    }

    @Override
    public PillarOfTheParuns copy() {
        return new PillarOfTheParuns(this);
    }
}


class PillarOfTheParunsManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new PillarOfTheParunsConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a multicolored spell";
    }
}

class PillarOfTheParunsConditionalMana extends ConditionalMana {

    public PillarOfTheParunsConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a multicolored spell";
        addCondition(new MultiColoredSpellCastManaCondition());
    }
}

class MultiColoredSpellCastManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source.getSourceId());
            if (object != null && object.getColor(game).getColorCount() > 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId) {
        return apply(game, source);
    }
}
