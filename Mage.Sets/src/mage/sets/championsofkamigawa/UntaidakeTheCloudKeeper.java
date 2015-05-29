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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;

/**
 *
 * @author anonymous
 */
public class UntaidakeTheCloudKeeper extends CardImpl {

    public UntaidakeTheCloudKeeper(UUID ownerId) {
        super(ownerId, 285, "Untaidake, the Cloud Keeper", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");

        // Untaidake, the Cloud Keeper enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Pay 2 life: Add {2} to your mana pool. Spend this mana only to cast legendary spells.
        Ability ability = new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new LegendarySpellManaBuilder());
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);
        
    }

    public UntaidakeTheCloudKeeper(final UntaidakeTheCloudKeeper card) {
        super(card);
    }

    @Override
    public UntaidakeTheCloudKeeper copy() {
        return new UntaidakeTheCloudKeeper(this);
    }
}

class LegendarySpellManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new LegendaryCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast legendary spells";
    }
}

class LegendaryCastConditionalMana extends ConditionalMana {

    public LegendaryCastConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast legendary spells";
        addCondition(new LegendaryCastManaCondition());
    }
}

class LegendaryCastManaCondition extends ManaCondition implements Condition {
    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source.getSourceId());
            if (object != null && object.getSupertype().contains("Legendary")) {
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