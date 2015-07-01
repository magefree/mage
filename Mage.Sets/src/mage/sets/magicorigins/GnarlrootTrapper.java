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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GnarlrootTrapper extends CardImpl {

    private final static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("attacking ELf you control");

    static {
        filter.add(new AttackingPredicate());
        filter.add(new SubtypePredicate("Elf"));
    }

    public GnarlrootTrapper(UUID ownerId) {
        super(ownerId, 100, "Gnarlroot Trapper", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "ORI";
        this.subtype.add("Elf");
        this.subtype.add("Druid");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}, Pay 1 life: Add {G} to your mana pool. Spend this mana only to cast an Elf creature spell.
        Ability ability = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.GreenMana(1), new GnarlrootTrapperManaBuilder());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {T}: Target attacking Elf you control gains deathtouch until end of turn.
        Effect effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Target attacking Elf you control gains deathtouch until end of turn. <i>(Any amount of damage it deals to a creature is enough to destroy it.)</i>");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);

    }

    public GnarlrootTrapper(final GnarlrootTrapper card) {
        super(card);
    }

    @Override
    public GnarlrootTrapper copy() {
        return new GnarlrootTrapper(this);
    }
}

class GnarlrootTrapperManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new GnarlrootTrapperConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an Elf creature spell.";
    }
}

class GnarlrootTrapperManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source.getSourceId());
            if (object.hasSubtype("Elf")
                    && object.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }
}

class GnarlrootTrapperConditionalMana extends ConditionalMana {

    public GnarlrootTrapperConditionalMana(Mana mana) {
        super(mana);
        addCondition(new GnarlrootTrapperManaCondition());
    }
}
