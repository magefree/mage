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
package mage.cards.s;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.SliverToken;

/**
 *
 * @author emerald000
 */
public class SliverHive extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Sliver");
    private static final FilterSpell filterSpell = new FilterSpell("a Sliver spell");

    static {
    }

    static {
        filter.add(new SubtypePredicate(SubType.SLIVER));
        filterSpell.add(new SubtypePredicate(SubType.SLIVER));
    }

    public SliverHive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color to your mana pool. Spend this mana only to cast a Sliver spell.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new ConditionalSpellManaBuilder(filterSpell), true));

        // {5}, {T}: Create a 1/1 colorless Sliver creature token. Activate this ability only if you control a Sliver.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SliverToken()), new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(filter),
                "{5}, {T}: Create a 1/1 colorless Sliver creature token. Activate this ability only if you control a Sliver.");
        ability.addCost(new GenericManaCost(5));
        this.addAbility(ability);
    }

    public SliverHive(final SliverHive card) {
        super(card);
    }

    @Override
    public SliverHive copy() {
        return new SliverHive(this);
    }
}

class SliverHiveManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        this.mana.setFlag(true); // indicates that the mana is from second ability
        return new SliverHiveConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Sliver spell.";
    }
}

class SliverHiveConditionalMana extends ConditionalMana {

    SliverHiveConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a Sliver spell.";
        addCondition(new SliverHiveManaCondition());
    }
}

class SliverHiveManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID manaProducer, Cost costToPay) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source.getSourceId());
            if (object.hasSubtype("Sliver", game)) {
                return true;
            }
        }
        return false;
    }
}
