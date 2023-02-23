package mage.abilities.keyword;

import mage.ObjectColor;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PrototypedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.continuous.SetCardColorSourceEffect;
import mage.abilities.effects.common.cost.SetCardCostSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801, Susucr
 */
public class PrototypeAbility extends SpellAbility {

    private String ruleText;

    public PrototypeAbility(Card card, String manaString, ObjectColor color, int power, int toughness) {
        super(new ManaCostsImpl<>(manaString), card.getName(), Zone.HAND, SpellAbilityType.PROTOTYPE);

        SetBasePowerToughnessSourceEffect basePTEffect =
            new SetBasePowerToughnessSourceEffect(power, toughness, Duration.EndOfGame, false);

        basePTEffect.setText("");

        card.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new ConditionalContinuousEffect(
                basePTEffect,
                PrototypedCondition.instance,
                "")
        ));

        card.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new ConditionalContinuousEffect(
                new SetCardColorSourceEffect(color, Duration.EndOfGame),
                PrototypedCondition.instance,
                "")
        ));

        card.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new ConditionalContinuousEffect(
                new SetCardCostSourceEffect(this.manaCosts, Duration.EndOfGame),
                PrototypedCondition.instance,
                "")
        ));

        this.ruleText =  "Prototype " + this.manaCosts.getText() + " — " + power + "/" + toughness;
        setRuleAtTheTop(true);
    }

    private PrototypeAbility(final PrototypeAbility ability) {
        super(ability);
    }

    @Override
    public PrototypeAbility copy() {
        return new PrototypeAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}
