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
package mage.sets.masterseditioniv;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;

/**
 *
 * @author hanasu
 */
public class SoldeviMachinist extends CardImpl {

    public SoldeviMachinist(UUID ownerId) {
        super(ownerId, 63, "Soldevi Machinist", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ME4";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.subtype.add("Artificer");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {2} to your mana pool. Spend this mana only to activate abilities of artifacts.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new SoldeviMachinistManaBuilder()));
    }

    public SoldeviMachinist(final SoldeviMachinist card) {
        super(card);
    }

    @Override
    public SoldeviMachinist copy() {
        return new SoldeviMachinist(this);
    }
}

class SoldeviMachinistManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ArtifactAbilityConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities of artifacts";
    }
}

class ArtifactAbilityConditionalMana extends ConditionalMana {

    public ArtifactAbilityConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities of artifacts";
        addCondition(new ArtifactAbilityManaCondition());
    }
}

class ArtifactAbilityManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null && source.getAbilityType().equals(AbilityType.ACTIVATED)) {
            MageObject object = game.getObject(source.getSourceId());
            if (object != null && object.getCardType().contains(CardType.ARTIFACT)) {
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
