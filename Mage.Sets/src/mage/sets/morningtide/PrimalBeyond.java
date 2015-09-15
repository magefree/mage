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
package mage.sets.morningtide;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TGower
 */
public class PrimalBeyond extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Elemental card from your hand");

    static {
        filter.add(new SubtypePredicate("Elemental"));
    }

    public PrimalBeyond(UUID ownerId) {
        super(ownerId, 149, "Primal Beyond", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "MOR";

        // As Primal Beyond enters the battlefield, you may reveal an Elemental card from your hand. If you don't, Primal Beyond enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Elemental card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add one mana of any color to your mana pool. Spend this mana only to cast an Elemental spell or activate an ability of an Elemental.
        Ability ability = new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new PrimalBeyondManaBuilder(), true);
        this.addAbility(ability);
    }

    public PrimalBeyond(final PrimalBeyond card) {
        super(card);
    }

    @Override
    public PrimalBeyond copy() {
        return new PrimalBeyond(this);
    }
}

class PrimalBeyondManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new PrimalBeyondConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Elemental spells or activate abilities of Elementals";
    }
}

class PrimalBeyondConditionalMana extends ConditionalMana {

    public PrimalBeyondConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast Elemental spells or activate abilities of Elementals";
        addCondition(new PrimalBeyondManaCondition());
    }
}

class PrimalBeyondManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        return object != null && object.hasSubtype("Elemental");
    }
}
