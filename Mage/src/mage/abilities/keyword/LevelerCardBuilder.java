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

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.Card;
import mage.cards.LevelerCard;
import mage.constants.Duration;
import mage.counters.CounterType;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation by BetaSteward was discarded as requires special handling in Mage.Core.
 *
 * Instead it was replaced by conditional continuous effects and builder pattern.
 *
 * @author BetaSteward_at_googlemail.com
 * @author noxx
 */
public class LevelerCardBuilder {

    private int level1;
    private int level2;
    private int power;
    private int toughness;
    private String rule = "";

    private Abilities<Ability> abilities = new AbilitiesImpl<>();

    /**
     * Main method constructing ability.
     *
     * @return
     */
    public List<Ability> build() {
        List<Ability> constructed = new ArrayList<>();

        Condition condition = new SourceHasCounterCondition(CounterType.LEVEL, level1, level2);
        for (Ability ability : abilities) {
            ContinuousEffect effect = new GainAbilitySourceEffect(ability);
            ConditionalContinuousEffect abEffect = new ConditionalContinuousEffect(effect, condition, "");
            Ability staticAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, abEffect);
            staticAbility.setRuleVisible(false);
            constructed.add(staticAbility);
        }

        ContinuousEffect effect = new SetPowerToughnessSourceEffect(power, toughness, Duration.WhileOnBattlefield);
        ConditionalContinuousEffect ptEffect = new ConditionalContinuousEffect(effect, condition, rule);
        constructed.add(new SimpleStaticAbility(Zone.BATTLEFIELD, ptEffect));

        return constructed;
    }

    public LevelerCardBuilder setLevel1(int level1) {
        this.level1 = level1;
        return this;
    }

    public LevelerCardBuilder setLevel2(int level2) {
        this.level2 = level2;
        return this;
    }

    public LevelerCardBuilder setPower(int power) {
        this.power = power;
        return this;
    }

    public LevelerCardBuilder setToughness(int toughness) {
        this.toughness = toughness;
        return this;
    }

    public LevelerCardBuilder addAbility(Ability ability) {
        this.abilities.add(ability);
        return this;
    }

    public LevelerCardBuilder setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public LevelerCardBuilder reset() {
        this.level1 = 0;
        this.level2 = 0;
        this.power = 0;
        this.toughness = 0;
        this.abilities.clear();
        this.rule = "";
        return this;
    }

    /**
     * Add abilities to card to enable switching between levels.
     *
     * @param card
     * @param levelAbilities
     * @return list of levelAbilities to add to card
     */
    public static List<Ability> construct(LevelAbility... levelAbilities) {
        LevelerCardBuilder builder = new LevelerCardBuilder();
        List<Ability> abilities = new ArrayList<>();

        for (LevelAbility levelAbility : levelAbilities) {
            // set main params
            builder = builder
                    .reset()
                    .setLevel1(levelAbility.getLevel1())
                    .setLevel2(levelAbility.getLevel2() == -1 ? Integer.MAX_VALUE : levelAbility.getLevel2())
                    .setPower(levelAbility.getPower())
                    .setToughness(levelAbility.getToughness())
                    .setRule(levelAbility.getRule());

            // set abilities that give the next level
            for (Ability addedAbility : levelAbility.getAbilities()) {
                builder.addAbility(addedAbility);
            }

            // build static abilities and add them to list
            abilities.addAll(builder.build());
        }

        return abilities;
    }

    public static class LevelAbility {

        private final int level1;
        private final int level2;
        private final int power;
        private final int toughness;

        private Abilities<Ability> abilities = new AbilitiesImpl<>();

        public LevelAbility(int level1, int level2, Abilities<Ability> abilities, int power, int toughness) {
            this.level1 = level1;
            this.level2 = level2;
            this.abilities.addAll(abilities);
            this.power = power;
            this.toughness = toughness;
        }

        public LevelAbility(LevelAbility ability) {
            this.level1 = ability.level1;
            this.level2 = ability.level2;
            this.abilities = ability.abilities.copy();
            this.power = ability.power;
            this.toughness = ability.toughness;
        }

        public int getLevel1() {
            return level1;
        }

        public int getLevel2() {
            return level2;
        }

        public Abilities<Ability> getAbilities() {
            return abilities;
        }

        public int getPower() {
            return power;
        }

        public int getToughness() {
            return toughness;
        }

        public String getRule() {
            StringBuilder sb = new StringBuilder();
            sb.append("<b>Level ").append(level1);
            if (level2 == -1) {
                sb.append("+");
            }
            else {
                sb.append("-").append(level2);
            }
            sb.append(":</b> ").append(power).append("/").append(toughness).append(" ");
            for (String rule: abilities.getRules("{this}")) {
                sb.append(rule).append(" ");
            }
            return sb.toString();
        }

    }
}
