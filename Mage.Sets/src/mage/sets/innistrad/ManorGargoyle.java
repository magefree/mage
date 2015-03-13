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
package mage.sets.innistrad;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class ManorGargoyle extends CardImpl {

    private static final String rule = "{this} is indestructible as long as it has defender.";

    public ManorGargoyle(UUID ownerId) {
        super(ownerId, 228, "Manor Gargoyle", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Gargoyle");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(DefenderAbility.getInstance());
        
        
    /*
    TODO: Implement the dependency rule
    613.7. Within a layer or sublayer, determining which order effects are applied in is sometimes done using a dependency system. 
              If a dependency exists, it will override the timestamp system.
        613.7a An effect is said to “depend on” another if 
                (a) it’s applied in the same layer (and, if applicable, sublayer) as the other effect (see rules 613.1 and 613.3); 
                (b) applying the other would change the text or the existence of the first effect, what it applies to, or what 
                    it does to any of the things it applies to; and 
                (c) neither effect is from a characteristic-defining ability or both effects are from characteristic-defining 
                    abilities. Otherwise, the effect is considered to be independent of the other effect.
        613.7b  An effect dependent on one or more other effects waits to apply until just after all of those effects have been applied.
                If multiple dependent effects would apply simultaneously in this way, they’re applied in timestamp order relative to each
                other. If several dependent effects form a dependency loop, then this rule is ignored and the effects in the dependency 
                loop are applied in timestamp order.
        613.7c  After each effect is applied, the order of remaining effects is reevaluated and may change if an effect that has not yet
                been applied becomes dependent on or independent of one or more other effects that have not yet been applied.
         */

        // Manor Gargoyle has indestructible as long as it has defender.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), HasDefenderCondition.getInstance(), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // {1}: Until end of turn, Manor Gargoyle loses defender and gains flying.
        Effect effect2 = new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("Until end of turn, {this} loses defender");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new ManaCostsImpl("{1}"));
        effect2 = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and gains flying");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public ManorGargoyle(final ManorGargoyle card) {
        super(card);
    }

    @Override
    public ManorGargoyle copy() {
        return new ManorGargoyle(this);
    }
}

class HasDefenderCondition implements Condition {

    private static HasDefenderCondition INSTANCE;

    private HasDefenderCondition() {
    }

    public static HasDefenderCondition getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HasDefenderCondition();
        }
        return INSTANCE;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Abilities<Ability> abilities = permanent.getAbilities();
            for (Ability ability : abilities) {
                if (ability.getClass().equals(DefenderAbility.getInstance().getClass())) {
                    return true;
                }
            }
        }
        return false;
    }
}
