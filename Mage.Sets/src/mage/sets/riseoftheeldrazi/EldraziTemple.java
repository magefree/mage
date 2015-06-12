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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;

import mage.ConditionalMana;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author Loki, nantuko
 */
public class EldraziTemple extends CardImpl {

    public EldraziTemple(UUID ownerId) {
        super(ownerId, 227, "Eldrazi Temple", Rarity.RARE, new CardType[]{ CardType.LAND }, null);
        this.expansionSetCode = "ROE";

        // {T}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {2} to your mana pool. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.  
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new EldraziTempleManaBuilder()));
    }

    public EldraziTemple(final EldraziTemple card) {
        super(card);
    }

    @Override
    public EldraziTemple copy() {
        return new EldraziTemple(this);
    }
}

class EldraziTempleManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new EldraziTempleConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi";
    }
}

class EldraziTempleConditionalMana extends ConditionalMana {

    public EldraziTempleConditionalMana(Mana mana) {
        super(mana);
        addCondition(new EldraziTempleCondition());
    }
}

class EldraziTempleCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        return object != null && object.hasSubtype("Eldrazi") && object.getColor(game).isColorless();        
    }
}
